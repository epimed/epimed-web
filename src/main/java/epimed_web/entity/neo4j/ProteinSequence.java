package epimed_web.entity.neo4j;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;

@NodeEntity
public class ProteinSequence {
	
	@GraphId 
	private Long graphId;

	@Id
	@Index
	private String uid;
	
	@Property(name="id_protein")
	private String idProtein;
	
	private String meta;
	private Integer length;
	private Double pi;
	
	@Property(name="average_mass")
	private Double averageMass;
	
	@Property(name="monoisotopic_mass")
	private Double monoisotopicMass;
	
	private boolean canonical;
	private String sequence;
	

	@Relationship(type="LINKS", direction=Relationship.OUTGOING)
	private  Protein protein;


	public ProteinSequence() {
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

	public String getIdProtein() {
		return idProtein;
	}


	public void setIdProtein(String idProtein) {
		this.idProtein = idProtein;
	}


	public String getMeta() {
		return meta;
	}


	public void setMeta(String meta) {
		this.meta = meta;
	}


	public Integer getLength() {
		return length;
	}


	public void setLength(Integer length) {
		this.length = length;
	}


	public Double getPi() {
		return pi;
	}


	public void setPi(Double pi) {
		this.pi = pi;
	}


	public Double getAverageMass() {
		return averageMass;
	}


	public void setAverageMass(Double averageMass) {
		this.averageMass = averageMass;
	}


	public Double getMonoisotopicMass() {
		return monoisotopicMass;
	}


	public void setMonoisotopicMass(Double monoisotopicMass) {
		this.monoisotopicMass = monoisotopicMass;
	}


	public boolean isCanonical() {
		return canonical;
	}


	public void setCanonical(boolean canonical) {
		this.canonical = canonical;
	}


	public String getSequence() {
		return sequence;
	}


	public void setSequence(String sequence) {
		this.sequence = sequence;
	}


	


	public Protein getProtein() {
		return protein;
	}


	public void setProtein(Protein protein) {
		this.protein = protein;
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
		ProteinSequence other = (ProteinSequence) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "ProteinSequence [graphId=" + graphId + ", uid=" + uid + ", idProtein=" + idProtein + ", meta=" + meta
				+ ", length=" + length + ", pi=" + pi + ", averageMass=" + averageMass + ", monoisotopicMass="
				+ monoisotopicMass + ", canonical=" + canonical + ", sequence=" + sequence + ", protein=" + protein
				+ "]";
	}


}
