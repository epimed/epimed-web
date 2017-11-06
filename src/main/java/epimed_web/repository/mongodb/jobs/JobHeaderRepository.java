package epimed_web.repository.mongodb.jobs;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.jobs.JobHeader;

public interface JobHeaderRepository extends MongoRepository<JobHeader, String> {
	
}
