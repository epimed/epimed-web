package epimed_web.repository.mongodb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

@SuppressWarnings("unchecked")
public class PlatformRepositoryImpl implements PlatformRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private MongoDatabase mongoDatabase;
	
	private String collectionName = "platform";
	
	/** ======================================================================================*/

	public List<String> listPlatformTypes () {
		
		List<String> listSamples = mongoTemplate.getCollection(collectionName).distinct("type");
		Collections.sort(listSamples);
		return listSamples;
	}
	
	/** ======================================================================================*/

	public List<Document> list() {
		List<Document>	list = mongoDatabase.getCollection(collectionName)
				.find()
				.sort(Sorts.ascending("_id"))
				.into(new ArrayList<Document>());
		return list;
	}

	/** ======================================================================================*/

	public List<Document> list (Bson filter) {

		List<Document> list = new ArrayList<Document>();

		if (filter==null) {
			list = this.list();
		}
		else {
			list = mongoDatabase.getCollection(collectionName)
					.find(filter)
					.sort(Sorts.ascending("_id"))
					.into(new ArrayList<Document>());
		}

		return list;
	}


	/** ======================================================================================*/

	public List<Document> listPlatforms(String type) {

		if (type==null || type.isEmpty()) {
			return null;
		}

		Bson filter = Filters.eq("type", type);

		List<Document> list = mongoDatabase.getCollection(collectionName)
				.find(filter)
				.sort(Sorts.orderBy(Sorts.ascending("type"), Sorts.ascending("_id")))
				.into(new ArrayList<Document>());

		return list;
	}

	/** ======================================================================================*/

	public List<String> listIdPlatforms(String type) {

		Bson filter = null;
		if (type!=null)  {
			filter = Filters.eq("type", type);
		}

		List<String> list = mongoDatabase.getCollection(collectionName)
				.distinct("_id", filter, String.class)
				.into(new ArrayList<String>());

		return list;
	}

	/** ======================================================================================*/
	
}
