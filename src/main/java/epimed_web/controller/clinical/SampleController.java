package epimed_web.controller.clinical;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import epimed_web.form.SampleForm;
import epimed_web.repository.mongodb.PlatformRepository;
import epimed_web.repository.mongodb.SampleRepository;
import epimed_web.service.form.SampleFormService;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.util.FileService;
import epimed_web.service.util.FormatService;
import epimed_web.validator.SampleFormValidator;

@Controller
public class SampleController extends ApplicationLogger {
	
	@Autowired
	private SampleRepository sampleRepository;

	@Autowired
	private PlatformRepository platformRepository;
	
	@Autowired
	private SampleFormValidator sampleFormValidator;
	
	@Autowired
	private SampleFormService sampleFormService;
	
	@Autowired
	private FormatService formatService;
	
	@Autowired
	private FileService fileService;
	
	/** ====================================================================================== */
	
	@RequestMapping(value = "/samples", method = RequestMethod.GET)
	public String queryGet(Model model) throws IOException {

		List<String> listPlatformTypes =  platformRepository.listPlatformTypes();	
		List<Document> listTopologies = sampleRepository.listTopologies();
		List<Document> listMorphologies = sampleRepository.listMorphologies();

		SampleForm sampleForm = new SampleForm();

		model.addAttribute("sampleForm", sampleForm);
		model.addAttribute("listPlatformTypes", listPlatformTypes);
		model.addAttribute("listTopologies", listTopologies);
		model.addAttribute("listMorphologies", listMorphologies);

		return "samples/search";

	}
	/** ====================================================================================== */

	@RequestMapping(value = "/samples", method = RequestMethod.POST)
	public String queryPost(Model model, 
			@ModelAttribute("sampleForm") @Valid SampleForm sampleForm, BindingResult result,
			@RequestParam(value = "searchButton", required = false) String searchButton,
			@RequestParam(value = "resetButton", required = false) String resetButton,
			@RequestParam(value = "downloadButton", required = false) String downloadButton,
			HttpServletRequest request,
			HttpServletResponse response
			) throws IOException {

		
		// === RESET button ===
		
		if (resetButton!=null) {
			sampleForm = new SampleForm();
		}


		// === SEARCH button ===
		
		if (searchButton!=null) {

			sampleFormValidator.validate(sampleForm, result);
			
			if (!result.hasErrors()) {
				sampleFormService.executeQuery(sampleForm);
				sampleForm.generateUrlParameter();
			}
			else {
				// === The form has Errors ===
				sampleForm.clearResults();
			}
		}
		else {
			sampleForm.clearResults();
		}


		// === DOWNLOAD button ===
		
		if (downloadButton!=null) {

			// ===== Query samples =====

			Bson filter = sampleFormService.convertToFilter(sampleForm);
			List<Document> listExpGroup = sampleRepository.getSamples(filter, downloadButton); 
			List<String> headerExpGroup = formatService.extractHeader(listExpGroup, downloadButton);
			List<Object> dataExpGroup = formatService.extractData(listExpGroup, headerExpGroup, downloadButton);

			// ===== File generation =====

			String fileName =  fileService.generateFileName("EpiMed_" + downloadButton, "csv");
			logger.debug("Generated file name {}", fileName);
			response.setContentType( "text/csv" );
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			fileService.writeCsvFile(response, headerExpGroup, dataExpGroup);
		}


		List<String> listPlatformTypes =  platformRepository.listPlatformTypes();
		List<Document> listPlatforms = platformRepository.listPlatforms(sampleForm.getPlatformType());
		List<Document> listTopologies = sampleRepository.listTopologies();
		List<Document> listMorphologies = sampleRepository.listMorphologies();

		model.addAttribute("sampleForm", sampleForm);
		model.addAttribute("listPlatformTypes", listPlatformTypes);
		model.addAttribute("listPlatforms", listPlatforms);
		model.addAttribute("listTopologies", listTopologies);
		model.addAttribute("listMorphologies", listMorphologies);

		return "samples/search";
	}

	/** ====================================================================================== */

}
