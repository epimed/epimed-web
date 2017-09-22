package epimed_web.repository.neo4j;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import epimed_web.entity.neo4j.Gene;

public interface GeneRepository extends GraphRepository<Gene>{

	public Gene findByUid(Integer uid);
	
	@Query("MATCH (g1:Gene)-[*0..1]->(g2:Gene) WHERE g1.uid={uid} AND NOT g2.status=\"replaced\" RETURN g2")
	public Gene findCurrentByUid(@Param("uid") Integer uid);
	
	@Query("MATCH (g1:Gene)-[*0..1]->(g2:Gene) WHERE g1.uid={idGene} AND g1.tax_id={taxid} AND NOT g2.status=\"replaced\" RETURN g2")
	public List<Gene> findCurrentByIdGeneAndTaxid(@Param("idGene") Integer idGene, @Param("taxid") Integer taxid);
	
	@Query("MATCH (g1:Gene)-[*0..1]->(g2:Gene) WHERE g1.gene_symbol={geneSymbol} AND g1.tax_id={taxid} AND NOT g2.status=\"replaced\" RETURN g2")
	public List<Gene> findCurrentByGeneSymbolAndTaxid(@Param("geneSymbol") String geneSymbol, @Param("taxid") Integer taxid);
	
	@Query("MATCH (g1:Gene)-[*0..1]->(g2:Gene) WHERE {alias} in g1.aliases AND g1.tax_id={taxid} AND NOT g2.status=\"replaced\" RETURN g2")
	public List<Gene> findCurrentByAliasAndTaxid(@Param("alias") String alias, @Param("taxid") Integer taxid);
	
	@Query("MATCH p=(n:Nucleotide {uid:{uidNucleotide}})-[*1..10]->(g:Gene) WHERE (all(rel in relationships(p) where type(rel) in ['LINKS', 'REPLACED_BY'])) AND g.tax_id={taxid} AND NOT g.status=\"replaced\"  RETURN distinct g")
	public List<Gene> findCurrentByNucleotideAndTaxid(@Param("uidNucleotide") String uidNucleotide, @Param("taxid") Integer taxid);
	
	@Query("MATCH p=(n:Probeset {uid:{uidProbeset}})-[*1..10]->(g:Gene) WHERE (all(rel in relationships(p) where type(rel) in ['LINKS', 'REPLACED_BY'])) AND g.tax_id={taxid} AND NOT g.status=\"replaced\" RETURN distinct g")
	public List<Gene> findCurrentByProbesetAndTaxid(@Param("uidProbeset") String uidProbeset, @Param("taxid") Integer taxid);
	
	@Query("MATCH p=(n:ProteinSequence {uid:{uidProteinSequence}})-[*1..10]->(g:Gene) WHERE (all(rel in relationships(p) where type(rel) in ['LINKS', 'REPLACED_BY'])) AND g.tax_id={taxid} AND NOT g.status=\"replaced\" RETURN distinct g")
	public List<Gene> findCurrentByProteinSequenceAndTaxid(@Param("uidProteinSequence") String uidProteinSequence, @Param("taxid") Integer taxid);
	
	@Query("MATCH p=(n:Position {uid:{uidPosition}})-[*1..10]->(g:Gene) WHERE (all(rel in relationships(p) where type(rel) in ['LINKS', 'REPLACED_BY'])) AND g.tax_id={taxid} AND NOT g.status=\"replaced\" RETURN distinct g")
	public List<Gene> findCurrentByPositionAndTaxid(@Param("uidPosition") String uidPosition, @Param("taxid") Integer taxid);
	
	// @Query("MATCH (g:Gene) WHERE g.gene_symbol={geneSymbol} OR {geneSymbol} in g.aliases RETURN distinct g")
	// public List<Gene> findByGeneSymbolOrAlias(@Param("geneSymbol") String geneSymbol);

	
}
