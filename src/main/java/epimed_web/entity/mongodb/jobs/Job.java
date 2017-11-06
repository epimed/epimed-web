package epimed_web.entity.mongodb.jobs;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import epimed_web.entity.mongodb.admin.User;

@Document(collection = "job")
@TypeAlias("Job")
public class Job {

	@Id
	private String id;

	@Field("submission_date")
	private Date submissionDate;

	@Field("last_activity")
	private Date lastActivity;

	private Integer total;
	private Integer current;
	private JobType type;
	private List<String> elements;
	
	@Field("main_object")
	private Object mainObject;
	private JobStatus status;
	
	private String comment;
	private String ip;

	@Field("single_ip")
	private String singleIp;

	private String method;
	private String url;

	// can be later used for dispalying
	private User user;

	public Job() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public JobType getType() {
		return type;
	}

	public void setType(JobType type) {
		this.type = type;
	}

	public List<String> getElements() {
		return elements;
	}

	public void setElements(List<String> elements) {
		this.elements = elements;
	}


	public Object getMainObject() {
		return mainObject;
	}

	public void setMainObject(Object mainObject) {
		this.mainObject = mainObject;
	}

	public JobStatus getStatus() {
		return status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSingleIp() {
		return singleIp;
	}

	public void setSingleIp(String singleIp) {
		this.singleIp = singleIp;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", submissionDate=" + submissionDate + ", lastActivity=" + lastActivity + ", total="
				+ total + ", current=" + current + ", type=" + type + ", elements=" + elements + ", mainObject="
				+ mainObject + ", status=" + status + ", comment=" + comment + ", ip=" + ip + ", singleIp=" + singleIp
				+ ", method=" + method + ", url=" + url + ", user=" + user + "]";
	}

}
