package epimed_web.repository.mongodb.jobs;

import java.util.List;

import epimed_web.entity.mongodb.jobs.Job;

public interface JobRepositoryCustom {

	public List<Job> findLastLogs(Integer maxNumber);
	public List<Job> findByIPs(List<String> listIPs);
	public List<Job> findByIPs(List<String> listIPs, Integer maxNumber);
}
