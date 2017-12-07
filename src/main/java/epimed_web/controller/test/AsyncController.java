package epimed_web.controller.test;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import epimed_web.service.log.ApplicationLogger;

@RestController
public class AsyncController extends ApplicationLogger {

	/** ====================================================================================== */

	@Async
	@RequestMapping(value = "/coucou", method = RequestMethod.GET)
	public void asyncCoucou() {
		
		logger.debug("Async coucou");
		
	}
	
}
