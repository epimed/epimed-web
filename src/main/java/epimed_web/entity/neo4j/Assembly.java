package epimed_web.entity.neo4j;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.springframework.data.annotation.Id;

@NodeEntity
public class Assembly {

	@GraphId 
	private Long graphId;

	@Id
	@Index
	private String uid;
	
	@Property(name="ucsc_code")
	private String ucscCode;
	
	private String name;
	
	@Property(name="tax_id")
	private Integer taxId;

	public Assembly() {
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

	public String getUcscCode() {
		return ucscCode;
	}

	public void setUcscCode(String ucscCode) {
		this.ucscCode = ucscCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public Integer getTaxId() {
		return taxId;
	}

	public void setTaxId(Integer taxId) {
		this.taxId = taxId;
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
		Assembly other = (Assembly) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Assembly [graphId=" + graphId + ", uid=" + uid + ", ucscCode=" + ucscCode + ", name=" + name
				+ ", taxId=" + taxId + "]";
	}

	
	
}
