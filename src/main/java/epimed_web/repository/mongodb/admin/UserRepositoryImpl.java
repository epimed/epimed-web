package epimed_web.repository.mongodb.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import epimed_web.entity.mongodb.admin.User;

public class UserRepositoryImpl implements UserRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	/** ===================================================================================== */
	
	@Override
	public User findByIp(String ip) {
		Criteria criteria = Criteria.where("ip").in(ip);
		return mongoTemplate.findOne(Query.query(criteria), User.class);
	}

	/** ===================================================================================== */
	
	public List<User> findAllSortedByLastName() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.ASC, "lastName"));
		return mongoTemplate.find(query, User.class);
	}
	
}
