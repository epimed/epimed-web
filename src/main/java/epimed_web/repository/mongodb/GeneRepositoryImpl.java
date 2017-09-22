package epimed_web.repository.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import epimed_web.entity.mongodb.Gene;

public class GeneRepositoryImpl implements GeneRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;


	/** ========================================================== */

	@Override
	public List<Gene> findInAnnotations(String annotation) {
		Criteria criteria = Criteria.where("annotations").in(annotation).and("removed").is(false);
		return mongoTemplate.find(Query.query(criteria),  Gene.class);
	}


	/** ========================================================== */

	@Override
	public List<Gene> findInAnnotations(List<String> listAnnotations) {
		Criteria criteria = Criteria.where("annotations").all(listAnnotations).and("removed").is(false);
		return mongoTemplate.find(Query.query(criteria), Gene.class);
	}	

	/** ========================================================== */

}
