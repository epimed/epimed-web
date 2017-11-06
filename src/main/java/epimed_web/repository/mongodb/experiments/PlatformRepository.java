package epimed_web.repository.mongodb.experiments;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.experiments.Platform;

public interface PlatformRepository extends MongoRepository<Platform, String>, PlatformRepositoryCustom {
	
	public Platform findById(String idPlatform);

}
