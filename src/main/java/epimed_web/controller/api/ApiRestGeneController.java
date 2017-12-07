package epimed_web.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import epimed_web.entity.mongodb.jobs.Job;
import epimed_web.entity.mongodb.jobs.JobStatus;
import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.entity.neo4j.Gene;
import epimed_web.entity.neo4j.GeneStatus;
import epimed_web.repository.mongodb.jobs.JobRepository;
import epimed_web.repository.neo4j.GeneRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.JobService;
import epimed_web.service.mongodb.LogService;
import epimed_web.service.thread.GeneQueryThread;
import epimed_web.service.util.FileService;
import epimed_web.service.util.FormatService;

@RestController
public class ApiRestGeneController extends ApplicationLogger {

	@Autowired
	private FileService fileService;

	@Autowired
	private FormatService formatService;

	@Autowired
	private GeneRepository geneRepository;	

	@Autowired
	private JobService jobService;

	@Autowired
	private LogService logService;

	@Autowired
	private GeneQueryThread geneQueryThread;	

	@Autowired
	private JobRepository jobRepository;


	/** ====================================================================================== */

	@RequestMapping(value = "/query/genes", method = RequestMethod.GET) 
	public Object queryGeneAnnotations (
			@RequestParam(value = "taxid", defaultValue="9606") Integer taxid,
			@RequestParam(value = "annotations", defaultValue="") String annotations,
			@RequestParam(value = "format", defaultValue="csv") String format,
			HttpServletResponse response
			) throws IOException {


		// === Header ===
		List<String> header = new ArrayList<String>();
		header.add("tax_id");
		header.add("entrez");
		header.add("gene_symbol");
		header.add("title");
		header.add("location");
		header.add("chrom");
		header.add("status");
		header.add("aliases");

		// === Data ===
		List<Object> data = new ArrayList<Object>();

		String suffixAnnotations = "_annotations"; 
		annotations = annotations.replaceAll("[\\[\\]]", "");

		if (annotations!=null && !annotations.isEmpty()) {

			List<String> idAnnotations = formatService.convertStringToList(annotations);
			suffixAnnotations = idAnnotations.toString().replaceAll("[\\]\\[,;]", "_");

			List<Gene> listGenes = geneRepository.findByAnnotationsAndTaxid(idAnnotations, taxid);

			for (Gene gene: listGenes) {
				Object [] dataline = new Object[header.size()];

				if (gene.getStatus().equals(GeneStatus.replaced)) {
					gene = geneRepository.findCurrentByUid(gene.getUid());
				}

				int i=0;
				dataline[i] = gene.getTaxId();
				dataline[++i] = gene.getUid();
				dataline[++i] = gene.getGeneSymbol();
				dataline[++i] = gene.getTitle();
				dataline[++i] = gene.getLocation();
				dataline[++i] = gene.getChrom();
				dataline[++i] = gene.getStatus();
				dataline[++i] = gene.getAliases();

				data.add(dataline);
			}
		}

		// ===== File generation =====

		String fileName = "";

		if (format!=null && format.equals("excel")) {
			fileName =  fileService.generateFileName("EpiMed_genes" + suffixAnnotations, "xlsx");
			logger.debug("Generated file name {}", fileName);
			response.setContentType("application/msexcel");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			fileService.writeExcelFile(response.getOutputStream(), header, data);
		}

		else {
			fileName =  fileService.generateFileName("EpiMed_genes" + suffixAnnotations, "csv");
			logger.debug("Generated file name {}", fileName);
			response.setContentType( "text/csv" );
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			fileService.writeCsvFile(response, header, data);
		}

		return null;

	}

	/** ======================================================================================  */

	@RequestMapping(value = "/query/genes/{jobtypeString}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> queryGenes (
			HttpServletRequest request,
			@PathVariable String jobtypeString,
			@RequestParam(value = "jobid", required = false) String jobid,
			@RequestParam(value = "taxid", defaultValue="9606") Integer taxid,
			@RequestParam(value = "symbols", required = false) String symbols,
			@RequestParam(value = "platform", defaultValue="GPL570") String platform,
			@RequestParam(value = "assembly", defaultValue="GRCh38") String assembly,
			@RequestParam(value = "positionType", defaultValue="unique") String positionType
			) throws InterruptedException, IOException {

		
		// String jobid = "No job created. Check your query.";
		
		// === Job Type ===
		
		JobType jobtype=null;
		try {
			jobtype = JobType.valueOf(jobtypeString);
		}
		catch (Exception e) {
			jobid = jobid + " Wrong URL (" + jobtypeString+ ").";
		}

		List<String> listElements = formatService.convertStringToList(symbols);
		if (listElements==null || listElements.isEmpty()) {
			jobid = jobid + " List of symbols is missing or empty.";
		}
		

		if (jobtype!=null && listElements!=null && !listElements.isEmpty() && taxid!=null) {

			jobid = jobService.createJob(jobtype, listElements, null);
			logService.log("API REST. New job started with jobid=" + jobid + ", jobtype=" + jobtypeString);

			// === Async thread ===

			geneQueryThread.setJobid(jobid);
			geneQueryThread.setJobtype(jobtype);
			geneQueryThread.setTaxid(taxid);
			geneQueryThread.setListElements(listElements);
			geneQueryThread.setAssembly(assembly);
			geneQueryThread.setPlatform(platform);
			geneQueryThread.setPositionType(positionType);
			Thread thread = new Thread(geneQueryThread);
			thread.start();
		}


		// === Return job id and status ===

		Map<String, Object> mapJob = new HashMap<String, Object>();
		mapJob.put("jobid", jobid);

		Job job = jobRepository.findOne(jobid);
		if (job!=null) {
			mapJob.put("status", job.getStatus());
		}
		else {
			mapJob.put("status", JobStatus.error);
		}

		return mapJob;

	}

	/** ====================================================================================== */
}
