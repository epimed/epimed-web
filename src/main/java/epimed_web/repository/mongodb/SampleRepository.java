package epimed_web.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.Sample;

public interface SampleRepository extends MongoRepository<Sample, String>, SampleRepositoryCustom {

	public Sample findById(String idSample);
	
}
