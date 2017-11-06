package epimed_web.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import epimed_web.entity.mongodb.jobs.JobHeader;
import epimed_web.repository.mongodb.jobs.JobHeaderRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.UserService;

@Controller
@RequestMapping("/admin/header")
public class AdminJobHeaderController extends ApplicationLogger {

	@Autowired
	public JobHeaderRepository jobHeaderRepository;

	@Autowired 
	public UserService userService;


	/** ====================================================================================== */

	@RequestMapping(value = {"/", "", "/list", "/update", "/delete"}, method = RequestMethod.GET)
	public String headers (
			Model model,
			HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}
		
		model.addAttribute("listHeaders", jobHeaderRepository.findAll());

		return "admin/header/list";

	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/add"}, method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}
		model.addAttribute("jobHeader", new JobHeader());

		return "admin/header/form";

	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/update/{idHeader}"}, method = RequestMethod.GET)
	public String update(Model model, 
			@PathVariable String idHeader,
			HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}
		model.addAttribute("jobHeader", jobHeaderRepository.findOne(idHeader));

		return "admin/header/form";

	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/add", "/update"}, method = RequestMethod.POST)
	public String update(Model model, 
			@ModelAttribute("jobHeader") @Valid JobHeader jobHeader, BindingResult result,
			final RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}


		if (jobHeader.getId()!=null) {
			jobHeaderRepository.save(jobHeader);
			String message = "Header " + jobHeader.getId() + " has been successfully updated";
			redirectAttributes.addFlashAttribute("msg", message);
			redirectAttributes.addFlashAttribute("css", "success");
		}

		

		return "redirect:/admin/header/";

	}


	/** ====================================================================================== */

	@RequestMapping(value = {"/delete/{idHeader}"}, method = RequestMethod.GET)
	public String delete(Model model, 
			@PathVariable String idHeader,
			final RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}

		JobHeader jobHeader= jobHeaderRepository.findOne(idHeader);

		String message = "Header " + idHeader + " doesn't exist";
		String css = "danger";
		if (jobHeader!=null) {
			jobHeaderRepository.delete(idHeader);
			message = "Header " + idHeader + " has been successfully deleted"; 
			css = "success";
		}

		redirectAttributes.addFlashAttribute("msg", message);
		redirectAttributes.addFlashAttribute("css", css);

		return "redirect:/admin/header/";

	}

	
	/** ====================================================================================== */



}
