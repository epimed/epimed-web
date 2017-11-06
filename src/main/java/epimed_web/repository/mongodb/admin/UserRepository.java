package epimed_web.repository.mongodb.admin;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.admin.User;

public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

	public User findByLastName(String lastName);
	public User findByFirstName(String firstName);
	
}
