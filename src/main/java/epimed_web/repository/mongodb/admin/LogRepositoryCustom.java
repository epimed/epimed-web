package epimed_web.repository.mongodb.admin;

import java.util.List;

import epimed_web.entity.mongodb.admin.Log;

public interface LogRepositoryCustom {
	
	public List<Log> findLastLogs(Integer maxNumber);
	public List<Log> findByIPs(List<String> listIPs);

}
