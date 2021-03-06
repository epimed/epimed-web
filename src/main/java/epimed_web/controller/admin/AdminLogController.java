package epimed_web.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import epimed_web.entity.mongodb.admin.Log;
import epimed_web.entity.mongodb.admin.User;
import epimed_web.repository.mongodb.admin.LogRepository;
import epimed_web.repository.mongodb.admin.UserRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.UserService;
import epimed_web.service.util.FormatService;

@Controller
@RequestMapping("/admin/log")
public class AdminLogController extends ApplicationLogger {

	@Autowired
	public UserRepository userRepository;

	@Autowired 
	public UserService userService;

	@Autowired
	public LogRepository logRepository;

	@Autowired
	public FormatService formatService;


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

		List<Log> logs = new ArrayList<Log>();

		// === Target is idUser ===
		User user = userRepository.findOne(target);
		if (user!=null) {
			logs = logRepository.findByIPs(user.getIp());
		}

		// === Targer id idLog ===
		if (user==null) {
			ObjectId idLog = new ObjectId(target);
			Log targetlog = logRepository.findOne(idLog);
			if (targetlog!=null) {
				logs.add(targetlog);
			}
		}

		for (Log log: logs) {
			logRepository.delete(log);
		}
		return "redirect:/admin/log";

	}


	/** ====================================================================================== */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/", "", "/list"}, method = RequestMethod.GET)
	public String logs (
			Model model,
			HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}

		List<Log> logs = logRepository.findLastLogs(50);

		for (Log log: logs) {
			User user = userRepository.findByIp(log.getSingleIp());
			if (user!=null) {
				log.setUser(user);
			}
			else {
				log.setUser(userService.generateDefaultUser());
			}

			// === Truncate a long list of elements ===
			if (log.getParameter()!=null) {
				Map<String, Object> parameter = (Map<String, Object>) log.getParameter();
				Set<String> keyset = parameter.keySet();
				if (keyset.contains("listElements")) {
					List<String> list =   (List<String>) parameter.get("listElements");
					
					if (list!=null && !list.isEmpty()) {
						List<String> listElements = formatService.convertStringToList(list.get(0));

						if (listElements!=null && listElements.size()>10) {
							List<String> truncatedListElements = new ArrayList<String>();
							parameter.remove("listElements");
							truncatedListElements.addAll(listElements.subList(0, 10));
							truncatedListElements.add("...");
							parameter.put("listElements", truncatedListElements);
							log.setParameter(parameter);
						}
					}
				}
			}
		}

		model.addAttribute("logs", logs);

		return "admin/log";

	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/{target}"}, method = RequestMethod.GET)
	public String logsTarget (
			Model model,
			@PathVariable String target,
			HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}

		List<String> listIPs = new ArrayList<String>();

		// === Target is idUser ===
		User user = userRepository.findOne(target);
		if (user!=null) {
			listIPs = user.getIp();
		}

		logger.debug("target {}", target);

		// === Target is IP ===
		if (user==null) {
			listIPs.add(target);
			user = userRepository.findByIp(target);
		}

		List<Log> logs = logRepository.findByIPs(listIPs, 300);

		for (Log log: logs) {
			if (user!=null) {
				log.setUser(user);
			}
			else {
				log.setUser(userService.generateDefaultUser());
			}
		}
		model.addAttribute("logs", logs);

		return "admin/log";

	}

	/** ====================================================================================== */

}
