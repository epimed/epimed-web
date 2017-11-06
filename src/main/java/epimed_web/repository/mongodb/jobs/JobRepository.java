package epimed_web.repository.mongodb.jobs;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.jobs.Job;
import epimed_web.entity.mongodb.jobs.JobType;

public interface JobRepository extends MongoRepository<Job, String>, JobRepositoryCustom {

	public List<Job> findByType (JobType type);
	
}
