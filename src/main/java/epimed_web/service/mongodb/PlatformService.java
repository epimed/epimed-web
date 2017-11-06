package epimed_web.service.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.model.Filters;

import epimed_web.entity.bind.NcbiGeoGpl;
import epimed_web.entity.mongodb.experiments.Platform;
import epimed_web.repository.mongodb.experiments.PlatformRepository;
import epimed_web.service.util.FormatService;
import epimed_web.service.util.NcbiService;

@Service
public class PlatformService {

	@Autowired
	private FormatService formatService;

	@Autowired
	private NcbiService ncbiService;

	@Autowired
	private PlatformRepository platformRepository;

	/** ====================================================================================== */

	public void createPlatforms (Set<String> idPlatforms) {

		if (idPlatforms!=null) {
			for (String idPlatform: idPlatforms) {

				Platform platform = platformRepository.findOne(idPlatform);

				if (platform==null) {
					
					platform = new Platform();
					platform.setId(idPlatform);

					NcbiGeoGpl gpl = new NcbiGeoGpl(ncbiService.loadGeo(idPlatform));
					if (gpl!=null) {
						platform.setTitle(gpl.getTitle());

						if (gpl.getTaxid()!=null && gpl.getTaxid().isEmpty()) {
							try {
							platform.setIdOrganism(Integer.parseInt(gpl.getTaxid()));
							}
							catch (Exception e) {
								// nothing to do
							}
						}
						
						platform.setOrganism(gpl.getOrganism());
						platform.setManufacturer(gpl.getManufacturer());
						platform.setSubmissionDate(gpl.getSubmissionDate());
						platform.setLastUpdate(gpl.getLastUpdate());
						platform.setImportDate(new Date());
						platform.setTechnology(gpl.getTechnology());
						platform.setType("unknown");
						
						platformRepository.save(platform);
					}
				}
			}
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

			// ===== Special case : _id =====

			if (key.toLowerCase().equals("id_platform") 
					|| key.toLowerCase().equals("id")
					|| key.toLowerCase().equals("_id")
					|| key.toLowerCase().equals("platforms")
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
