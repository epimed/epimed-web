package epimed_web.repository.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class SeriesRepositoryImpl implements SeriesRepositoryCustom {
	
	@Autowired
	private MongoDatabase mongoDatabase;
	
	private String collectionName = "series";
	
	
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

	public List<Document> listWithNumberOfSamples() {

		List<Document> listSeries = mongoDatabase.getCollection(collectionName)
				.find()
				.projection(Projections.fields(Projections.include("title")))
				.sort(Sorts.ascending("_id"))
				.into(new ArrayList<Document>());


		for (Document doc : listSeries) {
			Long nbSamples = mongoDatabase.getCollection("samples").count((Filters.eq("series", doc.getString("_id"))));
			doc.append("nbSamples", nbSamples);

		}

		return listSeries;
	}

	/** ======================================================================================*/

}
