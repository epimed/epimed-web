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

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import epimed_web.entity.mongodb.Series;
import epimed_web.repository.mongodb.SampleRepository;
import epimed_web.repository.mongodb.SeriesRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.util.FileService;
import epimed_web.service.util.FormatService;


@Controller
public class SeriesController extends ApplicationLogger {

	@Autowired
	private FileService fileService;

	@Autowired
	private FormatService formatService;

	@Autowired
	private SeriesRepository seriesRepository;

	@Autowired
	private SampleRepository sampleRepository;

	/** ====================================================================================== */

	@RequestMapping(value = {"/series", "expgroup"}, method = RequestMethod.GET)
	public String downloadExpGroupGet(Model model, HttpServletRequest request) throws IOException {

		List<Series> listSeries = seriesRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
		model.addAttribute("listSeries", listSeries);

		return "series/list";
	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/series", "expgroup"}, method = RequestMethod.POST)
	public Object downloadExpGroupPost(
			@RequestParam(value = "listIdSeries", required = false) String[] listIdSeries,
			final RedirectAttributes redirectAttributes,
			HttpServletRequest request,
			HttpServletResponse response
			) throws IOException {

		if (listIdSeries!=null && listIdSeries.length>0) {

			// ====== Create workbook =====

			XSSFWorkbook workbook = fileService.createWorkbook();

			// ====== Add mandatory parameters ====== 

			List<Document> listExpGroup = sampleRepository.findSamples(listIdSeries, "exp_group"); 
			List<String> headerExpGroup = formatService.extractHeader(listExpGroup, "exp_group");
			List<Object> dataExpGroup = formatService.extractData(listExpGroup, headerExpGroup, "exp_group");
			fileService.addSheet(workbook, "mandatory parameters", headerExpGroup, dataExpGroup);


			// ===== Add supplementary parameters ======

			List<Document> listParam = sampleRepository.findSamples(listIdSeries, "parameters"); 
			List<String> headerParam = formatService.extractHeader(listParam, "parameters");
			List<Object> dataParam = formatService.extractData(listParam, headerParam, "parameters");
			fileService.addSheet(workbook, "supplementary parameters", headerParam, dataParam);

			// ===== File generation =====

			String fileName =  fileService.generateFileName("EpiMed_experimental_grouping", listIdSeries, 3, "SEVERAL_STUDIES", "xlsx");
			logger.debug("Generated file name {}", fileName);
			response.setContentType("application/msexcel");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			fileService.writeWorkbook(response.getOutputStream(), workbook);

			return null;
		}

		else {

			// ===== No study selected =====
			redirectAttributes.addFlashAttribute("message","Please select at least one study");
			return "redirect:/series";

		}

	}

	/** ====================================================================================== */

}
