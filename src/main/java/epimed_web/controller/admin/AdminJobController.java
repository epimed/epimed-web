package epimed_web.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import epimed_web.entity.mongodb.admin.User;
import epimed_web.entity.mongodb.jobs.Job;
import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.repository.mongodb.admin.LogRepository;
import epimed_web.repository.mongodb.admin.UserRepository;
import epimed_web.repository.mongodb.jobs.JobHeaderRepository;
import epimed_web.repository.mongodb.jobs.JobRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.JobElementService;
import epimed_web.service.mongodb.JobService;
import epimed_web.service.mongodb.LogService;
import epimed_web.service.mongodb.UserService;

@Controller()
@RequestMapping("/admin/job")
public class AdminJobController extends ApplicationLogger {

	@Autowired
	public UserRepository userRepository;

	@Autowired 
	public UserService userService;

	@Autowired
	public LogRepository logRepository;

	@Autowired
	public LogService logService;

	@Autowired
	public JobHeaderRepository jobHeaderRepository;

	@Autowired
	public JobRepository jobRepository;

	@Autowired
	public JobService jobService;

	@Autowired
	public JobElementService jobElementService;


	/** ====================================================================================== */

	@RequestMapping(value = {"/delete/{target}"}, method = RequestMethod.GET)
	public String purge (Model model, 
			@PathVariable String target,
			HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}

		List<Job> jobs = new ArrayList<Job>();

		// === Target is idUser ===
		User user = userRepository.findOne(target);
		if (user!=null) {
			jobs = jobRepository.findByIPs(user.getIp());
		}

		// === Targer is jobId ===
		Job targetjob = jobRepository.findOne(target);
		if (targetjob!=null) {
			jobs.add(targetjob);
		}

		for (Job job: jobs) {
			jobElementService.deleteJobElements(job.getId());
			jobRepository.delete(job);
		}

		return "redirect:/admin/job";

	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/{target}"}, method = RequestMethod.GET)
	public String jobsByUser (
			@PathVariable String target,
			Model model) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}

		List<Job> jobs = new ArrayList<Job>();

		// === Target is idUser ===
		User user = userRepository.findOne(target);
		if (user!=null) {
			jobs = jobRepository.findByIPs(user.getIp());
		}

		// === Target is job type ===
		if (jobs.isEmpty()) {
			jobs = jobRepository.findByType(JobType.valueOf(target));
		}

		// === Find job user ===
		for (Job job: jobs) {

			User jobuser = userRepository.findByIp(job.getSingleIp());
			if (jobuser!=null) {
				job.setUser(jobuser);
			}
			else {
				job.setUser(userService.generateDefaultUser());
			}

		}

		for (Job job : jobs) {
			job = jobService.truncateListElements(job, 10);
		}

		model.addAttribute("jobs", jobs);

		return "admin/job";

	}


	/** ====================================================================================== */

	@RequestMapping(value = {"","/", "/list"}, method = RequestMethod.GET)
	public String jobs (
			Model model, HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}

		List<Job> jobs = jobRepository.findLastLogs(50);

		for (Job job: jobs) {
			User user = userRepository.findByIp(job.getSingleIp());
			if (user!=null) {
				job.setUser(user);
			}
			else {
				job.setUser(userService.generateDefaultUser());
			}


			job = jobService.truncateListElements(job, 10);

		}
		model.addAttribute("jobs", jobs);

		return "admin/job";

	}


	/** ====================================================================================== */



}
