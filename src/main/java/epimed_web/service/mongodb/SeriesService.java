package epimed_web.service.mongodb;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.model.Filters;

import epimed_web.repository.mongodb.SeriesRepository;
import epimed_web.service.util.FormatService;



@Service
public class SeriesService {

	@Autowired
	private SeriesRepository seriesRepository;

	@Autowired
	private FormatService formatService;


	/** ====================================================================================== */

	public List<Object> listWithNumberOfSamples() {
		return formatService.convertHomogeneousMongoDocuments(seriesRepository.listWithNumberOfSamples());
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

			// ===== Special case : _id =====

			if (key.toLowerCase().equals("id_series") 
					|| key.toLowerCase().equals("id")
					|| key.toLowerCase().equals("_id")
					|| key.toLowerCase().equals("series")
					|| key.toLowerCase().equals("main_gse_number")
					) {
				isSpecialKey = true;
				filter.add(Filters.in("_id", valueAsArray));
			}

			// ===== Special case : title =====

			if (key.toLowerCase().equals("title")) {
				isSpecialKey = true;
				filter.add(Filters.regex("title", ".*" + value +".*"));
			}



			if (!isSpecialKey && valueAsArray!=null) {
				filter.add(Filters.in(key, valueAsArray));
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


}
