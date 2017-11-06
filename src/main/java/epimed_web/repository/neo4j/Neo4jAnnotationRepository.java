package epimed_web.repository.neo4j;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import epimed_web.entity.neo4j.Annotation;
import epimed_web.entity.neo4j.DataSource;

public interface Neo4jAnnotationRepository extends GraphRepository<Annotation> {

	@Query("MATCH (a:Annotation) RETURN a ORDER BY a.source ASC, a.parameter ASC, a.uid ASC")
	public List<Annotation> findAllAnnotations();
	
	@Query("MATCH (a:Annotation) WHERE a.uid={idAnnotation} RETURN a")
	public Annotation findByUid(@Param("idAnnotation") String idAnnotation);
	
	@Query("MATCH (a:Annotation) WHERE a.uid IN {idAnnotation} RETURN a")
	public List<Annotation> findByIdAnnotations(@Param("idAnnotations") List<String> idAnnotations);
	
	@Query("MATCH (g:Gene)-[r:BELONGS_TO]->(a:Annotation) WHERE g.uid={idGene} AND a.uid IN {idAnnotations} RETURN a")
	public List<Annotation> findByIdGeneAndIdAnnotations(@Param("idGene") Integer idGene, @Param("idAnnotations") List<String> idAnnotations);
	
	@Query("MATCH (g:Gene)-[r:BELONGS_TO]->(a:Annotation) WHERE g.uid={idGene} AND a.uid IN {idAnnotations} RETURN DISTINCT a.parameter")
	public List<String> findParameters(@Param("idGene") Integer idGene, @Param("idAnnotations") List<String> idAnnotations);
	
	@Query("MATCH (g:Gene)-[r:BELONGS_TO]->(a:Annotation) WHERE g.uid={idGene} AND a.uid IN {idAnnotations} RETURN DISTINCT a.uid")
	public List<String> findUids(@Param("idGene") Integer idGene, @Param("idAnnotations") List<String> idAnnotations);
	
	@Query("MATCH (g:Gene)-[r:BELONGS_TO]->(a:Annotation) WHERE g.uid={idGene} AND a.uid IN {idAnnotations} RETURN DISTINCT a.type")
	public List<String> findTypes(@Param("idGene") Integer idGene, @Param("idAnnotations") List<String> idAnnotations);
	
	@Query("MATCH (g:Gene)-[r:BELONGS_TO]->(a:Annotation) WHERE g.uid={idGene} AND a.uid IN {idAnnotations} RETURN DISTINCT a.source")
	public List<String> findSources(@Param("idGene") Integer idGene, @Param("idAnnotations") List<String> idAnnotations);
	
	@Query("MATCH (a:Annotation) RETURN DISTINCT a.source ORDER BY a.source ASC")
	public List<String> findSources();
	
	@Query("MATCH (g:Gene)-[r:BELONGS_TO]->(a:Annotation) WHERE g.tax_id={taxid} RETURN DISTINCT a.source ORDER BY a.source ASC")
	public List<String> findSourcesByTaxid(@Param("taxid") Integer taxid);
	
	@Query("MATCH (a:Annotation) WHERE a.source={source} RETURN DISTINCT a.parameter ORDER BY a.parameter ASC")
	public List<String> findParametersBySource(@Param("source") DataSource source);
	
	@Query("MATCH (g:Gene)-[r:BELONGS_TO]->(a:Annotation) WHERE g.tax_id={taxid} AND a.source={source} RETURN DISTINCT a.parameter ORDER BY a.parameter ASC")
	public List<String> findParametersBySourceAndTaxid(@Param("source") DataSource source, @Param("taxid") Integer taxid);
	
	@Query("MATCH (a:Annotation) WHERE a.uid={idAnnotation} RETURN a.parameter")
	public String findParameterByIdAnnotation(@Param("idAnnotation") String idAnnotation);
	
	@Query("MATCH (g:Gene)-[r:BELONGS_TO]->(a:Annotation) WHERE g.tax_id={taxid} AND a.source={source} RETURN DISTINCT a.uid ORDER BY a.uid")
	public List<String> findUidsBySourceAndTaxid(@Param("source") DataSource source, @Param("taxid") Integer taxid);
	
	@Query("MATCH (g:Gene)-[r:BELONGS_TO]->(a:Annotation) WHERE g.tax_id={taxid} AND a.source={source} AND a.parameter={parameter} RETURN DISTINCT a.uid ORDER BY a.uid")
	public List<String> findUidsBySourceAndTaxidAndParameter(@Param("source") DataSource source, @Param("taxid") Integer taxid, @Param("parameter") String parameter);
	
}
