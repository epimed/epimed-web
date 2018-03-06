package epimed_web.repository.neo4j;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import epimed_web.entity.neo4j.Annotation;

public interface Neo4jAnnotationRepository extends GraphRepository<Annotation> {

	@Query("MATCH (a:Annotation) RETURN a ORDER BY a.source ASC, a.parameter ASC, a.uid ASC")
	public List<Annotation> findAllAnnotations();
	
}
