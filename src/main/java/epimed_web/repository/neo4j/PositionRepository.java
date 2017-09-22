package epimed_web.repository.neo4j;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import epimed_web.entity.neo4j.Position;

public interface PositionRepository extends GraphRepository<Position>{

	@Query("MATCH (p:Position)-[*1..2]->(g:Gene) WHERE g.uid={idGene} AND p.id_assembly={idAssembly} RETURN p")
	public List<Position> findPositions(@Param("idGene") Integer idGene, @Param("idAssembly") String idAssembly);
}
