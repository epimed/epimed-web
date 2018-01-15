package epimed_web.service.mongodb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epimed_web.entity.mongodb.jobs.Job;
import epimed_web.entity.mongodb.jobs.JobStatus;
import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.repository.mongodb.jobs.JobRepository;
import epimed_web.service.log.ApplicationLogger;

@Service
public class JobService extends ApplicationLogger {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private  HttpServletRequest request;


	/** ================================================================================= */

	public String generateUniqueJobId(Date currentDate) {
		Random random = new Random();
		String jobid = dateFormat.format(currentDate) + "R" + random.nextInt(1000);

		boolean isAlreadyExists = jobRepository.exists(jobid);
		int i=0; 
		int max = 10;
		while (isAlreadyExists && i<max) {
			jobid = dateFormat.format(currentDate) + "E" + random.nextInt(1000);
			isAlreadyExists = jobRepository.exists(jobid);
			i++;
		}

		return jobid;

	}

	/** ================================================================================= */

	public String createJob (JobType jobtype, List<String> listElements, Object mainObject) {

		String jobid = null;

		if (listElements!=null) {
			Date currentDate = new Date();
			jobid = this.generateUniqueJobId(currentDate);
			Job job = new Job();
			job.setId(jobid);
			job.setSubmissionDate(currentDate);
			job.setLastActivity(currentDate);

			job.setCurrent(0);
			job.setType(jobtype);
			job.setElements(listElements);
			job.setStatus(JobStatus.init);
			job.setTotal(listElements.size());

			String ipAddress = request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
			job.setIp(ipAddress);

			String singleIp = ipAddress;
			if (ipAddress!=null && !ipAddress.isEmpty()) {
				singleIp = ipAddress.split("[,;\\p{Space}]")[0];
			}
			job.setSingleIp(singleIp);

			job.setMethod(request.getMethod());
			job.setUrl(request.getRequestURI());
			if (mainObject!=null) {
				job.setMainObject(mainObject);
			}

			jobRepository.save(job);

			logger.debug("New job created {}", jobid);
		}

		return jobid;
	}


	/** ================================================================================= */

	public boolean updateJob(String jobid, Integer current, JobStatus jobStatus, String comment) {

		if (jobStatus==null) {
			return false;
		}

		Job job = jobRepository.findOne(jobid);
		return this.updateJob(job, current, jobStatus, comment);

	}

	/** ================================================================================= */

	public boolean updateJob(Job job, Integer current, JobStatus jobStatus, String comment) {

		if (jobStatus==null) {
			return false;
		}

		if (job==null) {
			return false;
		}

		job.setLastActivity(new Date());
		job.setCurrent(current);
		job.setStatus(jobStatus);
		job.setComment(comment);

		jobRepository.save(job);
		return true;
	}

	/** ================================================================================= */

	public boolean updateJob(Job job, JobType jobType, JobStatus jobStatus, List<String> listElements, String comment) {

		if (job==null || jobStatus==null || jobType==null) {
			return false;
		}


		job.setLastActivity(new Date());
		job.setType(jobType);
		job.setStatus(jobStatus);
		if (listElements!=null) {
			job.setElements(listElements);
			job.setTotal(listElements.size());
		}
		job.setComment(comment);

		jobRepository.save(job);
		return true;
	}


	/** ================================================================================= */

	public void save(Job job) {
		jobRepository.save(job);
	}


	/** ================================================================================= */

	public Job findByJobid(String jobid) {
		return jobRepository.findOne(jobid);
	}

	/** ================================================================================= */

	public Job truncateListElements (Job job, int maxNbElements) {
		if (job.getElements()!=null && job.getElements().size()>maxNbElements) {
			List<String> truncatedListElements = new ArrayList<String>();
			truncatedListElements.addAll(job.getElements().subList(0, maxNbElements));
			truncatedListElements.add("...");
			job.setElements(truncatedListElements);
		}
		return job;
	}


	/** ================================================================================= */


}
