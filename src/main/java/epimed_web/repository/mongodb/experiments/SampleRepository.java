package epimed_web.repository.mongodb.experiments;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.experiments.Sample;

public interface SampleRepository extends MongoRepository<Sample, String>, SampleRepositoryCustom {

	public Sample findById(String idSample);
	
}
