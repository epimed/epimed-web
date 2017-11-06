package epimed_web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import epimed_web.repository.mongodb.experiments.SampleRepository;
import epimed_web.repository.mongodb.experiments.SeriesRepository;
import epimed_web.service.log.ApplicationLogger;

@Controller
public class HomeController extends ApplicationLogger {

	@Autowired
	private SeriesRepository seriesRepository;
	
	@Autowired
	private SampleRepository sampleRepository;
	
	
	/** ====================================================================================== */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {

		// ====== Statistics ======
		model.addAttribute("nbSeries", seriesRepository.count());
		model.addAttribute("nbSamples", sampleRepository.count());

		// ===== Chart =====
		model.addAttribute("chartTitle", "Tissue samples");
		model.addAttribute("chartId", "topology_chart");
		model.addAttribute("chartHeaders", new String [] {"Tissue", "Nb of samples"} );
		
		List<Document> topologyDistribution = sampleRepository.getTopologyDistribution();
		model.addAttribute("topologyDistribution", topologyDistribution);

		return "index";
	}

	/** ====================================================================================== */

	
}
