/**
 * EpiMed - Information system for bioinformatics developments in the field of epigenetics
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
package epimed_web.service.util;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.springframework.stereotype.Service;

import epimed_web.entity.mongodb.experiments.Sample;
import epimed_web.service.log.ApplicationLogger;

@Service
public class FormatService extends ApplicationLogger {

	/** ================================================================================= */

	public Integer convertStringToInteger (String text) {
		try {
			int entryInt = Integer.parseInt(text);
			return entryInt;
		}
		catch(Exception ex) {
			return null;
		}
	}

	/** ================================================================================= */

	public List<Boolean> convertStringArrayToBooleanList (String [] stringArray) {

		List<Boolean> result = new ArrayList<> ();

		if (stringArray!=null && stringArray.length>0) {
			for (String entryString : stringArray) {

				try {

					if(entryString.toLowerCase().equals("true")) {
						result.add(true);
					}

					if(entryString.toLowerCase().equals("false")) {
						result.add(false);
					}

				}
				catch(Exception ex) {
					// nothing to do
				}
			}
		}

		if (result.isEmpty()) {
			return null;
		}

		return result;
	}

	/** ================================================================================= */

	public List<Integer> convertStringArrayToIntegerList (String [] stringArray) {

		List<Integer> integerList = new ArrayList<> ();

		if (stringArray!=null && stringArray.length>0) {
			for (String entryString : stringArray) {

				try {
					int entryInt = Integer.parseInt(entryString);
					integerList.add(entryInt);
				}
				catch(Exception ex) {
					// nothing to do
				}
			}
		}

		if (integerList.isEmpty()) {
			return null;
		}

		return integerList;
	}

	/** ================================================================================= */

	public String [] convertStringToArray (String list) {
		String [] array = null;

		if (list!=null && !list.isEmpty()) {
			array = list.trim().replaceAll("'\"]", "").split("[,;:\\p{Space}][\\p{Space}]*");
		}

		return array;
	}

	/** ================================================================================= */

	public List<String> convertStringToList (String list) {
		List<String> result = null;

		String [] array = this.convertStringToArray(list);

		try {
			result = Arrays.asList(array);
		}
		catch (Exception e) {
			// nothing to do
		}
		return result;
	}


	/** ================================================================================= */

	public List<Object> convertHomogeneousMongoDocuments (List<Document> listDocuments) {

		List<Object> data = new ArrayList<Object>();
		List<String> header = new ArrayList<String>();

		try  {

			// ===== Extract header =====
			header.addAll(listDocuments.get(0).keySet());

			// ===== Extract data =====
			for (Document doc : listDocuments) {
				Object [] dataLine = new Object [header.size()];

				for (int j=0; j<header.size(); j++) {
					dataLine[j] = (Object) doc.get(header.get(j));
				}
				data.add(dataLine);
			}
		}
		catch (Exception e) {
			logger.debug("ERROR in {}: {}", this.getClass().getName(), "convertHomogeneousMongoDocuments");
		}

		return data;

	}

	/** ================================================================================= */

	public List<Object> convertHeterogeneousMongoDocuments (List<Document> listDocuments) {

		List<Object> data = new ArrayList<Object>();
		Set<String> headerSet = new HashSet<String>();
		List<String> header = new ArrayList<String>();

		try  {

			// ===== Extract header =====
			for (Document doc : listDocuments) {
				headerSet.addAll(doc.keySet());
			}
			header.addAll(headerSet);
			Collections.sort(header);


			// ===== Extract data =====
			for (Document doc : listDocuments) {
				Object [] dataLine = new Object [header.size()];

				for (int j=0; j<header.size(); j++) {
					dataLine[j] = (Object) doc.get(header.get(j));
				}
				data.add(dataLine);
			}
		}
		catch (Exception e) {
			logger.debug("ERROR in {}: {}", this.getClass().getName(), "convertHeterogeneousMongoDocuments");
		}

		return data;

	}

	/** ================================================================================= */

	public List<String> extractHeader (List<Document> listDocuments, String rootDocumentName) {


		List<String> header = new ArrayList<String>();

		try  {


			// ===== Extract header =====

			for (Document doc : listDocuments) {

				if (rootDocumentName!=null) {
					doc = (Document) doc.get(rootDocumentName);
				}

				for (String key : doc.keySet()) {
					if (!header.contains(key)) {
						header.add(key);
					}
				}
			}
		}
		catch (Exception e) {
			logger.debug("ERROR in {}: {}", this.getClass().getName(), "extractHeader");
		}

		return header;
	}

	/** ================================================================================= */

	public List<Object> extractData (List<Document> listDocuments, List<String> header, String rootName) {


		List<Object> data = new ArrayList<Object>();

		try  {


			// ===== Extract data =====
			for (Document doc : listDocuments) {

				if (rootName!=null) {
					doc = (Document) doc.get(rootName);
				}

				Object [] dataLine = new Object [header.size()];

				for (int j=0; j<header.size(); j++) {
					dataLine[j] = (Object) doc.get(header.get(j));
				}
				data.add(dataLine);
			}
		}
		catch (Exception e) {
			logger.debug("ERROR in {}: {}", this.getClass().getName(), "extractData");
			logger.debug("RootName: {}", rootName);
			logger.debug("Header: {}", header);
			e.printStackTrace();
		}

		return data;

	}


	/** ================================================================================= */

	public Set<String> extractSampleHeader (List<Sample> listSamples, String rootName) {
		Set<String> header = new LinkedHashSet<String>();
		for (Sample sample : listSamples) {
			Document doc = rootName.equals("parameters") ? sample.getParameters() : sample.getExpGroup();
			header.addAll(doc.keySet());
		}
		return header;
	}

	/** ================================================================================= */

	public List<Object> extractSampleData (List<Sample> listSamples, Collection<String> header, String rootName) {

		List<Object> data = new ArrayList<Object>();

		for (Sample sample : listSamples) {

			Document doc = rootName.equals("parameters") ? sample.getParameters() : sample.getExpGroup();

			Object [] dataLine = new Object [header.size()];

			int j=0;
			for (String item : header) {
				dataLine[j] = (Object) doc.get(item);
				j++;
			}
			data.add(dataLine);
		}

		return data;
	}

	/** ================================================================================= */

	public static String flattenToAscii(String string) {
		char[] out = new char[string.length()];
		string = Normalizer.normalize(string, Normalizer.Form.NFD);
		int j = 0;
		for (int i = 0, n = string.length(); i < n; ++i) {
			char c = string.charAt(i);
			if (c <= '\u007F') out[j++] = c;
		}
		return new String(out);
	}

	/** ================================================================================= */

	public String normalize(String entry) {
		return flattenToAscii(entry).replaceAll("[\\p{Punct}\\p{Space}*]", "_").toLowerCase();
	}


	/** ================================================================================= */

}
