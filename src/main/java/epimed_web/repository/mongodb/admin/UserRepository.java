package epimed_web.repository.mongodb.admin;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.admin.User;

public interface UserRepository extends MongoRepository<User, ObjectId>, UserRepositoryCustom {

}
