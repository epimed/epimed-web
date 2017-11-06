package epimed_web.controller.job;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import epimed_web.entity.mongodb.jobs.Job;
import epimed_web.entity.mongodb.jobs.JobHeader;
import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.repository.mongodb.jobs.JobHeaderRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.JobElementService;
import epimed_web.service.mongodb.JobService;
import epimed_web.service.util.FileService;
import epimed_web.service.util.FormatService;

@Controller
public class JobController extends ApplicationLogger {

	@Autowired
	private FormatService formatService;

	@Autowired
	private FileService fileService;

	@Autowired
	private JobService jobService;
	
	@Autowired
	private JobElementService jobElementService;
	
	@Autowired
	public JobHeaderRepository jobHeaderRepository;

	
	/** ====================================================================================== */

	@RequestMapping(value = {"/{root}/job/download", "/job/download"})
	public void download (
			@RequestParam(value = "jobid") String jobid,
			@RequestParam(value = "format", defaultValue="csv") String format,
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {
		
		// === Job ===
		Job job = jobService.findByJobid(jobid);
		if (job==null) {
			throw new Exception("Job " + jobid + " is not found in EpiMed database");
		}
		
		// === Header for a job type ===
		JobType jobtype = job.getType();
		if (jobtype==null) {
			throw new Exception("Job " + jobid + " has an unknown type");
		}
		JobHeader jobHeader = jobHeaderRepository.findOne(jobtype.name());
		if (jobHeader==null) {
			throw new Exception("No header found for job type " + jobtype + " (jobid=" + jobid + ")");
		}
		List<String> header = jobHeader.getHeader();
		
		
		// === Job elements ===
		List<Document> listJobElements = jobElementService.findByJobid(jobid);
		List<Object> data = formatService.extractData(listJobElements, header, "result");

		// ===== File generation =====

		if (format!=null && format.equals("excel")) {
			String fileName =  fileService.generateFileName("EpiMed_job_" + jobid, "xlsx");
			logger.debug("Generated file name {}", fileName);
			response.setContentType("application/msexcel");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			fileService.writeExcelFile(response.getOutputStream(), header, data);
		}
		else {
			String fileName =  fileService.generateFileName("EpiMed_job_" + jobid, "csv");
			logger.debug("Generated file name {}", fileName);
			response.setContentType( "text/csv" );
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			fileService.writeCsvFile(response, header, data);

		}

	}

	/** ====================================================================================== */

	@RequestMapping(value = "/query/jobs") 
	public String queryJobs (
			@RequestParam(value = "jobid") String jobid,
			@RequestParam(value = "format", defaultValue="csv") String format
			){
		return "redirect:/job/download?jobid=" + jobid + "&format=" + format;
	}
	
	/** ====================================================================================== */
}
