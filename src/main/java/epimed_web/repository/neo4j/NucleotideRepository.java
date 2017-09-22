package epimed_web.repository.neo4j;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import epimed_web.entity.neo4j.DataSource;
import epimed_web.entity.neo4j.Nucleotide;

public interface NucleotideRepository extends GraphRepository<Nucleotide> {

	public Nucleotide findByUid(String uid);
	
	@Query("MATCH (n:Nucleotide)-[*1..2]->(g:Gene) WHERE g.uid={idGene} AND n.source={source} RETURN n.uid")
	public Set<String> findIdNucleotides(@Param("idGene") Integer idGene, @Param("source") DataSource source);
}
