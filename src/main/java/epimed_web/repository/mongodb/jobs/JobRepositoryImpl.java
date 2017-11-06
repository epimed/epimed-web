package epimed_web.repository.mongodb.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import epimed_web.entity.mongodb.jobs.Job;

public class JobRepositoryImpl implements JobRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	/** ===================================================================================== */ 

	public List<Job> findLastLogs(Integer maxNumber) {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "lastActivity"));
		if (maxNumber!=null) {
			query.limit(maxNumber);
		}
		return mongoTemplate.find(query, Job.class);
	}

	/** ===================================================================================== */ 

	public List<Job> findByIPs(List<String> listIPs, Integer maxNumber) {
		Criteria criteria = Criteria.where("single_ip").in(listIPs);
		Query query = Query.query(criteria);
		if (maxNumber!=null) {
			query.limit(maxNumber);
		}
		query.with(new Sort(Sort.Direction.DESC, "lastActivity"));
		return mongoTemplate.find(query, Job.class);
	}

	/** ===================================================================================== */ 

	public List<Job> findByIPs(List<String> listIPs) {
		return this.findByIPs(listIPs, null);
	}


	/** ===================================================================================== */ 
}
