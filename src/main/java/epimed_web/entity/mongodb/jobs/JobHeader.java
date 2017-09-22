package epimed_web.entity.mongodb.jobs;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "job_header")
@TypeAlias("JobHeader")
public class JobHeader {

	@Id
	private JobType id;
	private List<String> header;


	public JobHeader() {
		super();
	}

	

	public JobHeader(JobType id, List<String> header) {
		super();
		this.id = id;
		this.header = header;
	}



	public JobType getId() {
		return id;
	}


	public void setId(JobType id) {
		this.id = id;
	}


	public List<String> getHeader() {
		return header;
	}


	public void setHeader(List<String> header) {
		this.header = header;
	}


	@Override
	public String toString() {
		return "JobHeader [id=" + id + ", header=" + header + "]";
	}

}