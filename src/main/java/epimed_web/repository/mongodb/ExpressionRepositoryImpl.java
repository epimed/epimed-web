package epimed_web.repository.mongodb;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import epimed_web.entity.mongodb.genes.Expression;

@SuppressWarnings("unchecked")
public class ExpressionRepositoryImpl implements ExpressionRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	/** ========================================================== */

	
	@Override
	public List<String> findDistinctSamples() {
		
		List<String> listSamples = mongoTemplate.getCollection("expression").distinct("id_sample");
		Collections.sort(listSamples);
		return listSamples;
	}

	/** ========================================================== */
	
	@Override
	public Expression findBySampleAndGene(String idSample, Integer idGene) {
		Criteria criteria = Criteria.where("id_sample").is(idSample).and("id_gene").is(idGene);
		return mongoTemplate.findOne(Query.query(criteria), Expression.class);
	}

	/** ========================================================== */

}
