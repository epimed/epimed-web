package epimed_web.service.mongodb;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.model.Filters;

import epimed_web.repository.mongodb.PlatformRepository;
import epimed_web.service.util.FormatService;


@Service
public class SampleService {

	@Autowired
	private FormatService formatService;

	@Autowired
	private PlatformRepository platformRepository;


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

