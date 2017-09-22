package epimed_web.repository.mongodb.jobs;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.jobs.JobHeader;
import epimed_web.entity.mongodb.jobs.JobType;

public interface JobHeaderRepository extends MongoRepository<JobHeader, JobType> {
	
}
