package epimed_web.controller.clinical;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import epimed_web.entity.pojo.FileUpload;
import epimed_web.repository.mongodb.SampleRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.util.FileService;
import epimed_web.service.util.FormatService;
import epimed_web.validator.FileUploadValidator;

@SessionAttributes({"file", "data", "mapHeaders", "separator"})
@Controller
public class FileUploadController extends ApplicationLogger {

	@Autowired
	FileUploadValidator fileUploadValidator;

	@Autowired
	FileService fileService;

	@Autowired
	FormatService formatService;

	@Autowired
	private SampleRepository sampleRepository;

	/** ====================================================================================== */

	@RequestMapping(value = {"/updateExpgroup", "/importExpgroup"}, method = RequestMethod.GET)
	public String updateExpgroupGet(Model model, HttpServletRequest request) throws IOException {

		return "samples/updateExpgroup";
	}

	/** ====================================================================================== 
	 * @throws Exception */

	@RequestMapping(value = "/updateExpgroup", method = RequestMethod.POST)
	public String updateExpgroupPost(
			Model model,
			@ModelAttribute("fileUpload") FileUpload fileUpload,  
			BindingResult result, 
			HttpServletRequest request
			) throws Exception {

		// === Validation ===
		fileUploadValidator.validate(fileUpload, result);

		if (!result.hasErrors()) {

			// === Clean up session attributes ===
			request.getSession().removeAttribute("file");
			request.getSession().removeAttribute("data");
			request.getSession().removeAttribute("mapHeaders");
			request.getSession().removeAttribute("separator");

			// === Define session attributes ===
			model.addAttribute("file", fileUploadValidator.getFile());
			model.addAttribute("data", fileUploadValidator.getData());
			model.addAttribute("separator", fileUploadValidator.getSeparator());
			model.addAttribute("mapHeaders", fileUploadValidator.getMapHeaders());

			// === Define request attributes ===
			model.addAttribute("listAnnotations", fileUploadValidator.getNormalizedHeader());

		}

		return "samples/updateExpgroup";

	}

	/** ====================================================================================== */

	@RequestMapping(value = "/importExpgroup", method = RequestMethod.POST)
	public String importExpgroup(
			@ModelAttribute("file") File file,
			@ModelAttribute("data") List<Object> data,
			@ModelAttribute("mapHeaders") Map<String, String> mapHeaders,
			@ModelAttribute("separator") String separator,
			@RequestParam(value = "listId", required = false) String[] listId,
			Model model,
			HttpServletRequest request,
			SessionStatus sessionStatus
			) throws Exception {

		String message = null;
		boolean success = false;
		String url = null;

		if (listId!=null && listId.length>0) {

			// ===== At least one annotation is selected =====
			try {
				List<String> header = fileService.getCsvHeader(file, separator);

				Integer idSampleIndex = header.indexOf("id_sample");
				String[] listIdSamples = new String[data.size()];
				for (int i=0; i<listId.length; i++) {

					String normalizedKey = listId[i];
					String key = mapHeaders.get(normalizedKey);

					Integer keyIndex = header.indexOf(key);

					for (int j=0; j<data.size(); j++) {

						try {

							String [] line = (String[]) data.get(j);
							listIdSamples[j]  = line[idSampleIndex].trim();
							String value = line[keyIndex];

							sampleRepository.updateSample(listIdSamples[j], "exp_group", normalizedKey, value);
						}
						catch (Exception e) {
							// skip empty line
						}
					}
				}

				// ===== Find distinct series for updated samples =====

				List<Object> listObjectSeries = formatService.convertHomogeneousMongoDocuments(sampleRepository.getSeries(listIdSamples));

				String textGseArray = "";
				for (int i=0; i<listObjectSeries.size(); i++) {
					Object [] gseArray = (Object[]) listObjectSeries.get(i);
					textGseArray = textGseArray + gseArray[0].toString();
					if (i<listObjectSeries.size()-1) {
						textGseArray = textGseArray + ",";
					}
				}

				success = true;
				message = "The following annotations have been imported with success: " + Arrays.toString(listId);
				url = textGseArray;

			}
			catch (Exception e) {
				e.printStackTrace();
				message = "The import of annotations " + Arrays.toString(listId) + " has failed because of the following error: " + e.toString()
				+ ". Please check the content of your file and try again.";
			}

		}
		else {

			// ===== No annotation selected =====
			message = "No annotation has been imported.";
		}

		model.addAttribute("message", message);
		model.addAttribute("success", success);
		model.addAttribute("url", url);

		// ===== Clear up session attributes =====
		sessionStatus.setComplete();

		return "samples/updateExpgroupResult";

	}

	/** ====================================================================================== */

}
