package epimed_web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import epimed_web.interceptor.LogInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private LogInterceptor logInterceptor;

	// === Interceptors ===
	
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor);
	}

}
