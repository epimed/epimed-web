package epimed_web.repository.neo4j;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import epimed_web.entity.neo4j.Assembly;

public interface AssemblyRepository extends GraphRepository<Assembly>{

	@Query("MATCH (a:Assembly) WHERE a.tax_id={taxid} RETURN a ORDER BY a.uid DESC")
	public List<Assembly> findByTaxid(@Param("taxid") Integer taxid);
}
