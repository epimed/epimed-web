package epimed_web.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epimed_web.service.log.ApplicationLogger;

@Service
public class NcbiService extends ApplicationLogger {

	@Autowired
	private WebService webService;
	
	/** =============================================================================== */

	public Document eSummary(String database, String id) {

		String url = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=" + database + "&id="+ id + "&retmode=json";
		logger.debug("URL {}", url);
		try {
			String json = webService.loadUrl(url);
			if (json!=null && !json.isEmpty()) {
				Document doc = Document.parse(json);
				Document summary = doc.get("result", Document.class).get(id, Document.class);
				return summary;
			}
			return null;
		}
		catch (Exception e) {
			System.out.println("Exception while searching an ID " + id + " on NCBI");
			System.out.println("URL " + url);
			System.out.println("ERROR:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/** =============================================================================== */

	@SuppressWarnings("unchecked")
	public List<String> eSearch (String database, String term, Integer retmax) {

		String url = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=" + database + "&term="+ term;
		if (retmax!=null) {
			url = url + "&retmax=" + retmax;
		}
		url = url + "&retmode=json";

		try {
			String json = webService.loadUrl(url);
			if (json!=null && !json.isEmpty()) {
				Document doc = Document.parse(json);
				List<String>  listIdGene = doc.get("esearchresult", Document.class).get("idlist", ArrayList.class);
				return listIdGene;
			}
			return null;
		}
		catch (Exception e) {
			System.out.println("Exception while searching a term " + term + " on NCBI");
			System.out.println("URL " + url);
			System.out.println("ERROR:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/** ====================================================================================== */

	public List<String> loadGeo(String geoAccession) {

		String url = "https://www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc=" + geoAccession + "&targ=self&view=brief&form=text";

		String text = webService.loadUrl(url);
		String [] parts = text.split(System.getProperty( "line.separator" ));
		List<String> data = new ArrayList<String>(Arrays.asList(parts));

		return data;	
	}


	/** =============================================================================== */
	
}
