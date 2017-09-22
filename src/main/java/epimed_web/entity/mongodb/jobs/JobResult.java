package epimed_web.entity.mongodb.jobs;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

@TypeAlias("JobResult")
public class JobResult {

	@Field("your_input")
	private String input;
	

	public JobResult() {
		super();
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

}
