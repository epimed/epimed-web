package epimed_web.repository.mongodb.experiments;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

import epimed_web.entity.mongodb.experiments.Series;
import epimed_web.service.util.FormatService;

public class SeriesRepositoryImpl implements SeriesRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private MongoDatabase mongoDatabase;
	
	@Autowired 
	private FormatService formatService;

	private String collectionName = "series";

	/** ======================================================================================*/

	public List<Series> findByText(String text) {

		String [] elements = formatService.convertStringToArray(text); 
		
		List<Criteria> orCriteriaList = new ArrayList<Criteria>();
		for (String element: elements) {
		        orCriteriaList.add(Criteria.where("id").regex(element,"i"));
		        orCriteriaList.add(Criteria.where("title").regex(element, "i"));
		        orCriteriaList.add(Criteria.where("secondaryAccessions").in(element, "i"));
		}

		Query query = new Query();
		query.addCriteria(new Criteria().orOperator(orCriteriaList.toArray(new Criteria[orCriteriaList.size()])));
		query.with(new Sort(Sort.Direction.ASC, "id"));
		return mongoTemplate.find(query, Series.class);
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
