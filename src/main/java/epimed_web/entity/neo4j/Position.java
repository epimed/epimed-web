package epimed_web.entity.neo4j;

import java.util.List;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;

@NodeEntity
public class Position {
	
	@GraphId 
	private Long graphId;

	@Id
	@Index
	private String uid;
	
	@Property(name="id_position")
	private String idPosition;
	
	@Property(name="tax_id")
	private Integer taxId;
	
	@Property(name="id_assemblies")
	private Set<String> idAssemblies;
	
	@Property(name="id_gene")
	private Long idGene;
	
	private String chrom;
	
	private String strand;
	
	@Property(name="tx_start")
	private Long txStart;
	
	@Property(name="tx_end")
	private Long txEnd;
	
	@Property(name="cds_start")
	private Long cdsStart;
	
	@Property(name="cds_end")
	private Long cdsEnd;
	
	@Property(name="exon_count")
	private Long exonCount;
	
	@Property(name="exon_starts")
	private List<Long> exonStarts;
	
	@Property(name="exon_ends")
	private List<Long> exonEnds;
	
	@Property(name="id_ensembl")
	private String idEnsembl;
	
	private DataSource source = DataSource.ucsc;
	
	@Relationship(type="LINKS", direction=Relationship.OUTGOING)
	private  Gene gene;
	
	@Relationship(type="BELONGS_TO", direction=Relationship.OUTGOING)
	private  List<Assembly> assemblies;

	public Position() {
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

	public String getIdPosition() {
		return idPosition;
	}

	public void setIdPosition(String idPosition) {
		this.idPosition = idPosition;
	}

	public Integer getTaxId() {
		return taxId;
	}

	public void setTaxId(Integer taxId) {
		this.taxId = taxId;
	}

	public Set<String> getIdAssemblies() {
		return idAssemblies;
	}

	public void setIdAssemblies(Set<String> idAssemblies) {
		this.idAssemblies = idAssemblies;
	}

	public Long getIdGene() {
		return idGene;
	}

	public void setIdGene(Long idGene) {
		this.idGene = idGene;
	}

	public String getChrom() {
		return chrom;
	}

	public void setChrom(String chrom) {
		this.chrom = chrom;
	}

	public String getStrand() {
		return strand;
	}

	public void setStrand(String strand) {
		this.strand = strand;
	}

	public Long getTxStart() {
		return txStart;
	}

	public void setTxStart(Long txStart) {
		this.txStart = txStart;
	}

	public Long getTxEnd() {
		return txEnd;
	}

	public void setTxEnd(Long txEnd) {
		this.txEnd = txEnd;
	}

	public Long getCdsStart() {
		return cdsStart;
	}

	public void setCdsStart(Long cdsStart) {
		this.cdsStart = cdsStart;
	}

	public Long getCdsEnd() {
		return cdsEnd;
	}

	public void setCdsEnd(Long cdsEnd) {
		this.cdsEnd = cdsEnd;
	}

	public Long getExonCount() {
		return exonCount;
	}

	public void setExonCount(Long exonCount) {
		this.exonCount = exonCount;
	}

	public List<Long> getExonStarts() {
		return exonStarts;
	}

	public void setExonStarts(List<Long> exonStarts) {
		this.exonStarts = exonStarts;
	}

	public List<Long> getExonEnds() {
		return exonEnds;
	}

	public void setExonEnds(List<Long> exonEnds) {
		this.exonEnds = exonEnds;
	}

	public String getIdEnsembl() {
		return idEnsembl;
	}

	public void setIdEnsembl(String idEnsembl) {
		this.idEnsembl = idEnsembl;
	}

	public DataSource getSource() {
		return source;
	}

	public void setSource(DataSource source) {
		this.source = source;
	}

	public Gene getGene() {
		return gene;
	}

	public void setGene(Gene gene) {
		this.gene = gene;
	}

	public List<Assembly> getAssemblies() {
		return assemblies;
	}

	public void setAssemblies(List<Assembly> assemblies) {
		this.assemblies = assemblies;
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
		Position other = (Position) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Position [graphId=" + graphId + ", uid=" + uid + ", idPosition=" + idPosition + ", taxId=" + taxId
				+ ", idAssemblies=" + idAssemblies + ", idGene=" + idGene + ", chrom=" + chrom + ", strand=" + strand
				+ ", txStart=" + txStart + ", txEnd=" + txEnd + ", cdsStart=" + cdsStart + ", cdsEnd=" + cdsEnd
				+ ", exonCount=" + exonCount + ", exonStarts=" + exonStarts + ", exonEnds=" + exonEnds + ", idEnsembl="
				+ idEnsembl + ", source=" + source + "]";
	}

}
