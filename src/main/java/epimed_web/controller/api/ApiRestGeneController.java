package epimed_web.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
import epimed_web.form.AjaxForm;
import epimed_web.repository.mongodb.jobs.JobRepository;
import epimed_web.repository.neo4j.GeneRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.JobElementService;
import epimed_web.service.mongodb.JobService;
import epimed_web.service.neo4j.GeneService;
import epimed_web.service.neo4j.PositionService;
import epimed_web.service.neo4j.ProbesetService;
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
	private GeneService geneService;

	@Autowired
	private JobService jobService;

	@Autowired
	private JobElementService jobElementService;


	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private ProbesetService probesetService;

	@Autowired
	private PositionService positionService;


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
		header.add("type");
		header.add("aliases");

		// === Data ===
		List<Object> data = new ArrayList<Object>();

		String suffixAnnotations = "_annotations"; 
		annotations = annotations.replaceAll("[\\[\\]]", "");

		if (annotations!=null && !annotations.isEmpty()) {

			List<String> idAnnotations = formatService.convertStringToList(annotations);
			suffixAnnotations = idAnnotations.toString().replaceAll("[\\]\\[,;]", "_");
			suffixAnnotations = formatService.normalize(suffixAnnotations);

			List<Gene> listGenes = geneRepository.findCurrentByAnnotationsAndTaxid(idAnnotations, taxid);
			
			for (Gene gene: listGenes) {

				if (gene!=null) {

					Object [] dataline = new Object[header.size()];

					int i=0;
					dataline[i] = gene.getTaxId();
					dataline[++i] = gene.getUid();
					dataline[++i] = gene.getGeneSymbol();
					dataline[++i] = gene.getTitle();
					dataline[++i] = gene.getLocation();
					dataline[++i] = gene.getChrom();
					dataline[++i] = gene.getStatus();
					dataline[++i] = gene.getType();
					dataline[++i] = gene.getAliases();

					data.add(dataline);
				}
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
	@Async
	public void queryGenes (
			HttpServletRequest request,
			@PathVariable String jobtypeString,
			@RequestParam(value = "jobid", required = false) String jobid,
			@RequestParam(value = "taxid", defaultValue="9606") Integer taxid,
			@RequestParam(value = "symbols", required = false) String symbols,
			@RequestParam(value = "platform", defaultValue="GPL570") String platform,
			@RequestParam(value = "assembly", defaultValue="GRCh38") String assembly,
			@RequestParam(value = "positionType", defaultValue="unique") String positionType
			) throws InterruptedException, IOException {


		boolean validRequest = false;
		JobType jobtype=null;

		List<String> listElements = formatService.convertStringToList(symbols);
		Job job = jobRepository.findOne(jobid);

		logger.debug("listElements={}", listElements);
		logger.debug("job={}", job);

		if (jobid!=null
				&& !jobid.isEmpty() 
				&& taxid!=null
				&& listElements!=null && !listElements.isEmpty()
				&& job!=null && job.getType().equals(JobType.reserved)	
				) {

			try {
				jobtype = JobType.valueOf(jobtypeString);
				validRequest = true;
			}
			catch (Exception e) {
				validRequest = false;
			}

		}


		logger.debug("validRequest={}", validRequest);

		if (validRequest) {

			try  {

				jobService.updateJob(job, jobtype, JobStatus.init, listElements, null);


				for (int i=0; i<listElements.size(); i++) {

					String symbol = listElements.get(i);

					logger.debug("Processing {}", symbol);

					// === Update job status in progress ===
					if (i==0 || (i+1)%100==0) {
						logger.debug("*** Progress ***");
						logger.debug("Processed " + (i+1) + " elements of " + listElements.size());
						jobService.updateJob(job, i+1, JobStatus.progress, "Processed " + (i+1) + " elements of " + listElements.size());
					}

					// === Ajax form ===
					AjaxForm ajaxForm = new AjaxForm();
					ajaxForm.setSymbol(symbol);
					ajaxForm.setTaxid(taxid);
					ajaxForm.setJobtype(jobtype);
					ajaxForm.setIdPlatform(platform);

					// === Parameters ===
					if (jobtype.equals(JobType.probeset)) {
						ajaxForm.setParameter(platform);
					}
					if (jobtype.equals(JobType.position)) {
						ajaxForm.setParameter(assembly + "_" + positionType);
					}

					boolean success = geneService.populateBySymbolAndTaxid(ajaxForm);


					if (jobtype.equals(JobType.probeset)) {
						probesetService.populate(ajaxForm);
					}
					if (jobtype.equals(JobType.position)) {
						positionService.populate(ajaxForm);
					}

					ajaxForm.setSuccess(success);

					jobElementService.createJobElments(jobid, symbol, taxid, ajaxForm);

					// === Job terminated ===
					if (i==(listElements.size()-1)) {
						logger.debug("*** Job terminated ***");
						jobService.updateJob(job, i+1, JobStatus.success, null);
					}

				}

			}
			catch (Exception e) {
				logger.debug("*** Error ***");
				jobService.updateJob(job, 0, JobStatus.error, e.getMessage());
				e.printStackTrace();
			}
		}

	}


	/** ====================================================================================== */
}
