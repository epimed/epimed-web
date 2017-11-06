package epimed_web.repository.neo4j;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import epimed_web.entity.neo4j.Probeset;

public interface ProbesetRepository extends GraphRepository<Probeset> {

	public Probeset findByUid(String uid);
	
	// @Query("MATCH (p:Probeset)-[r:BELONGS_TO]->(pl:Platform) WHERE pl.uid={idPlatform} RETURN count(p)")
	// public int countByIdPlatform(@Param("idPlatform") String idPlatform);
	
	// @Query("MATCH (p:Probeset)-[r:BELONGS_TO]->(pl:Platform) WHERE pl.uid={idPlatform} RETURN p")
	// public Page<Probeset> findByIdPlatform(@Param("idPlatform") String idPlatform, Pageable pageRequest);
	
	// @Query("MATCH (p:Probeset) WHERE EXISTS (p.id_genes) RETURN p")
	// public Page<Probeset> findAllWithIdGenes(Pageable pageRequest);

	@Query("MATCH (p:Probeset)-[*1..2]->(g:Gene) WHERE g.uid={idGene} AND {idPlatform} IN p.id_platforms RETURN DISTINCT p")
	public List<Probeset> findByGene(@Param("idGene") Integer idGene, @Param("idPlatform") String idPlatform);
	
	@Query("MATCH (p:Probeset)-[*1..3]->(n:Nucleotide) WHERE n.uid={symbol} AND {idPlatform} IN p.id_platforms RETURN DISTINCT p")
	public List<Probeset> findByNucleotideSymbol(@Param("symbol") String symbol, @Param("idPlatform") String idPlatform);
	
	
	
}
