package epimed_web.repository.mongodb;

import java.util.List;

import epimed_web.entity.mongodb.Expression;

public interface ExpressionRepositoryCustom {

	public List<String> findDistinctSamples();
	public Expression findBySampleAndGene(String idSample, Integer idGene);
	
}
