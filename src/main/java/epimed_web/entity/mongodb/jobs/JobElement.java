package epimed_web.entity.mongodb.jobs;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "job_element")
@TypeAlias("JobElement")
public class JobElement {;

	@Id
	private ObjectId id;

	private Set<String> jobs = new HashSet<String>();

	@Field("submission_date")
	private Date submissionDate;
	
	@Field("last_activity")
	private Date lastActivity;
	
	private String element;
	
	@Field("tax_id")
	private Integer taxid;
	
	private JobType type;
	private String parameter;
	private JobResult result;
	
	public JobElement() {
		super();
	}

	
	public ObjectId getId() {
		return id;
	}


	public void setId(ObjectId id) {
		this.id = id;
	}


	public Set<String> getJobs() {
		return jobs;
	}


	public void setJobs(Set<String> jobs) {
		this.jobs = jobs;
	}


	public Date getSubmissionDate() {
		return submissionDate;
	}


	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}


	public Date getLastActivity() {
		return lastActivity;
	}


	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}


	public String getElement() {
		return element;
	}


	public void setElement(String element) {
		this.element = element;
	}


	public Integer getTaxid() {
		return taxid;
	}


	public void setTaxid(Integer taxid) {
		this.taxid = taxid;
	}


	public JobType getType() {
		return type;
	}


	public void setType(JobType type) {
		this.type = type;
	}
	
	


	public String getParameter() {
		return parameter;
	}


	public void setParameter(String parameter) {
		this.parameter = parameter;
	}


	public JobResult getResult() {
		return result;
	}


	public void setResult(JobResult result) {
		this.result = result;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		JobElement other = (JobElement) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "JobElement [id=" + id + ", jobs=" + jobs + ", submissionDate=" + submissionDate + ", lastActivity="
				+ lastActivity + ", element=" + element + ", taxid=" + taxid + ", type=" + type + ", parameter="
				+ parameter + ", result=" + result + "]";
	}



	
}
