package epimed_web.service.mongodb;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import epimed_web.entity.mongodb.admin.Log;
import epimed_web.repository.mongodb.admin.LogRepository;

@Service
public class LogService {

	@Autowired
	public LogRepository logRepository;

	@Autowired
	public HttpServletRequest request;



	/** ================================================================================= */

	public void log () {
		String comment = null;
		this.log(comment);
	}

	/** ================================================================================= */

	public void log (String comment) {
		if (request!=null) {
			Log log = this.generateLog();
			log.setComment(comment);
			log.setParameter(request.getParameterMap());
			logRepository.save(log);
		}
	}

	/** ================================================================================= */

	public void log (ModelAndView modelAndView) {
		this.log(modelAndView, null);
	}

	/** ================================================================================= */

	public void log (ModelAndView modelAndView, String comment) {
		if (modelAndView!=null && request!=null) {
			Log log = this.generateLog();
			log.setComment(comment);
			log.setParameter(modelAndView.getModelMap());
			logRepository.save(log);
		}
	}

	/** ================================================================================= */


	public Log generateLog() {
		Log log = new Log();
		log.setLastActivity(new Date());
		if (request!=null) {
			log.setIp(this.getIp());
			log.setSingleIp(this.getSingleIp());
			log.setMethod(request.getMethod());
			log.setRoute(request.getContextPath());
			log.setUrl(request.getRequestURI());
		}
		return log;
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

	@SuppressWarnings({ "rawtypes", "unused" })
	private String mapToString(Map<String, ?> map) {

		String text = null;
		int maxElementNb = 5;

		if (map!=null) {


			for (Map.Entry<String, ?> entry : map.entrySet()) {

				String key = entry.getKey();
				Object value = entry.getValue();

				if (value instanceof Collection) {
					if ((( Collection) value).size()>maxElementNb) {
						value = "list of " + (( Collection) value).size() + " values";
					}
				}

				if (value instanceof String[]) {

					if (((String []) value).length>maxElementNb) {
						value = "list of " + ((String []) value).length + " values";
					}

					else {
						value = Arrays.toString((String []) value);
					}
				}


				text = (text==null ? "" : text) + key + ": " + value + " ";
			}
		}

		return text;

	}

	/** ====================================================================================== */

}
