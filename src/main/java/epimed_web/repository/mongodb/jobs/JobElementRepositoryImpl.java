package epimed_web.repository.mongodb.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.service.log.ApplicationLogger;

public class JobElementRepositoryImpl extends ApplicationLogger implements JobElementRepositoryCustom {

	@Autowired
	public MongoTemplate mongoTemplate;

	@Autowired
	private MongoDatabase mongoDatabase;

	private String collectionName = "job_element";

	/** ================================================================================= */

	public List<Document> findByElementTaxidTypeParameter(String element, Integer taxid, JobType type, String parameter, int nbDaysExpiration) {

		Date currentDate = new Date();
		Date minDate = DateUtils.addDays(currentDate, -nbDaysExpiration);

		Bson filters = null;

		if (parameter!=null && !parameter.isEmpty()) {

			filters = Filters.and(
					Filters.eq("element", element),
					Filters.eq("tax_id", taxid),
					Filters.eq("type", type.name()),
					Filters.eq("parameter", parameter),
					Filters.gt("submission_date", minDate)
					);
		}
		else {
			filters = Filters.and(
					Filters.eq("element", element),
					Filters.eq("tax_id", taxid),
					Filters.eq("type", type.name()),
					Filters.gt("submission_date", minDate)
					);
		}

		List<Document> list = mongoDatabase.getCollection(collectionName)
				.find(filters)
				.into(new ArrayList<Document>());

		return list;
	}

	/** ================================================================================= */

	public List<Document> findByJobid(String jobid) {
		Bson filters = Filters.in("jobs", jobid);
		List<Document> list = mongoDatabase.getCollection(collectionName)
				.find(filters)
				.sort(Sorts.ascending("last_activity"))
				.into(new ArrayList<Document>());

		return list;
	}

	/** ================================================================================= */

	public void updateJobElement(Document docElement) {	
		ObjectId idJobElement = docElement.getObjectId("_id");
		mongoDatabase.getCollection(collectionName).updateOne(Filters.eq("_id", idJobElement), new Document("$set", docElement));
	}

	/** ================================================================================= */

}
