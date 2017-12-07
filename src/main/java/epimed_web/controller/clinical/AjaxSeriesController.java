package epimed_web.controller.clinical;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import epimed_web.entity.bind.NcbiGeoGsm;
import epimed_web.entity.mongodb.experiments.Sample;
import epimed_web.entity.mongodb.experiments.Series;
import epimed_web.entity.mongodb.experiments.Status;
import epimed_web.entity.mongodb.jobs.Job;
import epimed_web.entity.mongodb.jobs.JobStatus;
import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.entity.pojo.AjaxResponse;
import epimed_web.repository.mongodb.experiments.SampleRepository;
import epimed_web.repository.mongodb.experiments.SeriesRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mail.MailService;
import epimed_web.service.mongodb.JobService;
import epimed_web.service.mongodb.LogService;
import epimed_web.service.mongodb.PlatformService;
import epimed_web.service.mongodb.SampleService;
import epimed_web.service.util.NcbiService;

@Controller
@RequestMapping("/{root}/ajax/")
public class AjaxSeriesController extends ApplicationLogger {


	@Autowired
	private SeriesRepository seriesRepository;

	@Autowired
	private SampleRepository sampleRepository;

	@Autowired
	private SampleService sampleService;

	@Autowired
	private PlatformService platformService;

	@Autowired
	private JobService jobService;

	@Autowired
	private LogService logService;

	@Autowired
	private NcbiService ncbiService;
	
	@Autowired
	private MailService mailService;


	/** ====================================================================================== */

	@RequestMapping(value = "/upload/series", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse uploadSeries (
			@RequestParam(value = "jobid", required = false) String jobid,
			@RequestParam(value = "symbol", required = false) String symbol,
			@RequestParam(value = "taxid", required = false) Integer taxid,
			@RequestParam(value = "currentIndex", required = false) Integer currentIndex,
			@RequestParam(value = "totalElements", required = false) Integer totalElements,
			@RequestParam(value = "listElements", required = false) List<String> listElements,
			@RequestParam(value = "idSelectedPlatform", required = false) String idSelectedPlatform,
			@RequestParam(value = "jobtype", required = false) JobType jobtype
			) {

		AjaxResponse ajaxResponse = new AjaxResponse();

		// === Series ===
		Job job = jobService.findByJobid(jobid);
		Series series = (Series) job.getMainObject();

		try {

			// === First request ===
			if (currentIndex==null || currentIndex.equals(0)) {
				logger.debug("*** Init ***");
				currentIndex=0;	
				series.setStatus(Status.incomplete);
				seriesRepository.save(series);
				logger.debug("{}", series);
				
				job.setType(JobType.DATA_REQUEST);
				jobService.save(job);

				mailService.sendRequestByMail("analyse", job);
				
				// === Check platforms ===
				platformService.createPlatforms(series.getPlatforms());
			}

			// === Create or update sample ===
			NcbiGeoGsm gsm = new NcbiGeoGsm(ncbiService.loadGeo(symbol));
			Sample sample = sampleService.createSample(gsm, series.getId());
			logger.debug("{}", sample);
			sampleRepository.save(sample);


			// === Progress ===
			if (currentIndex!=null && currentIndex>0 && currentIndex<(totalElements-1)) {
				logger.debug("*** Progress ***");
				jobService.updateJob(jobid, currentIndex+1, JobStatus.progress, null);
			}

			// === Last request ===
			if (currentIndex!=null && currentIndex.equals(totalElements-1)) {
				logger.debug("*** Terminate ***");
				jobService.updateJob(jobid, totalElements, JobStatus.success, null);
				logService.log("Job jobid=" + jobid + " is terminated");
				series.setStatus(Status.imported);
				seriesRepository.save(series);
			}

			ajaxResponse.setSuccess(true);
			ajaxResponse.setMessage("imported");
			ajaxResponse.setJobid(jobid);
		}
		catch (Exception e) {
			logger.debug("*** Error ***");
			ajaxResponse.setSuccess(false);
			ajaxResponse.setMessage("ERROR: " + e.getMessage());
			jobService.updateJob(jobid, currentIndex+1, JobStatus.error, e.getMessage());
			logService.log("Job jobid=" + jobid + " has an error: " + e.getMessage());
			series.setStatus(Status.error);
			seriesRepository.save(series);
			e.printStackTrace();
		}


		return ajaxResponse;
	}
	/** ====================================================================================== */

}
