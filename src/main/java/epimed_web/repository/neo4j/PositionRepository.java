package epimed_web.repository.neo4j;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import epimed_web.entity.neo4j.Position;

public interface PositionRepository extends GraphRepository<Position> {
	
	@Query("MATCH (p:Position)-[]->(g:Gene) WHERE g.uid={idGene} AND {idAssembly} IN p.id_assemblies RETURN p ORDER BY p.chrom ASC, p.exon_count DESC")
	public List<Position> findByIdGeneAndIdAssembly(@Param("idGene") Integer idGene, @Param("idAssembly") String idAssembly);

}
