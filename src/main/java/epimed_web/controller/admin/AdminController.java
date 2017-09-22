package epimed_web.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import epimed_web.entity.mongodb.admin.Log;
import epimed_web.entity.mongodb.admin.Role;
import epimed_web.entity.mongodb.admin.User;
import epimed_web.entity.mongodb.jobs.JobHeader;
import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.repository.mongodb.admin.LogRepository;
import epimed_web.repository.mongodb.admin.UserRepository;
import epimed_web.repository.mongodb.jobs.JobHeaderRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.LogService;

@Controller
@RequestMapping("/admin")
public class AdminController extends ApplicationLogger {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public LogRepository logRepository;

	@Autowired
	public LogService logService;

	@Autowired
	public JobHeaderRepository jobHeaderRepository;


	/** ====================================================================================== */

	@RequestMapping(value = {"/header"}, method = RequestMethod.GET)
	public void createHeaders (
			Model model,
			HttpServletRequest request) {

		List<String> h1 = new ArrayList<String>();
		h1.add("your_input");
		h1.add("tax_id");
		h1.add("platform");
		h1.add("probeset");

		JobHeader j1 = new JobHeader(JobType.probeset, h1);
		jobHeaderRepository.save(j1);

		List<String> h2 = new ArrayList<String>();
		h2.add("your_input"); 
		h2.add("tax_id");
		h2.add("entrez");  
		h2.add("gene_symbol"); 
		h2.add("title");  
		h2.add("location");  
		h2.add("chrom");  
		h2.add("status");  
		h2.add("aliases");  
		h2.add("database"); 

		JobHeader j2 = new JobHeader(JobType.update, h2);
		jobHeaderRepository.save(j2);



	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/log", "/logs", "/stat", "/stats"}, method = RequestMethod.GET)
	public String logs (
			Model model,
			HttpServletRequest request) {

		// === Access ===
		if (!this.isAdmin(request)) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}

		// === Default unknown user ===
		User defaultUser = new User();
		defaultUser.setFirstName("Unknown");
		defaultUser.setLastName("user");

		List<Log> logs = logRepository.findLastLogs(300);

		for (Log log: logs) {
			User user = userRepository.findByIp(log.getSingleIp());
			if (user!=null) {
				log.setUser(user);
			}
			else {
				log.setUser(defaultUser);
			}
		}
		model.addAttribute("logs", logs);

		return "admin/log";

	}

	/** ====================================================================================== */

	public boolean isAdmin (HttpServletRequest request) {
		boolean result = false;
		String singleIp = logService.getSingleIp();
		if (singleIp!=null) {
			User user = userRepository.findByIp(singleIp);
			if (user.getRole().equals(Role.ROLE_ADMIN)) {
				result = true;
			}
		}
		return result;
	}

	/** ====================================================================================== */

}