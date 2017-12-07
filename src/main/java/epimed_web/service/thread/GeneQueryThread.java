package epimed_web.service.thread;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epimed_web.entity.mongodb.jobs.JobStatus;
import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.form.AjaxForm;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.JobElementService;
import epimed_web.service.mongodb.JobService;
import epimed_web.service.neo4j.GeneService;
import epimed_web.service.neo4j.PositionService;
import epimed_web.service.neo4j.ProbesetService;

@Service
public class GeneQueryThread extends ApplicationLogger implements Runnable {


	@Autowired 
	private GeneService geneService;

	@Autowired
	private ProbesetService probesetService;

	@Autowired
	private PositionService positionService;

	
	@Autowired
	private JobService jobService;

	@Autowired
	private JobElementService jobElementService;

	private List<String> listElements = new ArrayList<String>();
	private Integer taxid;
	private String jobid;
	private JobType jobtype;
	private String platform;
	private String assembly;
	private String positionType;




	public Integer getTaxid() {
		return taxid;
	}


	public void setTaxid(Integer taxid) {
		this.taxid = taxid;
	}


	public JobType getJobtype() {
		return jobtype;
	}


	public void setJobtype(JobType jobtype) {
		this.jobtype = jobtype;
	}


	public String getJobid() {
		return jobid;
	}


	public void setJobid(String jobid) {
		this.jobid = jobid;
	}


	public String getPlatform() {
		return platform;
	}


	public void setPlatform(String platform) {
		this.platform = platform;
	}


	public String getAssembly() {
		return assembly;
	}


	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}


	public String getPositionType() {
		return positionType;
	}


	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}


	public List<String> getListElements() {
		return listElements;
	}


	public void setListElements(List<String> listElements) {
		this.listElements = listElements;
	}


	/** =================================================================================== */

	public void run() {

		try {
			logger.debug(Thread.currentThread().getId() + " is running");

			for (int i=0; i<listElements.size(); i++) {

				String symbol = listElements.get(i);

				logger.debug("Processing {}", symbol);

				// === Update job status in progress ===
				if (i==0) {
					logger.debug("*** Progress ***");
					jobService.updateJob(jobid, i+1, JobStatus.progress, null);
				}

				// === Ajax form ===
				AjaxForm ajaxForm = new AjaxForm();
				ajaxForm.setSymbol(symbol);
				ajaxForm.setTaxid(taxid);
				ajaxForm.setJobtype(jobtype);
				ajaxForm.setIdPlatform(platform);
				
				// === Parameters ===
				if (jobtype.equals(JobType.probeset)) {
					ajaxForm.setParameter(platform);
				}
				if (jobtype.equals(JobType.position)) {
					ajaxForm.setParameter(assembly + "_" + positionType);
				}

				boolean success = geneService.populateBySymbolAndTaxid(ajaxForm);
				

				if (jobtype.equals(JobType.probeset)) {
					probesetService.populate(ajaxForm);
				}
				if (jobtype.equals(JobType.position)) {
					positionService.populate(ajaxForm);
				}
				
				ajaxForm.setSuccess(success);
				
				jobElementService.createJobElments(jobid, symbol, taxid, 7, ajaxForm);

				// === Job terminated ===
				if (i==(listElements.size()-1)) {
					jobService.updateJob(jobid, i+1, JobStatus.success, null);
				}
				
			}

			logger.debug(Thread.currentThread().getId() + " is terminated");
		}
		catch (Exception e) {
			logger.debug("*** Error ***");
			jobService.updateJob(jobid, 0, JobStatus.error, e.getMessage());
			e.printStackTrace();
		}
	}


	/** ==================================================================================== */

}
