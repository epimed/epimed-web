package epimed_web.entity.neo4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;

@NodeEntity
public class Gene {

	@GraphId 
	private Long graphId;

	@Id
	@Index
	private Integer uid;

	@Property(name="gene_symbol")
	private String geneSymbol;

	private String title;
	private String chrom;
	private String location;
	private String type;
	
	private GeneStatus status = GeneStatus.unknown;

	@Property(name="tax_id")
	private Integer taxId;

	private Set<String> aliases = new HashSet<String>();

	@Property(name="replaced_by")
	private Integer replacedBy;
	
	@Relationship(type="REPLACED_BY", direction=Relationship.OUTGOING)
	private Gene currentGene;
	
	private String feature;
	
	@Property(name="modification_date")
	private String modificationDate;
	
	@Property(name="last_update")
	private String lastUpdate;
	
	// === Supplementary attributes to fill manually ===
	private List<Probeset> probesets;
	private List<Position> positions;
	
	public Gene() {
		super();
	}


	public Long getGraphId() {
		return graphId;
	}


	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}


	public Integer getUid() {
		return uid;
	}


	public void setUid(Integer uid) {
		this.uid = uid;
	}


	public String getGeneSymbol() {
		return geneSymbol;
	}


	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getChrom() {
		return chrom;
	}


	public void setChrom(String chrom) {
		this.chrom = chrom;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public GeneStatus getStatus() {
		return status;
	}


	public void setStatus(GeneStatus status) {
		this.status = status;
	}


	public Integer getTaxId() {
		return taxId;
	}


	public void setTaxId(Integer taxId) {
		this.taxId = taxId;
	}


	public Set<String> getAliases() {
		return aliases;
	}


	public void setAliases(Set<String> aliases) {
		this.aliases = aliases;
	}


	public Integer getReplacedBy() {
		return replacedBy;
	}


	public void setReplacedBy(Integer replacedBy) {
		this.replacedBy = replacedBy;
	}


	public Gene getCurrentGene() {
		return currentGene;
	}


	public void setCurrentGene(Gene currentGene) {
		this.currentGene = currentGene;
	}
	

	public String getFeature() {
		return feature;
	}


	public void setFeature(String feature) {
		this.feature = feature;
	}
	
	
	public String getModificationDate() {
		return modificationDate;
	}


	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}


	public String getLastUpdate() {
		return lastUpdate;
	}


	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	public List<Probeset> getProbesets() {
		return probesets;
	}

	public void setProbesets(List<Probeset> probesets) {
		this.probesets = probesets;
	}

	public List<Position> getPositions() {
		return positions;
	}


	public void setPositions(List<Position> positions) {
		this.positions = positions;
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
		Gene other = (Gene) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Gene [graphId=" + graphId + ", uid=" + uid + ", geneSymbol=" + geneSymbol + ", title=" + title
				+ ", chrom=" + chrom + ", location=" + location + ", type=" + type + ", status=" + status + ", taxId="
				+ taxId + ", aliases=" + aliases + ", replacedBy=" + replacedBy + ", currentGene=" + currentGene
				+ ", feature=" + feature + ", probesets=" + probesets + "]";
	}

	

	
}