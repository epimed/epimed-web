package epimed_web.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import epimed_web.entity.neo4j.Gene;
import epimed_web.entity.neo4j.GeneStatus;
import epimed_web.repository.neo4j.GeneRepository;
import epimed_web.service.log.ApplicationLogger;
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

	/** ====================================================================================== */

	@RequestMapping(value = "/query/genes") 
	public Object queryGenes (
			@RequestParam(value = "taxid", defaultValue="9606") Integer taxid,
			@RequestParam(value = "annotations", defaultValue="") String annotations,
			@RequestParam(value = "format", defaultValue="csv") String format,
			HttpServletResponse response
			) throws IOException{

		
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

	/** ====================================================================================== */


}
