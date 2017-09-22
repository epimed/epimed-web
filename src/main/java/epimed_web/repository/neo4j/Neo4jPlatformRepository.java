package epimed_web.repository.neo4j;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import epimed_web.entity.neo4j.Platform;

public interface Neo4jPlatformRepository extends GraphRepository<Platform> {

	public Platform findByUid(String uid);
	
	@Query("MATCH (p:Platform) WHERE p.enabled=true AND p.tax_id={taxid} RETURN p ORDER BY p.uid ASC")
	public List<Platform> findAllEnabledByTaxid(@Param("taxid") Integer taxid);
	
	@Query("MATCH (p:Platform) WHERE p.uid={idPlatform} RETURN p")
	public Platform findByIdPlatform(@Param("idPlatform") String idPlatform);
	
}
