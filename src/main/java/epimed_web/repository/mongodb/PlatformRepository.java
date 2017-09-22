package epimed_web.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.Platform;

public interface PlatformRepository extends MongoRepository<Platform, String>, PlatformRepositoryCustom {
	
	public Platform findById(String idPlatform);

}
