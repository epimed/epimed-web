package epimed_web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import epimed_web.entity.mongodb.admin.User;
import epimed_web.entity.mongodb.experiments.Series;
import epimed_web.repository.mongodb.admin.UserRepository;
import epimed_web.repository.mongodb.experiments.SeriesRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.SampleService;
import epimed_web.service.mongodb.UserService;

@Controller()
@RequestMapping("/admin/series")
public class AdminSeriesController extends ApplicationLogger {

	@Autowired 
	public UserService userService;

	@Autowired
	public UserRepository userRepository;

	@Autowired
	private SampleService sampleService;
	
	@Autowired
	private SeriesRepository seriesRepository;



	/** ====================================================================================== */

	@RequestMapping(value = {"","/", "/list"}, method = RequestMethod.GET)
	public String jobs (
			Model model) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}

		List<Series> listSeries = seriesRepository.findAll(new Sort(Sort.Direction.DESC, "importDate"));

		for (Series series: listSeries) {

			User user = userRepository.findByIp(series.getSingleIp());
			if (user!=null) {
				series.setUser(user);
			}
			else {
				series.setUser(userService.generateDefaultUser());
			}

		}

		model.addAttribute("listSeries", listSeries);
		return "admin/series";

	}


	/** ====================================================================================== */

	@RequestMapping(value = {"/delete/{idSeries}"}, method = RequestMethod.GET)
	public String purge (Model model, 
			@PathVariable String idSeries) {

		// === Access ===
		if (!userService.isAdmin()) {
			model.addAttribute("errorMessage", "Access denied");
			return "error";
		}

		Series series = seriesRepository.findOne(idSeries);
		if (series!=null) {
			sampleService.deleteSamples(idSeries);
			seriesRepository.delete(series);
		}
		
		return "redirect:/admin/series";

	}

	/** ====================================================================================== */



}
