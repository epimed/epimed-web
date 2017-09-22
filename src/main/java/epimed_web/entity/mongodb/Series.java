package epimed_web.entity.mongodb;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "series")
@TypeAlias("Series")
public class Series {
	
	@Id
	private String id; // GSE
	
	private String title;
	private Set<String> platforms;
	
	@Field("submission_date")
	private Date submissionDate;
	
	@Field("last_update")
	private Date lastUpdate;
	
	@Field("import_date")
	private Date importDate;
	
	@Field("nb_samples")
	private Integer nbSamples;
	
	@Field("secondary_accessions")
	private Set<String> secondaryAccessions;

	public Series() {
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

	public Set<String> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(Set<String> platforms) {
		this.platforms = platforms;
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

	public Integer getNbSamples() {
		return nbSamples;
	}

	public void setNbSamples(Integer nbSamples) {
		this.nbSamples = nbSamples;
	}

	public Set<String> getSecondaryAccessions() {
		return secondaryAccessions;
	}

	public void setSecondaryAccessions(Set<String> secondaryAccessions) {
		this.secondaryAccessions = secondaryAccessions;
	}

	@Override
	public String toString() {
		return "Series [id=" + id + ", title=" + title + ", platforms=" + platforms + ", submissionDate="
				+ submissionDate + ", lastUpdate=" + lastUpdate + ", importDate=" + importDate + ", nbSamples="
				+ nbSamples + ", secondaryAccessions=" + secondaryAccessions + "]";
	}
	
}