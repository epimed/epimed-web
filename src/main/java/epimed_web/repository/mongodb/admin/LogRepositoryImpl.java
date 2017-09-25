package epimed_web.repository.mongodb.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import epimed_web.entity.mongodb.admin.Log;

public class LogRepositoryImpl implements LogRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	/** ===================================================================================== */ 

	public List<Log> findLastLogs(Integer maxNumber) {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "lastActivity"));
		if (maxNumber!=null) {
			query.limit(maxNumber);
		}
		return mongoTemplate.find(query, Log.class);
	}

	/** ===================================================================================== */ 
	
	public List<Log> findByIPs(List<String> listIPs) {
		Criteria criteria = Criteria.where("single_ip").in(listIPs);
		return mongoTemplate.find(Query.query(criteria), Log.class);
	}

	/** ===================================================================================== */ 

}
