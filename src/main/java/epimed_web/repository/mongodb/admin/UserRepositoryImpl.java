package epimed_web.repository.mongodb.admin;

import org.springframework.beans.factory.annotation.Autowired;
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
	
}
