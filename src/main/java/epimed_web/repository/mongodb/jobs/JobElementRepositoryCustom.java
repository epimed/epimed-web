package epimed_web.repository.mongodb.jobs;

import java.util.List;

import org.bson.Document;

import epimed_web.entity.mongodb.jobs.JobElement;
import epimed_web.entity.mongodb.jobs.JobType;

public interface JobElementRepositoryCustom {

	public List<Document> findByElementTaxidTypeParameter(String element, Integer taxid, JobType type, String parameter, int nbDaysExpiration);
	public List<Document> findByJobid(String jobid);
	public List<JobElement> findJobElementsByJobid(String jobid);
	
	public void updateJobElement(Document docElement);
	
}
