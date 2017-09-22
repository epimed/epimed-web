package epimed_web.entity.mongodb;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "sample")
@TypeAlias("Sample")
public class Sample {
	
	@Id
	private String id; // GSM
	
	@Field("main_gse_number")
	private String mainGseNumber;

	private Set<String> series;
	
	private String organism;

	@Field("submission_date")
	private Date submissionDate;
	
	@Field("last_update")
	private Date lastUpdate;
	
	@Field("import_date")
	private Date importDate;
	
	@Field("exp_group")
	private ExpGroup expGroup;
	
	private org.bson.Document parameters;
	
	private Boolean analyzed;
	
	public Sample() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getMainGseNumber() {
		return mainGseNumber;
	}


	public void setMainGseNumber(String mainGseNumber) {
		this.mainGseNumber = mainGseNumber;
	}


	public Set<String> getSeries() {
		return series;
	}


	public void setSeries(Set<String> series) {
		this.series = series;
	}


	public String getOrganism() {
		return organism;
	}


	public void setOrganism(String organism) {
		this.organism = organism;
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


	
	public org.bson.Document getExpGroup() {
		return expGroup;
	}


	public org.bson.Document getParameters() {
		return parameters;
	}


	public void setExpGroup(ExpGroup expGroup) {
		this.expGroup = expGroup;
	}


	public void setParameters(org.bson.Document parameters) {
		this.parameters = parameters;
	}


	public Boolean getAnalyzed() {
		return analyzed;
	}


	public void setAnalyzed(Boolean analyzed) {
		this.analyzed = analyzed;
	}


	@Override
	public String toString() {
		return "Sample [id=" + id + ", mainGseNumber=" + mainGseNumber + ", series=" + series + ", organism=" + organism
				+ ", submissionDate=" + submissionDate + ", lastUpdate=" + lastUpdate + ", importDate=" + importDate
				+ ", analyzed=" + analyzed + "]";
	}

}