package epimed_web.repository.mongodb.jobs;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.jobs.Job;

public interface JobRepository extends MongoRepository<Job, String>, JobRepositoryCustom {

}
