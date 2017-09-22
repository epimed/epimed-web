package epimed_web.entity.pojo;

public class AjaxResponse {

	private String message;
	private boolean success = false;
	private String jobid;
	
	public AjaxResponse() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getJobid() {
		return jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	@Override
	public String toString() {
		return "AjaxResponse [message=" + message + ", success=" + success + ", jobid=" + jobid + "]";
	}

	

}
