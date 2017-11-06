package epimed_web.controller.gene;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import epimed_web.entity.neo4j.Annotation;
import epimed_web.entity.neo4j.DataSource;
import epimed_web.repository.neo4j.GeneRepository;
import epimed_web.repository.neo4j.Neo4jAnnotationRepository;
import epimed_web.service.log.ApplicationLogger;

@Controller
public class AnnotationController extends ApplicationLogger {

	@Autowired
	private Neo4jAnnotationRepository annotationRepository;

	@Autowired
	private GeneRepository geneRepository;

	
	/** ====================================================================================== */

	@RequestMapping(value = "annotations", method = {RequestMethod.GET, RequestMethod.POST})
	public String displayAllAnnotations(Model model) throws IOException {
		
		List<Annotation> listAnnotations = annotationRepository.findAllAnnotations();
		model.addAttribute("listAnnotations", listAnnotations);
		
		return "annotations/list";

	}

	/** ====================================================================================== */

	@RequestMapping(value = "genes/annotation", method = {RequestMethod.GET, RequestMethod.POST})
	public String queryGeneAnnotations(Model model, 
			@RequestParam(value = "taxid", required = false) Integer taxid,
			@RequestParam(value = "source", required = false) DataSource source,
			@RequestParam(value = "parameter", required = false) String parameter,
			@RequestParam(value = "uid", required = false) String uid,
			@RequestParam(value = "searchButton", required = false) String searchButton,
			HttpServletRequest request
			) throws IOException {

		// === Taxid ===
		
		Integer selectedTaxid = taxid;
		if (selectedTaxid==null) {
			selectedTaxid = 9606;
		}

		// === Source ===
		
		DataSource selectedSource = source;
		if (selectedSource==null) {
			selectedSource = DataSource.rnaseq;
		}
		List<String> listSources = annotationRepository.findSourcesByTaxid(selectedTaxid);
		if (listSources!=null && !listSources.contains(selectedSource.name())) {
			selectedSource = DataSource.valueOf(listSources.get(0));
		}

		// === Parameter (tissue) ===
		
		String selectedParameter = parameter;
		List<String> listParameters = annotationRepository.findParametersBySourceAndTaxid(selectedSource, selectedTaxid);
		if (listParameters!=null && !listParameters.isEmpty() && listParameters.size()==1) {
			selectedParameter = listParameters.get(0);
		}

		// === Uid ===
		
		String selectedUid = uid;
		List<String> listUids = null;
		if (selectedParameter!=null && !selectedParameter.isEmpty()) {
			listUids = annotationRepository.findUidsBySourceAndTaxidAndParameter(selectedSource, selectedTaxid, selectedParameter);
		}
		else {
			listUids = annotationRepository.findUidsBySourceAndTaxid(selectedSource, selectedTaxid);
		}
		if (listUids!=null && !listUids.isEmpty() && listUids.size()==1) {
			selectedUid = listUids.get(0);
		}
		
		// === Number of found genes ===

		Long nbGenes = null;
		if (selectedUid!=null && !selectedUid.isEmpty()) {
			nbGenes = geneRepository.countByAnnotation(selectedUid, selectedTaxid);
			if (selectedParameter==null || selectedParameter.isEmpty()) {
				selectedParameter = annotationRepository.findParameterByIdAnnotation(selectedUid);
			}
			model.addAttribute("nbGenes", nbGenes);	
		}

		
		model.addAttribute("listSources", listSources);
		model.addAttribute("listParameters", listParameters);
		model.addAttribute("listUids", listUids);

		model.addAttribute("selectedSource", selectedSource);
		model.addAttribute("selectedTaxid", selectedTaxid);
		model.addAttribute("selectedParameter", selectedParameter);
		model.addAttribute("selectedUid", selectedUid);

		return "genes/annotation";
	}

	/** ====================================================================================== */

}
