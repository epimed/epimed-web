package epimed_web.repository.mongodb.admin;

import java.util.List;

import epimed_web.entity.mongodb.admin.User;

public interface UserRepositoryCustom {

	public User findByIp(String ip); 
	public List<User> findAllSortedByLastName();
	
}
