package epimed_web.entity.mongodb.experiments;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "platform")
@TypeAlias("Platform")
public class Platform {
	
	@Id
	private String id; // GPL
	
	private String title;
	
	@Field("id_organism")
	private Integer idOrganism;
	
	private String organism;
	private String manufacturer;
	
	@Field("submission_date")
	private Date submissionDate;
	
	@Field("last_update")
	private Date lastUpdate;
	
	@Field("import_date")
	private Date importDate;
	
	private String technology;
	private String type;
	
	
	
	
	public Platform() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getIdOrganism() {
		return idOrganism;
	}
	public void setIdOrganism(Integer idOrganism) {
		this.idOrganism = idOrganism;
	}
	public String getOrganism() {
		return organism;
	}
	public void setOrganism(String organism) {
		this.organism = organism;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Date getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Date getImportDate() {
		return importDate;
	}
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
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

	@Override
	public String toString() {
		return "Platform [id=" + id + ", title=" + title + ", idOrganism=" + idOrganism + ", organism=" + organism
				+ ", manufacturer=" + manufacturer + ", submissionDate=" + submissionDate + ", lastUpdate=" + lastUpdate
				+ ", importDate=" + importDate + ", technology=" + technology + ", type=" + type + "]";
	}

}