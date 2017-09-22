package epimed_web.entity.mongodb.admin;

import java.util.Date;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "log")
@TypeAlias("Log")
public class Log {

	@Id
	private ObjectId id;

	@Field("last_activity")
	private Date lastActivity;

	private String ip;

	@Field("single_ip")
	private String singleIp;

	private String method;
	private String route;
	private String url;
	private Map<String, ?> parameter;
	private String comment;
	
	// can be later used for dispalying
	private User user;
	
	public Log() {
		super();
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public Date getLastActivity() {
		return lastActivity;
	}
	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
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
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Map<String, ?> getParameter() {
		return parameter;
	}
	public void setParameter(Map<String, ?> parameter) {
		this.parameter = parameter;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public String toString() {
		return "Log [id=" + id + ", lastActivity=" + lastActivity + ", ip=" + ip + ", singleIp=" + singleIp
				+ ", method=" + method + ", route=" + route + ", url=" + url + ", parameter=" + parameter + ", comment="
				+ comment + ", user=" + user + "]";
	}
	
	
}
