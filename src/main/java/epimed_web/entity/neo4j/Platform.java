package epimed_web.entity.neo4j;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.springframework.data.annotation.Id;

@NodeEntity
public class Platform {
	
	@GraphId 
	private Long graphId;

	@Id
	@Index
	private String uid;
	
	private String title;
	
	private String manufacturer;
	
	@Property(name="tax_id")
	private Integer taxId;

	private String technology;
	private String type;
	
	private Boolean enabled = true;
	
	public Platform() {
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

	public Integer getTaxId() {
		return taxId;
	}

	public void setTaxId(Integer taxId) {
		this.taxId = taxId;
	}
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
		Platform other = (Platform) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Platform [graphId=" + graphId + ", uid=" + uid + ", title=" + title + ", manufacturer=" + manufacturer
				+ ", taxId=" + taxId + ", technology=" + technology + ", type=" + type + ", enabled=" + enabled + "]";
	}



}
