package epimed_web.entity.neo4j;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;

@NodeEntity
public class Probeset {

	@GraphId 
	private Long graphId;

	@Id
	@Index
	private String uid;
	
	private DataSource source = DataSource.probeset;
	
	@Property(name="id_platforms")
	private Set<String> idPlatforms = new HashSet<String>();
	
	@Property(name="genome_build")
	private String genomeBuild;
	
	private String chrom;
	
	/**
	 * MAPINFO: Coordinates on the genome build
	 */
	private String mapinfo;
	
	/**
	 * SourceSeq: Unconverted design sequence
	 */
	@Property(name="source_seq")
	private String sourceSeq;
	
	private String strand;
	
	@Property(name="id_genes")
	private Set<Integer> idGenes;
	
	@Property(name="gene_symbols")
	private Set<String> geneSymbols;

	@Property(name="id_nucleotides")
	private Set<String> idNucleotides;
	
	@Property(name="gene_groups")
	private Set<String> geneGroups;
	
	/**
	 * CpG island location (UCSC)
	 */
	@Property(name="cpg_island_location")
	private String cpgIslandLocation;
	
	/**
	 * Relation_to_UCSC_CpG_Island: Relationship to Canonical CpG Island: Shores - 0-2 kb from CpG island; Shelves - 2-4 kb from CpG island.
	 */
	@Property(name="relation_to_cpg_island")
	private String relationToCpgIsland;
	
	/**
	 * DMR: Differentially methylated region (experimentally determined)
	 */
	private String dmr;
	
	private String enhancer;
	
	// === Relationships ===
	
	@Relationship(type="LINKS", direction=Relationship.OUTGOING)
	private Set<Gene> genes = new HashSet<Gene>();
	
	@Relationship(type="LINKS", direction=Relationship.OUTGOING)
	private Set<Nucleotide> nucleotides = new HashSet<Nucleotide>();

	@Relationship(type="BELONGS_TO", direction=Relationship.OUTGOING)
	private  Set<Platform> platforms = new HashSet<Platform>();

	public Probeset() {
		super();
	}




	public Long getGraphId() {
		return graphId;
	}




	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}




	public String getUid() {
		return uid;
	}




	public void setUid(String uid) {
		this.uid = uid;
	}




	public DataSource getSource() {
		return source;
	}




	public void setSource(DataSource source) {
		this.source = source;
	}




	public Set<String> getIdPlatforms() {
		return idPlatforms;
	}




	public void setIdPlatforms(Set<String> idPlatforms) {
		this.idPlatforms = idPlatforms;
	}




	public String getGenomeBuild() {
		return genomeBuild;
	}




	public void setGenomeBuild(String genomeBuild) {
		this.genomeBuild = genomeBuild;
	}




	public String getChrom() {
		return chrom;
	}




	public void setChrom(String chrom) {
		this.chrom = chrom;
	}




	public String getMapinfo() {
		return mapinfo;
	}




	public void setMapinfo(String mapinfo) {
		this.mapinfo = mapinfo;
	}




	public String getSourceSeq() {
		return sourceSeq;
	}




	public void setSourceSeq(String sourceSeq) {
		this.sourceSeq = sourceSeq;
	}




	public String getStrand() {
		return strand;
	}




	public void setStrand(String strand) {
		this.strand = strand;
	}




	public Set<Integer> getIdGenes() {
		return idGenes;
	}




	public void setIdGenes(Set<Integer> idGenes) {
		this.idGenes = idGenes;
	}




	public Set<String> getGeneSymbols() {
		return geneSymbols;
	}




	public void setGeneSymbols(Set<String> geneSymbols) {
		this.geneSymbols = geneSymbols;
	}




	public Set<String> getIdNucleotides() {
		return idNucleotides;
	}




	public void setIdNucleotides(Set<String> idNucleotides) {
		this.idNucleotides = idNucleotides;
	}




	public Set<String> getGeneGroups() {
		return geneGroups;
	}




	public void setGeneGroups(Set<String> geneGroups) {
		this.geneGroups = geneGroups;
	}




	public String getCpgIslandLocation() {
		return cpgIslandLocation;
	}




	public void setCpgIslandLocation(String cpgIslandLocation) {
		this.cpgIslandLocation = cpgIslandLocation;
	}




	public String getRelationToCpgIsland() {
		return relationToCpgIsland;
	}




	public void setRelationToCpgIsland(String relationToCpgIsland) {
		this.relationToCpgIsland = relationToCpgIsland;
	}




	public String getDmr() {
		return dmr;
	}




	public void setDmr(String dmr) {
		this.dmr = dmr;
	}




	public String getEnhancer() {
		return enhancer;
	}




	public void setEnhancer(String enhancer) {
		this.enhancer = enhancer;
	}




	public Set<Gene> getGenes() {
		return genes;
	}




	public void setGenes(Set<Gene> genes) {
		this.genes = genes;
	}




	public Set<Nucleotide> getNucleotides() {
		return nucleotides;
	}




	public void setNucleotides(Set<Nucleotide> nucleotides) {
		this.nucleotides = nucleotides;
	}




	public Set<Platform> getPlatforms() {
		return platforms;
	}




	public void setPlatforms(Set<Platform> platforms) {
		this.platforms = platforms;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Probeset other = (Probeset) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Probeset [graphId=" + graphId + ", uid=" + uid + ", source=" + source + ", idPlatforms=" + idPlatforms
				+ ", genomeBuild=" + genomeBuild + ", chrom=" + chrom + ", mapinfo=" + mapinfo + ", sourceSeq="
				+ sourceSeq + ", strand=" + strand + ", idGenes=" + idGenes + ", geneSymbols=" + geneSymbols
				+ ", idNucleotides=" + idNucleotides + ", geneGroups=" + geneGroups + ", cpgIslandLocation="
				+ cpgIslandLocation + ", relationToCpgIsland=" + relationToCpgIsland + ", dmr=" + dmr + ", enhancer="
				+ enhancer + "]";
	}

}
