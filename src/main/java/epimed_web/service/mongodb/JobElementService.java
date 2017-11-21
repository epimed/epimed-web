package epimed_web.service.mongodb;

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
import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.entity.neo4j.Gene;
import epimed_web.entity.neo4j.Position;
import epimed_web.entity.neo4j.Probeset;
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
			if (jobs!=null && jobs.size()>1) {
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

		if (type.equals(JobType.position)) {
			this.createPositionJobElements(jobid, element, ajaxForm);
		}


		return true;
	}

	/** ================================================================================= */

	public void createPositionJobElements(String jobid, String element, AjaxForm ajaxForm) {

		String parameter = ajaxForm.getParameter();

		if (ajaxForm.getListGenes()!=null && !ajaxForm.getListGenes().isEmpty()) {

			for (Gene gene: ajaxForm.getListGenes()) {

				if (gene.getPositions()==null || gene.getPositions().isEmpty()) {

					JobElement jobElement = this.createDefaultJobElement(jobid, element, ajaxForm);
					jobElement.setType(JobType.position);

					JobResult result = new JobResult();
					result.append("your_input", element);

					// Specific
					result.append("tax_id",ajaxForm.getTaxid());
					result.append("entrez", gene.getUid());
					result.append("gene_symbol",gene.getGeneSymbol());
					result.append("title", gene.getTitle());
					result.append("location", gene.getLocation());
					result.append("chrom",gene.getChrom());
					result.append("status", gene.getStatus().name());
					result.append("feature", gene.getFeature());
					result.append("aliases", gene.getAliases()==null ? null : gene.getAliases());
					result.append("database", ajaxForm.getSource());

					jobElement.setResult(result);
					jobElementRepository.save(jobElement);

				}
				else {

					for (Position position: gene.getPositions()) {

						JobElement jobElement = this.createDefaultJobElement(jobid, element, ajaxForm);
						jobElement.setType(JobType.position);

						JobResult result = new JobResult();
						result.append("your_input", element);

						// Specific
						result.append("tax_id",ajaxForm.getTaxid());
						result.append("entrez", gene.getUid());
						result.append("gene_symbol",gene.getGeneSymbol());
						result.append("title", gene.getTitle());
						result.append("location", gene.getLocation());
						result.append("chrom",gene.getChrom());
						result.append("status", gene.getStatus().name());
						result.append("feature", gene.getFeature());
						result.append("aliases", gene.getAliases()==null ? null : gene.getAliases());
						result.append("database", ajaxForm.getSource());

						// Position
						result.append("id_position", position.getIdPosition());
						result.append("id_ensembl", position.getIdEnsembl());
						result.append("id_assembly", parameter.split("_")[0]);
						result.append("chrom_text", position.getChrom());
						result.append("strand", position.getStrand());
						result.append("tx_start", position.getTxStart());
						result.append("tx_end", position.getTxEnd());
						result.append("cds_start", position.getCdsStart());
						result.append("cds_end", position.getCdsEnd());
						result.append("exon_count", position.getExonCount());
						result.append("exon_starts", position.getExonStarts());
						result.append("exon_ends", position.getExonEnds());

						jobElement.setResult(result);
						jobElementRepository.save(jobElement);

					}
				}
			}
		}
		else {
			this.createEmptyJobElement(jobid, JobType.update, element, ajaxForm);
		}

	}



	/** ================================================================================= */

	public void createProbesetJobElements(String jobid, String element, AjaxForm ajaxForm) {

		if ( !ajaxForm.getListGenes().isEmpty() || !ajaxForm.getProbesets().isEmpty() ) {

			Set<String> idProbesets = new HashSet<String>();

			// === Probesets via genes ===

			for (Gene gene: ajaxForm.getListGenes()) {

				for (Probeset pg: gene.getProbesets()) {

					idProbesets.add(pg.getUid());

					JobElement jobElement = this.createDefaultJobElement(jobid, element, ajaxForm);
					jobElement.setType(JobType.probeset);

					JobResult result = new JobResult();
					result.append("your_input", element);

					// Specific
					result.append("tax_id",ajaxForm.getTaxid());
					result.append("entrez", gene.getUid());
					result.append("gene_symbol",gene.getGeneSymbol());
					result.append("title", gene.getTitle());
					result.append("location", gene.getLocation());
					result.append("chrom",gene.getChrom());
					result.append("status", gene.getStatus().name());
					result.append("aliases", gene.getAliases()==null ? null : gene.getAliases());
					result.append("feature", gene.getFeature());
					result.append("database", ajaxForm.getSource());

					result.append("platform",ajaxForm.getIdPlatform());
					result.append("probeset", pg.getUid());

					jobElement.setResult(result);
					jobElementRepository.save(jobElement);

				}
			}

			// === Probesets by direct link (from nucleotides) if not already saved via genes ===

			for (Probeset pn: ajaxForm.getProbesets() ) {

				if (!idProbesets.contains(pn.getUid())) {

					JobElement jobElement = this.createDefaultJobElement(jobid, element, ajaxForm);
					jobElement.setType(JobType.probeset);

					JobResult result = new JobResult();
					result.append("your_input", element);

					// Specific
					result.append("tax_id",ajaxForm.getTaxid());


					result.append("platform",ajaxForm.getIdPlatform());
					result.append("probeset", pn.getUid());
					result.append("database", "EpiMed NUC");

					jobElement.setResult(result);
					jobElementRepository.save(jobElement);
				}
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

				JobResult result = new JobResult();
				result.append("your_input", element);

				// Specific
				result.append("tax_id",ajaxForm.getTaxid());
				result.append("entrez", gene.getUid());
				result.append("gene_symbol",gene.getGeneSymbol());
				result.append("title", gene.getTitle());
				result.append("location", gene.getLocation());
				result.append("chrom",gene.getChrom());
				result.append("status", gene.getStatus().name());
				result.append("feature", gene.getFeature());
				result.append("aliases", gene.getAliases()==null ? null : gene.getAliases());
				result.append("database", ajaxForm.getSource());

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
		result.append("your_input", element);

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
