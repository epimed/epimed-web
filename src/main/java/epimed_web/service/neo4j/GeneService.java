package epimed_web.service.neo4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epimed_web.entity.neo4j.Gene;
import epimed_web.entity.neo4j.GeneStatus;
import epimed_web.form.AjaxForm;
import epimed_web.repository.neo4j.GeneRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.util.FormatService;
import epimed_web.service.util.WebService;

@Service
public class GeneService extends ApplicationLogger {


	@Autowired
	private FormatService formatService;

	@Autowired 
	private GeneRepository geneRepository;

	@Autowired
	private WebService webService;

	/** =============================================================================== */

	/**
	 * @param ajaxForm
	 * @return true (success) if genes found, false (fail) otherwise 
	 */

	public boolean populateBySymbolAndTaxid(AjaxForm ajaxForm) {

		String symbol = ajaxForm.getSymbol();
		Integer taxid = ajaxForm.getTaxid();

		List<Gene> genes = new ArrayList<Gene>();


		// === Search by Gene ID Entrez ===

		Integer idGene = formatService.convertStringToInteger(symbol);
		if (idGene!=null) {
			genes = geneRepository.findCurrentByIdGeneAndTaxid(idGene, taxid);	
			if (genes.isEmpty()) {
				Set<String> setIdGenes = new HashSet<String>();
				setIdGenes.add(idGene.toString());
				this.addGenes(setIdGenes, taxid, genes);
			}
			if (genes!=null && !genes.isEmpty()) {
				ajaxForm.setListGenes(genes);
				ajaxForm.setSource("EpiMed GENUID");
				return true;
			}
			return false;
		}


		// === Search by gene symbol ===

		genes = geneRepository.findCurrentByGeneSymbolAndTaxid(symbol, taxid);
		if (genes!=null && !genes.isEmpty()) {
			ajaxForm.setListGenes(genes);
			ajaxForm.setSource("EpiMed GENSYM");
			return true;
		}

		// === Search by alias ===

		genes = geneRepository.findCurrentByAliasAndTaxid(symbol, taxid);
		if (genes!=null && !genes.isEmpty()) {
			ajaxForm.setListGenes(genes);
			ajaxForm.setSource("EpiMed GENALIAS");
			return true;
		}

		// === Search by nucleotide ===

		genes = geneRepository.findCurrentByNucleotideAndTaxid(symbol, taxid);
		if (genes!=null && !genes.isEmpty()) {
			ajaxForm.setListGenes(genes);
			ajaxForm.setSource("EpiMed NUC");
			return true;
		}

		// === Search by probeset ===
		genes = geneRepository.findCurrentByProbesetAndTaxid(symbol, taxid);
		if (genes!=null && !genes.isEmpty()) {
			ajaxForm.setListGenes(genes);
			ajaxForm.setSource("EpiMed PROBESET");
			return true;
		}

		// === Search by protein ===
		genes = geneRepository.findCurrentByProteinSequenceAndTaxid(symbol, taxid);
		if (genes!=null && !genes.isEmpty()) {
			ajaxForm.setListGenes(genes);
			ajaxForm.setSource("EpiMed PROTEIN");
			return true;
		}

		// === Search by position ===
		genes = geneRepository.findCurrentByPositionAndTaxid(symbol, taxid);
		if (genes!=null && !genes.isEmpty()) {
			ajaxForm.setListGenes(genes);
			ajaxForm.setSource("EpiMed POSITION");
			return true;
		}


		// === Search a list of idGene on NCBI ===

		String [] sources = {"GENE", "ACCN", "UGEN", "ALL"};
		Set<String> listIdGeneString = new HashSet<String>();
		String sourceField = sources[0];
		int i=0;
		boolean isFound = false;
		while (!isFound && i<sources.length) {
			sourceField = sources[i];
			listIdGeneString.addAll(this.findListIdGeneFromNcbi(symbol, sourceField, taxid));
			isFound = listIdGeneString!=null && !listIdGeneString.isEmpty();
			i++;
		}

		this.addGenes(listIdGeneString, taxid, genes);

		if (genes!=null && !genes.isEmpty()) {
			ajaxForm.setListGenes(genes);
			ajaxForm.setSource("NCBI " + sourceField);
			return true;
		}



		// === Recognize a symbol from Ensembl ===

		Set<String> setIdEnsembl = new HashSet<String>();
		List<Document> listDocEnsembl = this.findIdEnsemblFromEnsembl(symbol, taxid, "gene");
		for (Document docEnsembl: listDocEnsembl) {
			setIdEnsembl.add(docEnsembl.getString("id"));
		}

		// === Search a list of idEnsemble into idGene  ===

		Set<String> setIdGenes = new HashSet<String>();
		for (String idEnsembl: setIdEnsembl) {	
			String [] sourcesEnsembl = {"WikiGene", "EntrezGene"};
			String sourceFieldEnsembl = sourcesEnsembl[0];
			i=0;
			isFound = false;
			while (!isFound && i<sourcesEnsembl.length) {
				sourceFieldEnsembl = sourcesEnsembl[i];
				List<Document> listDocIdGenes= this.findListIdGeneFromEnsembl(idEnsembl, sourceFieldEnsembl);
				for (Document docIdGene: listDocIdGenes) {
					setIdGenes.add(docIdGene.getString("primary_id"));
				}
				isFound = listDocIdGenes!=null && !listDocIdGenes.isEmpty();
				i++;
			}

			this.addGenes(setIdGenes, taxid, genes);

		}

		if (genes!=null && !genes.isEmpty()) {
			ajaxForm.setListGenes(genes);
			ajaxForm.setSource("Ensembl");
			return true;
		}


		return false;

	}

	/** ================================================================================ */

	/**
	 * 
	 * @param symbol 
	 * @param symbolField 
	 * 		ACCN nucleotide
	 * 		GENE gene name
	 * 		UGEN unigene
	 * @param taxid
	 * @return
	 */

	@SuppressWarnings({ "unchecked"})
	private List<String> findListIdGeneFromNcbi (String symbol, String symbolField, Integer taxid) {

		try {
			String urlnuc = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=gene&term=" + symbol 
					+"[" + symbolField + "]+AND+" + taxid +"[TID]&retmode=json";
			logger.debug("{}", urlnuc);
			String jsonnuc = webService.loadUrl(urlnuc);
			Document docnuc = Document.parse(jsonnuc);
			List<String>  listIdGene = docnuc.get("esearchresult", Document.class).get("idlist", ArrayList.class);
			return listIdGene;
		}
		catch (Exception e) {
			logger.warn("Exception while searching a symbol " + symbol + " from NCBI");
			logger.warn("ERROR:" + e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	/** =============================================================================== */
	/**
	 * @param symbol
	 * @param taxid
	 * @param objectType 
	 * may by "gene", "transcript" or "translation"
	 * if null, return all object types
	 * @return
	 * Example: for symbol = A1L443 (protein uniprot) the output will be
	 * [{"type":"gene","id":"ENSG00000130950"},{"type":"transcript","id":"ENST00000253262"},{"type":"translation","id":"ENSP00000253262"}]
	 */

	@SuppressWarnings("unchecked")
	private List<Document> findIdEnsemblFromEnsembl(String symbol, Integer taxid, String objectType) {

		List<Document> result = new ArrayList<Document>();

		try {
			String organism = null;
			if (taxid.equals(9606)) {
				organism = "homo_sapiens";
			}

			if (taxid.equals(10090)) {
				organism = "mus_musculus";
			}

			if (organism==null) {
				throw new Exception ("Taxid " + taxid + " not found");
			}

			String urlnuc = "https://rest.ensembl.org/xrefs/symbol/" + organism + "/" + symbol + "?content-type=application/json";
			if (objectType!=null && !objectType.isEmpty()) {
				urlnuc = urlnuc + ";object_type=" + objectType;	
			}			
			logger.debug("{}", urlnuc);
			String jsonnuc = webService.loadUrl(urlnuc);

			if (jsonnuc!=null && !jsonnuc.isEmpty() && jsonnuc.startsWith("[") && jsonnuc.endsWith("]") && jsonnuc.length()>3) {
				jsonnuc = "{root: " + jsonnuc + "}";
				Document docnuc = Document.parse(jsonnuc);
				result = (List<Document>) docnuc.get("root");
			}

			return result;

		}
		catch (Exception e) {
			logger.warn("Exception while searching a symbol " + symbol + " from Ensembl");
			logger.warn("ERROR:" + e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	/** =============================================================================== */

	@SuppressWarnings("unchecked")
	private List<Document> findListIdGeneFromEnsembl(String symbol, String dbname) {

		List<Document> result = new ArrayList<Document>();

		try {

			String urlnuc = "https://rest.ensembl.org/xrefs/id/" + symbol + "?content-type=application/json";
			if (dbname!=null && !dbname.isEmpty()) {
				urlnuc = urlnuc + ";external_db=" + dbname;
			}
			logger.debug("{}", urlnuc);
			String jsonnuc = webService.loadUrl(urlnuc);

			if (jsonnuc!=null && !jsonnuc.isEmpty() && jsonnuc.startsWith("[") && jsonnuc.endsWith("]") && jsonnuc.length()>3) {
				jsonnuc = "{root: " + jsonnuc + "}";
				Document docnuc = Document.parse(jsonnuc);
				result = (List<Document>) docnuc.get("root");
			}

			return result;
		}
		catch (Exception e) {
			logger.warn("Exception while searching a symbol " + symbol + " from Ensembl");
			logger.warn("ERROR:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/** =============================================================================== */

	private Gene findOrCreateActiveGeneIfPossible(Integer idGene) {

		// === Check in the database ===
		Gene gene = geneRepository.findCurrentByUid(idGene);

		// === Check on the web and manage replaced removed genes ===
		if (gene==null) {
			gene = this.createGeneFromNcbi(idGene);
			while (gene!=null && gene.getReplacedBy()!=null) {
				Integer currentId = gene.getReplacedBy();	
				gene = this.createGeneFromNcbi(currentId);
			}
		}

		return gene;
	}

	/** =============================================================================== */

	/**
	 * This method is private
	 * It is a part of the parent method: public Gene findOrCreateActiveGene(Integer idGene)
	 * Cannot be used separately
	 * @param idGene
	 * @return
	 */
	private Gene createGeneFromNcbi (Integer idGene) {

		try {

			// === Check in the database ===
			Gene gene = geneRepository.findByUid(idGene);

			if (gene==null) {

				String urlgene = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=gene&id=" + idGene + "&retmode=json";
				String jsongene = webService.loadUrl(urlgene);



				Document docgenefull = Document.parse(jsongene);
				Document docgene = docgenefull.get("result", Document.class).get(idGene.toString(), Document.class);

				gene = new Gene();
				gene.setUid(idGene);
				gene.setGeneSymbol(docgene.getString("name"));

				// === Chrom === 
				String chrom = docgene.getString("chromosome");
				if (chrom!=null && !chrom.isEmpty()) {
					gene.setChrom(chrom);
				}

				// === Location ===

				String location = docgene.getString("maplocation");
				if (location!=null) {
					gene.setLocation(location);
				}

				// === Taxid ===
				Document docorganism = (Document) docgene.get("organism");
				gene.setTaxId(docorganism.getInteger("taxid"));

				// === Title ===

				gene.setTitle(docgene.getString("description"));

				// === Status ===

				Object status = docgene.get("status");
				if (status==null || status.equals("")) {
					gene.setStatus(GeneStatus.active);
				}
				else {
					if (status.equals("1") || status.equals(1)) {
						gene.setStatus(GeneStatus.replaced);
						Object currentid = docgene.get("currentid");
						if (currentid!=null) {
							Integer intCurrentid = null;
							try {
								intCurrentid = (Integer) currentid;
							}
							catch (ClassCastException e1) {
								try {
									intCurrentid = Integer.parseInt( (String) currentid);
								}
								catch (ClassCastException e2) {
									e2.printStackTrace();
								}
							}
							if (intCurrentid!=null) {
								gene.setReplacedBy(intCurrentid);
							}
						}
					}
					if (status.equals("2") || status.equals(2)) {
						gene.setStatus(GeneStatus.removed);
					}
				}

				// === Aliases ===
				String otheraliases = docgene.getString("otheraliases");
				if (otheraliases!=null && !otheraliases.isEmpty()) {
					String [] arrayAliases = formatService.convertStringToArray(otheraliases);
					gene.getAliases().addAll(Arrays.asList(arrayAliases));
				}

				// === Save new gene in the database ===
				geneRepository.save(gene);

				logger.debug("A new gene has been created " + gene);
			}

			return gene;
		}
		catch (Exception e) {
			logger.warn("Exception while creating a new gene with UID " + idGene + " from NCBI");
			logger.warn("ERROR:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}


	/** =============================================================================== */

	public void addGenes (Collection<String> listIdGeneString, Integer taxid, List<Gene> genes) {
		
		Set<Gene> setGenes = new HashSet<Gene>();
		
		if (listIdGeneString!=null && taxid!=null) {

			for (String idGeneString: listIdGeneString) {
				try {
					Integer idGene = Integer.parseInt(idGeneString);
					Gene gene = this.findOrCreateActiveGeneIfPossible(idGene);
					if (gene!=null && gene.getTaxId()!=null && gene.getTaxId().equals(taxid)) {
						setGenes.add(gene);
					}
				}
				catch (Exception e) {
					// nothing to do, skip 
				}
			}
		}
		
		if (!setGenes.isEmpty()) {
			genes.addAll(setGenes);
		}
	}

	/** =============================================================================== */


}
