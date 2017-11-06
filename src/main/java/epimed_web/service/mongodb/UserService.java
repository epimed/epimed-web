package epimed_web.service.mongodb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epimed_web.entity.mongodb.admin.Role;
import epimed_web.entity.mongodb.admin.User;
import epimed_web.repository.mongodb.admin.UserRepository;
import epimed_web.service.log.ApplicationLogger;

@Service
public class UserService extends ApplicationLogger {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	@Autowired
	public UserRepository userRepository;

	@Autowired
	public HttpServletRequest request;

	
	/** ====================================================================================== */
	
	public String generateUniqueUserId() {
		Date currentDate = new Date();
		Random random = new Random();
		String jobid = "USER" + dateFormat.format(currentDate) + "F" + random.nextInt(1000);
		
		boolean isAlreadyExists = userRepository.exists(jobid);
		int i=0; 
		int max = 10;
		while (isAlreadyExists && i<max) {
			jobid = "USER" +  dateFormat.format(currentDate) + "S" + random.nextInt(1000);
			isAlreadyExists = userRepository.exists(jobid);
			i++;
		}
		
		return jobid;
		
	}

	
	/** ====================================================================================== */

	public User generateDefaultUser() {
		// === Default unknown user ===
		User defaultUser = new User();
		defaultUser.setFirstName("Unknown");
		defaultUser.setLastName("user");
		return defaultUser;
	}

	/** ====================================================================================== */

	public boolean isAdmin () {
		boolean result = false;
		String singleIp = this.getSingleIp();
		if (singleIp!=null) {
			User user = userRepository.findByIp(singleIp);
			if (user!=null && user.getRole().equals(Role.ROLE_ADMIN)) {
				result = true;
			}
		}
		return result;
	}
	
	/** ================================================================================= */
	
	public User getUser() {
		User user = null;
		String singleIp = this.getSingleIp();
		if (singleIp!=null) {
			user = userRepository.findByIp(singleIp);
		}
		return user;
	}

	/** ================================================================================= */

	public String getIp() {
		return request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
	}

	/** ====================================================================================== */

	public String getSingleIp() {

		String ipAddress = this.getIp();

		String singleIp = ipAddress;
		if (ipAddress!=null && !ipAddress.isEmpty()) {
			singleIp = ipAddress.split("[,;\\p{Space}]")[0];
		}

		return singleIp;
	}
	/** ====================================================================================== */

}
