package epimed_web.repository.mongodb.admin;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.admin.Log;

public interface LogRepository extends MongoRepository<Log, ObjectId>, LogRepositoryCustom {

}
