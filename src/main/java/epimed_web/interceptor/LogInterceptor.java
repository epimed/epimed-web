package epimed_web.interceptor;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.mongodb.LogService;


@Component
public class LogInterceptor extends ApplicationLogger implements HandlerInterceptor {


	@Autowired
	private LogService logService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		try  {
			logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");	

			if (request!=null) {

				if (!request.getRequestURI().contains("ajax") && !request.getRequestURI().contains("admin")) {
					logService.log();
				}

				for (Map.Entry<String,String[]> entry : request.getParameterMap().entrySet()) {
					String key = entry.getKey();
					String[] value = entry.getValue();
					logger.debug("Request \t {}: {}", key, Arrays.toString(value));
				}
			}
		}
		catch (Exception e) {
			// skip
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {

		if (modelAndView!=null) {

			for (Map.Entry<String,Object> entry : modelAndView.getModelMap().entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				logger.debug("Model \t {}: {}", key, value);
			}
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("----- Completed ------");

		if (ex!=null) {
			logService.log(ex.getMessage());
		}

	}


}
