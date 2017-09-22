package epimed_web.controller.gene;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.util.FormatService;

@Controller
@RequestMapping("/genes")
public class UpdateGeneController extends ApplicationLogger {

	@Autowired
	private FormatService formatService;


	/** ====================================================================================== */

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateGet(Model model, HttpServletRequest request) throws IOException {

		return "genes/update";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object updatePost(
			@RequestParam(value = "listGenes", required = false) String listGenes,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String [] genes = formatService.convertStringToArray(listGenes);

		String message="";

		if (listGenes!=null && !listGenes.isEmpty() && genes!=null && genes.length>0) {
			message = "Recognized genes " + Arrays.toString(genes);
		}
		else {
			// ===== No entry =====
			message = "Please entry at lease one gene symbol";
		}

		ModelAndView modelAndView = new ModelAndView("genes/update"); 
		modelAndView.addObject("listGenes", listGenes);
		modelAndView.addObject("message", message);
		return modelAndView;

	}

	/** ====================================================================================== */

}
