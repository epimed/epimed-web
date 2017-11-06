package epimed_web.repository.neo4j;

import org.springframework.data.neo4j.repository.GraphRepository;

import epimed_web.entity.neo4j.GoTerm;


public interface GoTermRepository extends GraphRepository<GoTerm> {

	public GoTerm findByUid(String uid);
	
	
}
