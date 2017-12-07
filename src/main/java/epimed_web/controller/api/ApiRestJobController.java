package epimed_web.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import epimed_web.entity.mongodb.jobs.Job;
import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.repository.mongodb.jobs.JobRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.JobService;

@RestController
public class ApiRestJobController extends ApplicationLogger {
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobService jobService;


	/** ====================================================================================== */
	
	@RequestMapping(value = "/query/jobstatus") 
	public Map<String, Object> queryJobStatus (
			@RequestParam(value = "jobid") String jobid
			){
		
		Map<String, Object> mapJobStatus = new HashMap<String, Object>();
		
		mapJobStatus.put("jobid", jobid);
		mapJobStatus.put("status", "No job found for jobid = " + jobid);
		
		Job job = jobRepository.findOne(jobid);
		if (job!=null) {
			mapJobStatus.put("status", job.getStatus());
		}
		return mapJobStatus;
	}
	
/** ====================================================================================== */
	
	@RequestMapping(value = "/query/jobid") 
	public Map<String, Object> queryGetJobId (){
		
		List<String> emptyListElements = new ArrayList<String>();
		String jobid = jobService.createJob(JobType.unknown, emptyListElements, null);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("jobid", jobid);
		
		return map;
	}
	
	/** ====================================================================================== */
	
}
