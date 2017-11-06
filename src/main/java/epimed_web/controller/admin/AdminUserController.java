package epimed_web.controller.admin;

import java.util.List;

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

import epimed_web.entity.mongodb.admin.User;
import epimed_web.repository.mongodb.admin.UserRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.UserService;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController extends ApplicationLogger {

	@Autowired
	public UserRepository userRepository;

	@Autowired 
	public UserService userService;


	/** ====================================================================================== */

	@RequestMapping(value = {"", "/", "/list", "/update", "/delete"}, method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}

		List<User> users = userRepository.findAllSortedByLastName();
		for (User user: users) {
			logger.debug("{}", user);
			if ( user.getLastName()==null || (user.getLastName().isEmpty() )) {
				userRepository.delete(user);
			}
		}
		
		model.addAttribute("users", users);

		return "admin/user/list";

	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/add"}, method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}
		model.addAttribute("user", new User());

		return "admin/user/form";

	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/update/{idUser}"}, method = RequestMethod.GET)
	public String update(Model model, 
			@PathVariable String idUser,
			HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}
		model.addAttribute("user", userRepository.findOne(idUser));

		return "admin/user/form";

	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/add", "/update"}, method = RequestMethod.POST)
	public String update(Model model, 
			@ModelAttribute("user") @Valid User user, BindingResult result,
			final RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}


		if (user.getId()==null || user.getId().isEmpty()) {
			user.setId(userService.generateUniqueUserId());
		}

		userRepository.save(user);
		String message = "User " + user.getFirstName() + " " + user.getLastName() +
				" (" + user.getId() + ") has been successfully updated";

		redirectAttributes.addFlashAttribute("msg", message);
		redirectAttributes.addFlashAttribute("css", "success");

		return "redirect:/admin/user/";

	}


	/** ====================================================================================== */

	@RequestMapping(value = {"/delete/{idUser}"}, method = RequestMethod.GET)
	public String delete(Model model, 
			@PathVariable String idUser,
			final RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}

		User user = userRepository.findOne(idUser);

		String message = "User " + idUser + " doesn't exist";
		String css = "danger";
		if (user!=null) {
			userRepository.delete(idUser);
			message = "User " + user.getFirstName() + " " + user.getLastName() +
					" (" + user.getId() + ") has been successfully deleted"; 
			css = "success";
		}

		redirectAttributes.addFlashAttribute("msg", message);
		redirectAttributes.addFlashAttribute("css", css);

		return "redirect:/admin/user/";

	}


}
