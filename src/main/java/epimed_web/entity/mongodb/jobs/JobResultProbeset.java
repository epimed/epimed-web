package epimed_web.entity.mongodb.jobs;

import org.springframework.data.mongodb.core.mapping.Field;

public class JobResultProbeset extends JobResult {

	@Field("tax_id")
	private Integer taxid;
	private String platform;
	private String probeset;


	public JobResultProbeset() {
		super();
	}


	public Integer getTaxid() {
		return taxid;
	}


	public void setTaxid(Integer taxid) {
		this.taxid = taxid;
	}


	public String getPlatform() {
		return platform;
	}


	public void setPlatform(String platform) {
		this.platform = platform;
	}


	public String getProbeset() {
		return probeset;
	}


	public void setProbeset(String probeset) {
		this.probeset = probeset;
	}


	@Override
	public String toString() {
		return "JobResultProbeset [taxid=" + taxid + ", platform=" + platform + ", probeset=" + probeset
				+ ", getInput()=" + getInput() + "]";
	}

}