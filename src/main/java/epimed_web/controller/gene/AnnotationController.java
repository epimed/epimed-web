package epimed_web.controller.gene;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import epimed_web.entity.neo4j.Annotation;
import epimed_web.repository.neo4j.Neo4jAnnotationRepository;
import epimed_web.service.log.ApplicationLogger;

@Controller
public class AnnotationController extends ApplicationLogger {

	@Autowired
	private Neo4jAnnotationRepository annotationRepository;

	
	/** ====================================================================================== */

	@RequestMapping(value = "annotations", method = {RequestMethod.GET, RequestMethod.POST})
	public String displayAllAnnotations(Model model) throws IOException {
		
		List<Annotation> listAnnotations = annotationRepository.findAllAnnotations();
		model.addAttribute("listAnnotations", listAnnotations);
		
		return "annotations/list";

	}

	/** ====================================================================================== */

}
