package epimed_web.service.mongodb.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epimed_web.entity.mongodb.jobs.JobElement;
import epimed_web.entity.mongodb.jobs.JobResult;
import epimed_web.entity.mongodb.jobs.JobResultProbeset;
import epimed_web.entity.mongodb.jobs.JobResultUpdate;
import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.entity.neo4j.Gene;
import epimed_web.form.AjaxForm;
import epimed_web.repository.mongodb.jobs.JobElementRepository;
import epimed_web.service.log.ApplicationLogger;

@Service
public class JobElementService extends ApplicationLogger {

	@Autowired
	private JobElementRepository jobElementRepository;


	/** ================================================================================= */
	
	public void deleteJobElements(String jobid) {
		
		List<JobElement> jobElements = jobElementRepository.findJobElementsByJobid(jobid);
		
		for (JobElement jobElement: jobElements) {
			
			Set<String> jobs = new HashSet<String>();
			jobs.addAll(jobElement.getJobs());
		
			// Job element belongs only to one job, can be removed
			if (jobs!=null && jobs.size()==1) {
				jobElementRepository.delete(jobElement);
			}
			
			// Job element belongs to other job, should be kept
			if (jobs!=null && jobs.size()==1) {
				jobs.remove(jobid);
				jobElement.setJobs(jobs);
				jobElementRepository.save(jobElement);
			}
			
		}
	}
	
	/** ================================================================================= */

	public boolean createJobElments(String jobid, String element, Integer taxid, int nbDaysExpiration, AjaxForm ajaxForm) {

		JobType type = ajaxForm.getJobtype();

		if (element==null || element.isEmpty() || type==null || jobid==null || jobid.isEmpty() || taxid==null) {
			return false;
		}

		// === No existing job elements in the database ===

		if (ajaxForm.getListGenes()!=null && !ajaxForm.getListGenes().isEmpty()) {
			ajaxForm.setNbGenes(ajaxForm.getListGenes().size());
		}

		// === Create result lines ===

		if (type.equals(JobType.update)) {
			this.createUpdateJobElements(jobid, element, ajaxForm);
		}

		if (type.equals(JobType.probeset)) {
			this.createProbesetJobElements(jobid, element, ajaxForm);
		}


		return true;
	}


	/** ================================================================================= */

	public void createProbesetJobElements(String jobid, String element, AjaxForm ajaxForm) {

		if (ajaxForm.getProbesets()!=null && !ajaxForm.getProbesets().isEmpty()) {
			
			for (String idProbeset: ajaxForm.getProbesets() ) {

				JobElement jobElement = this.createDefaultJobElement(jobid, element, ajaxForm);
				jobElement.setType(JobType.probeset);

				JobResultProbeset result = new JobResultProbeset();
				result.setInput(element);
				
				// Specific
				result.setTaxid(ajaxForm.getTaxid());
				result.setPlatform(ajaxForm.getIdPlatform());
				result.setProbeset(idProbeset);

				jobElement.setResult(result);
				jobElementRepository.save(jobElement);
			}

		}
		else {
			this.createEmptyJobElement(jobid, JobType.probeset, element, ajaxForm);
		}

	}

	/** ================================================================================= */

	public void createUpdateJobElements(String jobid, String element, AjaxForm ajaxForm) {

		if (ajaxForm.getListGenes()!=null && !ajaxForm.getListGenes().isEmpty()) {

			for (Gene gene: ajaxForm.getListGenes()) {

				JobElement jobElement = this.createDefaultJobElement(jobid, element, ajaxForm);
				jobElement.setType(JobType.update);

				JobResultUpdate result = new JobResultUpdate();
				result.setInput(element);
				
				// Specific
				result.setTaxid(gene.getTaxId());
				result.setEntrez(gene.getUid());
				result.setGeneSymbol(gene.getGeneSymbol());
				result.setTitle(gene.getTitle());
				result.setLocation(gene.getLocation());
				result.setChrom(gene.getChrom());
				result.setStatus(gene.getStatus().name());
				result.setAliases(gene.getAliases()==null ? null : gene.getAliases());
				result.setDatabase(ajaxForm.getSource());

				jobElement.setResult(result);
				jobElementRepository.save(jobElement);

			}
		}
		else {
			this.createEmptyJobElement(jobid, JobType.update, element, ajaxForm);
		}

	}

	/** ================================================================================= */
	
	public JobElement createDefaultJobElement(String jobid, String element, AjaxForm ajaxForm) {
		Date currentDate = new Date();
		JobElement jobElement = new JobElement();
		jobElement.getJobs().add(jobid);
		jobElement.setSubmissionDate(currentDate);
		jobElement.setLastActivity(currentDate);
		jobElement.setElement(element);
		jobElement.setTaxid(ajaxForm.getTaxid());
		jobElement.setParameter(ajaxForm.getParameter());
		
		return jobElement;
	}
	
	
	/** ================================================================================= */

	public void createEmptyJobElement(String jobid, JobType type, String element, AjaxForm ajaxForm) {

		Date currentDate = new Date();
		JobElement jobElement = new JobElement();
		jobElement.getJobs().add(jobid);
		jobElement.setSubmissionDate(currentDate);
		jobElement.setLastActivity(currentDate);
		jobElement.setElement(element);
		jobElement.setTaxid(ajaxForm.getTaxid());
		jobElement.setType(type);
		jobElement.setParameter(ajaxForm.getParameter());

		JobResult result = new JobResult();
		result.setInput(element);

		jobElement.setResult(result);
		jobElementRepository.save(jobElement);
	}


	/** ================================================================================= */

	@SuppressWarnings("unchecked")
	public boolean updateJobElements(String jobid, String element, Integer taxid, int nbDaysExpiration, AjaxForm ajaxForm) {

		JobType type = ajaxForm.getJobtype();

		if (element==null || element.isEmpty() || type==null || jobid==null || jobid.isEmpty() || taxid==null) {
			return false;
		}

		List<Document> docElements = jobElementRepository.findByElementTaxidTypeParameter(element, taxid, type, ajaxForm.getParameter(), nbDaysExpiration);

		if (docElements!=null && !docElements.isEmpty()) {

			// === Existing job elements are found in the database ===

			for (Document docElement: docElements) {
				List<String> jobs = docElement.get("jobs", ArrayList.class);
				if (!jobs.contains(jobid)) {
					jobs.add(jobid);
				}
				docElement.append("jobs", jobs);
				docElement.put("last_activity", new Date());
				jobElementRepository.updateJobElement(docElement);
			}

			ajaxForm.setNbGenes(docElements.size());
			ajaxForm.setSource("EpiMed JOBCACHE");

			return true;
		}

		return false;
	}

	/** ================================================================================= */

	public List<Document> findByJobid(String jobid) {
		return jobElementRepository.findByJobid(jobid);
	}


	/** ================================================================================= */

}
