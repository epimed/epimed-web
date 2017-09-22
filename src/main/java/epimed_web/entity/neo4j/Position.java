package epimed_web.entity.neo4j;

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
	
	@Property(name="id_assembly")
	private String idAssembly;
	
	@Property(name="id_gene")
	private Integer idGene;
	
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
	private Integer exonCount;
	
	private DataSource source = DataSource.ucsc;
	
	@Relationship(type="LINKS", direction=Relationship.OUTGOING)
	private  Gene gene;
	
	@Relationship(type="BELONGS_TO", direction=Relationship.OUTGOING)
	private  Assembly assembly;

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

	public String getIdAssembly() {
		return idAssembly;
	}

	public void setIdAssembly(String idAssembly) {
		this.idAssembly = idAssembly;
	}

	public Integer getIdGene() {
		return idGene;
	}

	public void setIdGene(Integer idGene) {
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

	public Integer getExonCount() {
		return exonCount;
	}

	public void setExonCount(Integer exonCount) {
		this.exonCount = exonCount;
	}
	
	

	public Gene getGene() {
		return gene;
	}

	public void setGene(Gene gene) {
		this.gene = gene;
	}
	
	

	public DataSource getSource() {
		return source;
	}

	public void setSource(DataSource source) {
		this.source = source;
	}

	public Assembly getAssembly() {
		return assembly;
	}

	public void setAssembly(Assembly assembly) {
		this.assembly = assembly;
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
		return "Position [graphId=" + graphId + ", uid=" + uid + ", idAssembly=" + idAssembly + ", idGene=" + idGene
				+ ", chrom=" + chrom + ", strand=" + strand + ", txStart=" + txStart + ", txEnd=" + txEnd
				+ ", cdsStart=" + cdsStart + ", cdsEnd=" + cdsEnd + ", exonCount=" + exonCount + ", source=" + source
				+ ", gene=" + gene + ", assembly=" + assembly + "]";
	}

	
	
}
