/**
ClinicalDataController.java * EpiMed - Information system for bioinformatics developments in the field of epigenetics
 * 
 * This software is a computer program which performs the data management 
 * for EpiMed platform of the Institute for Advances Biosciences (IAB)
 *
 * Copyright University of Grenoble Alps (UGA)
 * 
 * Please check LICENSE file
 *
 * Author: Ekaterina Bourova-Flin 
 *
 */
package epimed_web.controller.clinical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import epimed_web.entity.mongodb.experiments.Series;
import epimed_web.entity.mongodb.experiments.Status;
import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.repository.mongodb.experiments.SeriesRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mail.MailService;
import epimed_web.service.mongodb.JobService;
import epimed_web.service.mongodb.LogService;
import epimed_web.service.mongodb.UserService;
import epimed_web.service.util.FormatService;
import epimed_web.service.util.NcbiService;


@Controller
public class SemanticController extends ApplicationLogger {
	
	@Autowired
	private FormatService formatService;

	@Autowired
	private SeriesRepository seriesRepository;

	@Autowired
	private LogService logService;

	@Autowired
	private NcbiService ncbiService;

	@Autowired
	private JobService jobService;

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	/** ====================================================================================== */

	@RequestMapping(value = "/series/add", method = RequestMethod.GET)
	public String loadExternalGet(Model model, HttpServletRequest request) {

		return "series/semantic/form";
	}

	/** ====================================================================================== */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/series/add", method = RequestMethod.POST)
	public String loadExternalPost(Model model, 
			@RequestParam(value = "database", required = false) String database,
			@RequestParam(value = "idSeries", required = false) String idSeries,
			HttpServletRequest request) {

		model.addAttribute("idSeries", idSeries);
		model.addAttribute("selectedDatabase", database);


		// === Check idSeries not empty ===

		if (idSeries==null || idSeries.isEmpty()) {
			model.addAttribute("message", "Accession ID is empty. Please provide a valid accession ID.");
			return "series/semantic/form";
		}

		// === Recognize elements and select the first one ===
		String originalIdSeries = idSeries;
		String [] elements = formatService.convertStringToArray(idSeries);
		if (elements!=null && elements.length>0) {
			idSeries = elements[0];
		}

		// === Check if this idSeries already exists in the database ===

		Series existingSeries = seriesRepository.findOne(idSeries);
		if (existingSeries!=null && 
				!existingSeries.getStatus().equals(Status.error) &&
				!existingSeries.getStatus().equals(Status.incomplete)) {
			model.addAttribute("message", "Study " + idSeries + " already exists in EpiMed Database.");
			return "series/semantic/form";
		}

		// === Redirect for manual import ===

		if (database!=null && !database.contains("NCBI GEO") && !idSeries.startsWith("GSE")) {
			return "redirect:/series/request?database=" + database +  "&type=import" + "&idSeries=" + originalIdSeries;
		}

		// === Check if idSeries starts with GSE ===
		
		if (!idSeries.startsWith("GSE")) {
			model.addAttribute("message", "NCBI GEO accession ID should start with a GSE prefix. Your input '" + idSeries + "' doesn't contain GSE. Please provide a valid accession ID.");
			return "series/semantic/form";
		}
		
		// === Check if this idSeries exists on NCBI ===

		List<String> listIdGsd = ncbiService.eSearch("gds", idSeries+"[ACCN]+AND+GSE[ETYP]", null);

		if (listIdGsd==null || listIdGsd.isEmpty()) {
			model.addAttribute("message", "No study corresponding to accession ID '" + idSeries + "' exists on NCBI GEO" 
					+ ". Please provide a valid accession ID.");
			return "series/semantic/form";
		}


		// === Load idSeries from NCBI ===

		Document docSeries = ncbiService.eSummary("gds", listIdGsd.get(0));

		if (docSeries==null || docSeries.isEmpty()) {
			model.addAttribute("message", "Study " + idSeries + " has been found on " + database 
					+ " but the corresponding data cannot be imported. Please contact the administrator of EpiMed database.");
			logService.log("ERROR: Study " + idSeries + " has been found on " + database + " but the corresponding data cannot be imported");
			return "series/semantic/form";
		}

		Series series = new Series();
		series.setId(idSeries);
		series.setTitle(docSeries.getString("title"));

		String [] numPlatforms = formatService.convertStringToArray(docSeries.getString("gpl"));
		for (String numPlatform: numPlatforms) {
			series.getPlatforms().add("GPL"  + numPlatform);
		}
		
		series.getSecondaryAccessions().add(docSeries.getString("bioproject"));
		series.setImportDate(new Date());
		series.setNbSamples(docSeries.getInteger("n_samples"));
		series.setIp(userService.getIp());
		series.setSingleIp(userService.getSingleIp());

		List<String> listElements = new ArrayList<String>();

		List<Document> docSamples = docSeries.get("samples", ArrayList.class);
		if (docSamples!=null) {
			for (Document docSample: docSamples) {
				listElements.add(docSample.getString("accession"));
			}
		}

		String jobid = jobService.createJob(JobType.series, listElements, series);

		model.addAttribute("series", series);
		model.addAttribute("jobid", jobid);

		model.addAttribute("listElements", listElements);

		return "series/semantic/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/series/request", method = {RequestMethod.GET, RequestMethod.POST})
	public String requestImport(Model model, 
			@RequestParam(value = "database", required = false) String database,
			@RequestParam(value = "idSeries", required = false) String idSeries,
			@RequestParam(value = "type", required = false) String type,
			HttpServletRequest request) {

		
		String [] elements = formatService.convertStringToArray(idSeries);
		
		model.addAttribute("elements", elements);
		model.addAttribute("idSeries", idSeries);
		model.addAttribute("database", database);
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("database", database);
		properties.put("idSeries", idSeries);
		properties.put("type", type);
		
		String jobid = jobService.createJob(JobType.DATA_REQUEST, Arrays.asList(elements), properties);
		
		// mailService.sendRequestByMail(type, database, idSeries, elements);

		return "series/semantic/request";

	}

	
	
	/** ====================================================================================== */

}
