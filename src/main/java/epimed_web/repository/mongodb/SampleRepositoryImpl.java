package epimed_web.repository.mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

import epimed_web.entity.mongodb.Sample;
import epimed_web.service.log.ApplicationLogger;


public class SampleRepositoryImpl extends ApplicationLogger implements SampleRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private MongoDatabase mongoDatabase;

	private String collectionName = "sample";


	/** ======================================================================================*/

	public List<Sample> findInSeries(Object [] listIdSeries) {
		Criteria criteria = Criteria.where("series").in(listIdSeries);
		return mongoTemplate.find(Query.query(criteria), Sample.class);
	}

	/** ======================================================================================*/

	public List<Document> getTopologyDistribution () {
		
		List<Document> listDocuments = mongoDatabase.getCollection(collectionName)
				.aggregate(
						Arrays.asList(
								Aggregates.group("$exp_group.topology", Accumulators.sum("total", 1)),
								Aggregates.sort(Sorts.orderBy(Sorts.descending("total")))
								))
				.into(new ArrayList<Document>());

		return listDocuments;
		
	}
	
	/** ======================================================================================*/

	public List<Document> listMorphologies() {

		Bson filters = Filters.ne("_id.id_morphology", null);

		Document group = new Document();
		group.put("id_morphology", "$exp_group.id_morphology");
		group.put("morphology", "$exp_group.morphology");

		List<Document> list = mongoDatabase.getCollection(collectionName)
				.aggregate(
						Arrays.asList(
								Aggregates.group(group),
								Aggregates.match(filters),
								Aggregates.sort(Sorts.orderBy(Sorts.ascending("_id.id_morphology")))
								))
				.into(new ArrayList<Document>());

		return list;

	}



	/** ======================================================================================*/

	/**
	 * Example of output
	 * Document{{_id=Document{{id_topology=15825003, topology=Aorta, id_topology_group=113257007, topology_group=CARDIOVASCULAR SYSTEM}}}}
	 * Document{{_id=Document{{id_topology=22083002, topology=Splenic artery, id_topology_group=113257007, topology_group=CARDIOVASCULAR SYSTEM}}}}
	 * Document{{_id=Document{{id_topology=419965008, topology=Embryonic stem cell, id_topology_group=419758009, topology_group=STEM CELL}}}}
	 * 
	 * @return
	 */

	public List<Document> listTopologies () {

		Bson filters = Filters.ne("_id.id_topology", null);

		Document group = new Document();
		group.put("id_topology", "$exp_group.id_topology");
		group.put("topology", "$exp_group.topology");
		group.put("id_topology_group", "$exp_group.id_topology_group");
		group.put("topology_group", "$exp_group.topology_group");

		List<Document> list = mongoDatabase.getCollection(collectionName)
				.aggregate(
						Arrays.asList(
								Aggregates.group(group),
								Aggregates.match(filters),
								Aggregates.sort(Sorts.orderBy(Sorts.ascending("_id.topology")))
								))
				.into(new ArrayList<Document>());

		return list;

	}

	/** ======================================================================================*/


	/**
	 * Example of output
	 * Document{{_id=Document{{id_topology_group=C02, topology_group=OTHER AND UNSPECIFIED PARTS OF TONGUE}}}}
	 * Document{{_id=Document{{id_topology_group=C03, topology_group=GUM}}}}
	 * Document{{_id=Document{{id_topology_group=C04, topology_group=FLOOR OF MOUTH}}}}
	 * @return
	 */
	public List<Document> listTopologyGroups () {

		Document f = new Document();
		f.put("id_topology_group", "$exp_group.id_topology_group");
		f.put("topology_group", "$exp_group.topology_group");


		List<Document> list = mongoDatabase.getCollection(collectionName)
				.aggregate(
						Arrays.asList(
								Aggregates.group(f),
								Aggregates.match(Filters.ne("_id.id_topology_group", null)),
								Aggregates.sort(Sorts.orderBy(Sorts.ascending("_id.id_topology_group")))
								))
				.into(new ArrayList<Document>());
		return list;
	}
	
	/** ======================================================================================*/

	public List<String> listPlatforms (Bson filter) {
		return mongoDatabase.getCollection(collectionName)
				.distinct("exp_group.id_platform", filter, String.class)
				.into(new ArrayList<String>());
	}


	/** ======================================================================================*/

	public List<String> listSeries (Bson filter) {
		return mongoDatabase.getCollection(collectionName)
				.distinct("exp_group.main_gse_number", filter, String.class)
				.into(new ArrayList<String>());
	}


	/** ======================================================================================*/

	public List<Document> listSamples () {
		List<Document> list = mongoDatabase.getCollection(collectionName)
				.find()
				.into(new ArrayList<Document>());

		return list;

	}

	/** ======================================================================================*/

	public List<Document> listSamples (Bson filter) {

		if (filter==null) {
			return this.listSamples();
		}
		else {
			List<Document> list = mongoDatabase.getCollection(collectionName)
					.find(filter)
					.into(new ArrayList<Document>());

			return list;
		}
	}
	
	/** ======================================================================================*/
		
	public Long count (Bson filter) {
		return mongoDatabase.getCollection(collectionName)
				.count(filter);
	}
	
	/** ======================================================================================*/

	public List<Document> getSamples (String[] listIdSamples, String projectionName) {
		List<Document> list = mongoDatabase.getCollection(collectionName)
				.find(Filters.in("_id", listIdSamples))
				.projection(Projections.fields(Projections.include(projectionName), Projections.excludeId()))
				.into(new ArrayList<Document>());
		return list;
	}

	/** ======================================================================================*/

	public List<Document> getSamples (Bson filter, String projectionName) {

		List<Document> list = new ArrayList<Document>();

		if (filter==null) {
			list = mongoDatabase.getCollection(collectionName)
					.find()
					.projection(Projections.fields(Projections.include(projectionName), Projections.excludeId()))
					.into(new ArrayList<Document>());
		}
		else {
			list = mongoDatabase.getCollection(collectionName)
					.find(filter)
					.projection(Projections.fields(Projections.include(projectionName), Projections.excludeId()))
					.into(new ArrayList<Document>());
		}


		return list;
	}
	
	/** ======================================================================================*/

	public List<Document> findSamples (String[] listIdSeries, String projectionName) {
		List<Document> list = mongoDatabase.getCollection(collectionName)
				.find(Filters.in("series", listIdSeries))
				.projection(Projections.fields(Projections.include(projectionName), Projections.excludeId()))
				.into(new ArrayList<Document>());
		return list;
	}
	
	/** ======================================================================================*/

	public List<Document> getSeries (String[] listIdSamples) {

		List<Document> list = mongoDatabase.getCollection(collectionName)
				.aggregate(
						Arrays.asList(
								Aggregates.match(Filters.in("_id", listIdSamples)),
								Aggregates.group("$main_gse_number"),
								Aggregates.sort(Sorts.orderBy(Sorts.ascending("main_gse_numbe")))
								)
						)
				.into(new ArrayList<Document>());
		return list;
	}

	/** ======================================================================================*/

	public boolean updateSample (String idSample, String projectionName, String key, String value) {

		boolean result = false;
		Document document = mongoDatabase.getCollection(collectionName).find(Filters.eq("_id", idSample)).first();

		if (document!=null) {
			Document projection = (Document) document.get(projectionName);
			projection.put(key, value);
			document.put(projectionName, projection);
			mongoDatabase.getCollection(collectionName).updateOne(Filters.eq("_id", idSample), new Document("$set", document));
			result = true;
		}

		return result;

	}
	
	/** ======================================================================================*/

	
}
