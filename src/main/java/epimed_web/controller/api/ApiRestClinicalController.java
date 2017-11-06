package epimed_web.controller.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import epimed_web.repository.mongodb.experiments.PlatformRepository;
import epimed_web.repository.mongodb.experiments.SampleRepository;
import epimed_web.repository.mongodb.experiments.SeriesRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.PlatformService;
import epimed_web.service.mongodb.SampleService;
import epimed_web.service.mongodb.SeriesService;
import epimed_web.service.util.FileService;
import epimed_web.service.util.FormatService;

@RestController
public class ApiRestClinicalController extends ApplicationLogger {

	@Autowired
	private FileService fileService;
	
	@Autowired
	private FormatService formatService;

	@Autowired
	private SeriesRepository seriesRepository;
	
	@Autowired
	private SeriesService seriesService;
	
	@Autowired
	private SampleRepository sampleRepository;
	
	@Autowired
	private SampleService sampleService;
	
	@Autowired
	private PlatformRepository platformRepository;	
	
	@Autowired
	private PlatformService platformService;	


	/** ====================================================================================== */

	@RequestMapping(value = "/expgroup/{gseNumbers}", method = RequestMethod.GET)
	public void downloadGse(
			@PathVariable String gseNumbers,
			HttpServletRequest request,
			HttpServletResponse response
			) throws IOException {

		String [] listIdSeries = formatService.convertStringToArray(gseNumbers);

		List<Document> listExpGroup = sampleRepository.findSamples(listIdSeries, "exp_group"); 
		List<String> headerExpGroup = formatService.extractHeader(listExpGroup, "exp_group");
		List<Object> dataExpGroup = formatService.extractData(listExpGroup, headerExpGroup, "exp_group");

		// ===== File generation =====

		String fileName =  fileService.generateFileName("EpiMed_standard_exp_group", listIdSeries, 3, "SEVERAL_STUDIES", "csv");
		logger.debug("Generated file name {}", fileName);
		response.setContentType( "text/csv" );
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		fileService.writeCsvFile(response, headerExpGroup, dataExpGroup);
	}


	/** ====================================================================================== */

	@RequestMapping(value = "/parameters/{gseNumbers}", method = RequestMethod.GET)
	public void downloadParameters(
			@PathVariable String gseNumbers,
			HttpServletRequest request,
			HttpServletResponse response
			) throws IOException {

		String [] listIdSeries = formatService.convertStringToArray(gseNumbers);

		List<Document> listParam= sampleRepository.findSamples(listIdSeries, "parameters"); 
		List<String> headerParam = formatService.extractHeader(listParam, "parameters");
		List<Object> dataParam = formatService.extractData(listParam, headerParam, "parameters");

		// ===== File generation =====

		String fileName =  fileService.generateFileName("EpiMed_original_parameters", listIdSeries, 3, "SEVERAL_STUDIES", "csv");
		logger.debug("Generated file name {}", fileName);
		response.setContentType( "text/csv" );
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		fileService.writeCsvFile(response, headerParam, dataParam);
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/query/samples") 
	public Object querySamples (
			@RequestParam Map<String, String> mapRequestParameters,
			@RequestParam(value = "format", defaultValue="csv") String format,
			@RequestParam(value = "document", defaultValue="exp_group") String document,
			HttpServletResponse response
			){

		mapRequestParameters.remove("format");
		mapRequestParameters.remove("document");

		// === Default projection ===
		if (document==null || document.isEmpty() ||  !document.equals("parameters")) {
			document= "exp_group";
		}

		Bson filter = sampleService.createFilter(mapRequestParameters);
		
		List<Document> listExpGroup = sampleRepository.getSamples(filter, document); 

		if (format!=null && format.equals("json")) {
			List<String> listSeries = sampleRepository.listSeries(filter);
			List<String> listPlatforms = sampleRepository.listPlatforms(filter);
			Document jsonResult = new Document();
			jsonResult.append("nbSamples", listExpGroup.size());
			jsonResult.append("nbPlatforms", listPlatforms.size());
			jsonResult.append("nbSeries", listSeries.size());
			jsonResult.append("platforms", listPlatforms);
			jsonResult.append("series", listSeries);
			return jsonResult;
		}

		List<String> headerExpGroup = formatService.extractHeader(listExpGroup, document);
		List<Object> dataExpGroup = formatService.extractData(listExpGroup, headerExpGroup, document);

		// ===== File generation =====

		String fileName =  fileService.generateFileName("EpiMed_" + document, "csv");
		logger.debug("Generated file name {}", fileName);
		response.setContentType( "text/csv" );
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		fileService.writeCsvFile(response, headerExpGroup, dataExpGroup);

		return null;

	}


	/** ====================================================================================== */

	@RequestMapping(value = "/query/series") 
	public Object querySeries (
			@RequestParam Map<String, String> mapRequestParameters,
			@RequestParam(value = "format", defaultValue="csv") String format,
			HttpServletResponse response
			){

		logger.debug("Request parameters {}", mapRequestParameters);
		
		mapRequestParameters.remove("format");

		Bson filter = seriesService.createFilter(mapRequestParameters);
		List<Document> listSeries = seriesRepository.list(filter);
		
		if (format!=null && format.equals("json")) {
			return listSeries;
		}
				
		List<String> header = formatService.extractHeader(listSeries, null);
		List<Object> data = formatService.extractData(listSeries, header, null);

		// ===== File generation =====

		String fileName =  fileService.generateFileName("EpiMed_series", "csv");
		logger.debug("Generated file name {}", fileName);
		response.setContentType( "text/csv" );
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		fileService.writeCsvFile(response, header, data);

		return null;

	}

	/** ====================================================================================== */

	@RequestMapping(value = "/query/platforms") 
	public Object queryPlatforms (
			@RequestParam Map<String, String> mapRequestParameters,
			@RequestParam(value = "format", defaultValue="csv") String format,
			HttpServletResponse response
			){

		logger.debug("Request parameters {}", mapRequestParameters);
		
		mapRequestParameters.remove("format");

		Bson filter = platformService.createFilter(mapRequestParameters);
		List<Document> listPlatforms = platformRepository.list(filter);
		
		if (format!=null && format.equals("json")) {
			return listPlatforms;
		}
				
		List<String> header = formatService.extractHeader(listPlatforms, null);
		List<Object> data = formatService.extractData(listPlatforms, header, null);

		// ===== File generation =====

		String fileName =  fileService.generateFileName("EpiMed_platforms", "csv");
		logger.debug("Generated file name {}", fileName);
		response.setContentType( "text/csv" );
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		fileService.writeCsvFile(response, header, data);

		return null;

	}
	
	/** ====================================================================================== */
	
	
}
