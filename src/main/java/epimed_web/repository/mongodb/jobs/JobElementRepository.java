package epimed_web.repository.mongodb.jobs;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.jobs.JobElement;

public interface JobElementRepository extends MongoRepository<JobElement, ObjectId>, JobElementRepositoryCustom {
	
}
