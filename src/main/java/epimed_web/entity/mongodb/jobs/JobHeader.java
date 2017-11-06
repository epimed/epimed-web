package epimed_web.entity.mongodb.jobs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "job_header")
@TypeAlias("JobHeader")
public class JobHeader {

	@Id
	private String id;
	private List<String> header = new ArrayList<String>();


	public JobHeader() {
		super();
		header.add("your_input"); 
		header.add("tax_id");
		header.add("entrez");  
		header.add("gene_symbol"); 
		header.add("title");  
		header.add("location");  
		header.add("chrom");  
		header.add("status");  
		header.add("aliases");  
		header.add("database"); 
	}

	

	public JobHeader(String id, List<String> header) {
		super();
		this.id = id;
		this.header = header;
	}



	public String getId() {
		return id;
	}


	public void setId(String id) {
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