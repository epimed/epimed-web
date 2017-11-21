package epimed_web.controller.gene;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import epimed_web.entity.mongodb.jobs.JobStatus;
import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.entity.pojo.AjaxResponse;
import epimed_web.form.AjaxForm;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.JobElementService;
import epimed_web.service.mongodb.JobService;
import epimed_web.service.mongodb.LogService;
import epimed_web.service.neo4j.GeneService;
import epimed_web.service.neo4j.PositionService;
import epimed_web.service.neo4j.ProbesetService;
import epimed_web.service.util.FormatService;

@Controller
@RequestMapping("/{root}/ajax/")
public class AjaxGeneController extends ApplicationLogger {

	@Autowired
	private FormatService formatService;

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

	@Autowired
	private LogService logService;

	/** ====================================================================================== */

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse upload (
			HttpServletRequest request,
			@RequestParam(value = "jobid", required = false) String jobid,
			@RequestParam(value = "symbol", required = false) String symbol,
			@RequestParam(value = "taxid", required = false) Integer taxid,
			@RequestParam(value = "currentIndex", required = false) Integer currentIndex,
			@RequestParam(value = "totalElements", required = false) Integer totalElements,
			@RequestParam(value = "listElements", required = false) List<String> listElements,
			@RequestParam(value = "idSelectedPlatform", required = false) String idSelectedPlatform,
			@RequestParam(value = "idAssembly", required = false) String idAssembly,
			@RequestParam(value = "positionType", required = false) String positionType,
			@RequestParam(value = "jobtype", required = false) JobType jobtype
			) {

		AjaxResponse ajaxResponse = new AjaxResponse();

		try  {

			// === Create job on the first request ===
			if (currentIndex==null || currentIndex.equals(0)) {
				logger.debug("*** Init ***");
				currentIndex=0;
				jobid = jobService.createJob(jobtype, listElements, null);
				logService.log("New job started with jobid=" + jobid);
			}

			// === No symbol ===
			if (symbol==null || symbol.isEmpty() || taxid==null) {
				ajaxResponse.setSuccess(false);
				ajaxResponse.setMessage("No symbol / empty symbol / no organism. Skip.");
				return ajaxResponse;
			}


			String [] symbols = formatService.convertStringToArray(symbol);

			for (String processedSymbol: symbols) {

				// === Update job status in progress ===
				if (currentIndex!=null && currentIndex>0 && currentIndex<(totalElements-1)) {
					logger.debug("*** Progress ***");
					if (currentIndex==1) {
						jobService.updateJob(jobid, currentIndex+1, JobStatus.progress, null);
					}
				}

				// === Ajax form ===
				AjaxForm ajaxForm = new AjaxForm();
				ajaxForm.setSymbol(processedSymbol);
				ajaxForm.setTaxid(taxid);
				ajaxForm.setJobtype(jobtype);
				ajaxForm.setIdPlatform(idSelectedPlatform);

				// === Parameters ===
				if (jobtype.equals(JobType.probeset)) {
					ajaxForm.setParameter(idSelectedPlatform);
				}
				if (jobtype.equals(JobType.position)) {
					ajaxForm.setParameter(idAssembly + "_" + positionType);
				}

				boolean success = geneService.populateBySymbolAndTaxid(ajaxForm);
				
				if (jobtype.equals(JobType.probeset)) {
					probesetService.populate(ajaxForm);
				}
				if (jobtype.equals(JobType.position)) {
					positionService.populate(ajaxForm);
				}

				ajaxForm.setSuccess(success);
				jobElementService.createJobElments(jobid, processedSymbol, taxid, 7, ajaxForm);


				// === Job terminated ===
				if (currentIndex!=null && currentIndex.equals(totalElements-1)) {
					logger.debug("*** Terminate ***");
					jobService.updateJob(jobid, totalElements, JobStatus.success, null);
					logService.log("Job jobid=" + jobid + " is terminated");
				}

				// === Response status and message ===
				String message = "Symbol " + processedSymbol + " doesn't correspond to a gene";
				if (ajaxForm.isSuccess()) {
					message = "Found " + ajaxForm.getNbGenes() +  " gene(s) in " + ajaxForm.getSource();
				}
				ajaxResponse.setSuccess(ajaxForm.isSuccess());
				ajaxResponse.setMessage(message);
				ajaxResponse.setJobid(jobid);

			}

		} 
		catch (Exception e) {
			logger.debug("*** Error ***");
			jobService.updateJob(jobid, currentIndex+1, JobStatus.error, e.getMessage());
			ajaxResponse.setSuccess(false);
			ajaxResponse.setMessage("ERROR: " + e.getMessage());
			logService.log("Job jobid=" + jobid + " has an error: " + e.getMessage());
			e.printStackTrace();
		}

		return ajaxResponse;
	}


	/** ====================================================================================== */

}
