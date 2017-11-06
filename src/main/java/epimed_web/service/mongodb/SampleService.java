package epimed_web.service.mongodb;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.model.Filters;

import epimed_web.entity.bind.NcbiGeoGsm;
import epimed_web.entity.mongodb.experiments.Sample;
import epimed_web.repository.mongodb.experiments.PlatformRepository;
import epimed_web.repository.mongodb.experiments.SampleRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.util.FormatService;


@Service
public class SampleService extends ApplicationLogger {

	@Autowired
	private FormatService formatService;

	@Autowired
	private PlatformRepository platformRepository;

	@Autowired
	private SampleRepository sampleRepository;


	/** ====================================================================================== */

	public void deleteSamples(String idSeries) {

		List<Sample> samples = sampleRepository.findInSeries(new String [] {idSeries});

		for (Sample sample: samples) {
			Set<String> series = new HashSet<String>();
			series.addAll(sample.getSeries());

			// Sample belongs only to one series, can be removed
			if (series!=null && series.size()==1) {
				sampleRepository.delete(sample);
			}

			// Sample belongs to other series, should be kept
			if (series!=null && series.size()>1) {
				series.remove(idSeries);
				sample.setSeries(series);;
				sampleRepository.save(sample);
			}


		}

	}

	/** ============================================================================================ */

	public Sample createSample(NcbiGeoGsm gsm, String idSeries) {

		Sample sample = this.generateSample(gsm, idSeries);

		Sample existingSample = sampleRepository.findOne(sample.getId());

		if (existingSample!=null) {
			sample.setExpGroup(existingSample.getExpGroup());
			sample.getSeries().addAll(existingSample.getSeries());
			sample.setMainGseNumber(existingSample.getMainGseNumber());
			sample.setAnalyzed(existingSample.getAnalyzed());
		}
		else {
			Document expgroup = this.generateExpGroup(gsm, idSeries);
			sample.setExpGroup(expgroup);
		}

		Document parameters = this.generateParameters(gsm);
		sample.setParameters(parameters);

		logger.debug("ExpGroup: {}", sample.getExpGroup());
		logger.debug("Parameters: {}", sample.getParameters());

		return sample;

	}

	/** ============================================================================================ */

	private Sample generateSample (NcbiGeoGsm gsm, String idSeries) {

		Sample sample = new Sample();

		sample.setId(gsm.getGsmNumber());
		sample.setMainGseNumber(idSeries);
		sample.getSeries().addAll(gsm.getListGse());
		sample.setOrganism(gsm.getOrganism());
		sample.setLastUpdate(gsm.getLastUpdate());
		sample.setSubmissionDate(gsm.getSubmissionDate());
		sample.setImportDate(new Date());
		sample.setAnalyzed(false);
		return sample;
	}



	/** ============================================================================================ */

	private Document generateExpGroup(NcbiGeoGsm gsm, String idSeries) {

		Document expGroup = new Document();

		expGroup
		.append("id_sample", gsm.getGsmNumber())
		.append("main_gse_number", idSeries)
		.append("id_platform", gsm.getGplNumber())
		.append("sample_title", gsm.getTitle())
		.append("sample_source", gsm.getSourceName())
		.append("sex", null)
		.append("ethnic_group", null)
		.append("age_min", null)
		.append("age_max", null)
		.append("id_tissue_stage", null)
		.append("tissue_stage", null)
		.append("id_tissue_status", null)
		.append("tissue_status", null)
		.append("id_pathology", null)
		.append("pathology", null)
		.append("collection_method", null)
		.append("id_topology", null)
		.append("topology", null)
		.append("id_topology_group", null)
		.append("topology_group", null)
		.append("id_morphology", null)
		.append("morphology", null)
		.append("histology_type", null)
		.append("histology_subtype", null)
		.append("t", null)
		.append("n", null)
		.append("m", null)
		.append("tnm_stage", null)
		.append("tnm_grade", null)
		.append("dfs_months", null)
		.append("os_months", null)
		.append("relapsed", null)
		.append("dead", null)
		.append("treatment", null)
		.append("exposure", null)
		;

		return expGroup;
	}

	/** =============================================================== */

	public Document generateParameters(NcbiGeoGsm gsm) {
		Document parameters = new Document();
		parameters.append("id_sample", gsm.getGsmNumber());
		this.appendParameters(parameters, gsm.getDescription());
		this.appendParameters(parameters, gsm.getListCharacteristics());
		// parameters.append("extract_protocol", gsm.getExtractProtocol())
		;
		return parameters;
	}

	/** ============================================================================================ */


	public void appendParameters(Document doc, List<String> list) {

		// String regex = "[:]";
		String regex = "[:=]";
		List<String> listText = new ArrayList<String>();

		for (String rawLine : list) {

			// String [] lines = rawLine.split(","); // Split into several entries
			String [] lines = rawLine.split("[,;]"); // Split into several entries

			// System.out.println("------------------------------------------");
			// System.out.println("rawLine=" + rawLine);
			// System.out.println("lines=" + Arrays.toString(lines));


			for (String line : lines) {

				line = line.trim();

				if (line.contains(":") || line.contains("=")
						) {

					String [] parts = line.split(regex);

					if (parts!=null && parts.length>1) {

						String key = parts[0].trim();
						key = key.replaceAll("\\.", " ");
						String value = "";

						for (int i=1; i<parts.length; i++) {
							value = value + parts[i].trim();
							if (i!=parts.length-1) {
								value =value  + ": ";
							}
						}
						value = value.trim();
						String existingValue = doc.getString(key);
						if (existingValue!=null && !existingValue.isEmpty()) {
							value = existingValue + ", " + value;
						}
						doc.append(key, value);
						// System.out.println(key + "=" + value);
					}
				}

				else {
					listText.add(line);
				}
			}
		}

		if (!listText.isEmpty()) {
			doc.append("text", listText.toString().replaceAll("[\\[\\]]", ""));
		}

	}


	/** ====================================================================================== */

	public Bson createFilter(Map<String, String> mapRequestParameters) {

		List<Bson> filter = new ArrayList<Bson>();

		for (Map.Entry<String, String> requestParameter : mapRequestParameters.entrySet()) {

			String key = requestParameter.getKey();
			String value = requestParameter.getValue();
			boolean isSpecialKey = false;


			// ===== Formats =====
			String [] valueAsArray = formatService.convertStringToArray(value);
			Integer valueAsInt = formatService.convertStringToInteger(value);
			List<Integer> valueAsIntList = formatService.convertStringArrayToIntegerList(valueAsArray);
			List<Boolean> valueAsBooleanList = formatService.convertStringArrayToBooleanList(valueAsArray);

			// ===== Special case : Survival =====

			if (key.toLowerCase().equals("survival")) {
				isSpecialKey = true;
				if (value.isEmpty() || value.toLowerCase().equals("true")) {
					filter.add(Filters.or(
							Filters.ne("exp_group.os_months", null),
							Filters.ne("exp_group.dfs_months", null),
							Filters.ne("exp_group.relapsed", null),
							Filters.ne("exp_group.dead", null)
							));
				}
			}

			// ===== Special case : Series ===== 

			if (key.toLowerCase().equals("series") && valueAsArray!=null) {
				isSpecialKey = true;
				filter.add(Filters.in("series", valueAsArray));	
			}


			// ===== Special case : Platform type ===== 

			if (key.toLowerCase().equals("platform_type") && value!=null) {
				isSpecialKey = true;
				List<String> listIdPlatforms = platformRepository.listIdPlatforms(value);
				filter.add(Filters.in("exp_group.id_platform", listIdPlatforms));
			}

			// ===== Special case : Age min ===== 

			if (key.toLowerCase().equals("age_min") && valueAsInt!=null) {
				isSpecialKey = true;
				filter.add(Filters.gte("exp_group.age_min", valueAsInt));
			}

			// ===== Special case : Age max ===== 

			if (key.toLowerCase().equals("age_max") && valueAsInt!=null) {
				isSpecialKey = true;
				filter.add(Filters.lte("exp_group.age_max", valueAsInt));
			}

			// ====== Any exp_group parameters ======

			if (!isSpecialKey && valueAsArray!=null) {

				// === Value is a list ===

				if (valueAsIntList!=null) {
					filter.add(Filters.in("exp_group." + key, valueAsIntList));
				}
				else {

					if (valueAsBooleanList!=null) {
						filter.add(Filters.in("exp_group." + key, valueAsBooleanList));
					}
					else {
						filter.add(Filters.in("exp_group." + key, valueAsArray));
					}
				}
			}
		}

		// === Final global filter ===

		if (filter!=null && !filter.isEmpty()) {
			Bson result = Filters.and(filter);
			return result;
		}

		return null;
	}


	/** ====================================================================================== */

	public Bson createFilter(String idPlatformType, String idPlatform, String idTopology, Integer idTissueStatus, 
			Integer idTissueStage, String idMorphology, Boolean survival) {

		List<Bson> filter = new ArrayList<Bson>();

		// === Platforms ===
		if (idPlatform!=null && !idPlatform.isEmpty()) {
			filter.add(Filters.eq("exp_group.id_platform", idPlatform));
		}
		else {
			if (idPlatformType!=null && !idPlatformType.isEmpty()) {
				List<String> listIdPlatforms = platformRepository.listIdPlatforms(idPlatformType);
				filter.add(Filters.in("exp_group.id_platform", listIdPlatforms));
			}
		}

		// === Topology ===
		if (idTopology!=null && !idTopology.isEmpty()) {
			filter.add(Filters.eq("exp_group.id_topology", idTopology));
		}

		// === Tissue status ===
		if (idTissueStatus!=null) {
			filter.add(Filters.eq("exp_group.id_tissue_status", idTissueStatus));
		}

		// === Tissue stage ===
		if (idTissueStage!=null) {
			filter.add(Filters.eq("exp_group.id_tissue_stage", idTissueStage));
		}

		// === Morphology ===
		if (idMorphology!=null && !idMorphology.isEmpty()) {
			filter.add(Filters.eq("exp_group.id_morphology", idMorphology));
		}

		// === With Survival ===
		if (survival!=null && survival) {
			filter.add(Filters.or(
					Filters.ne("exp_group.os_months", null),
					Filters.ne("exp_group.dfs_months", null),
					Filters.ne("exp_group.relapsed", null),
					Filters.ne("exp_group.dead", null)
					));
		}

		// === Final global filter ===

		Bson result = Filters.and(filter);

		return result;

	}
}

