package epimed_web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class GlobalVariablesConfig {

	@Autowired
	private WebApplicationContext webApplicationContext;

	// === Global variables in application scope ===
	@Bean
	public Object applicationScope() {
		webApplicationContext.getServletContext().setAttribute("globalApplicationName", "EpiMed Database");
		webApplicationContext.getServletContext().setAttribute("globalApplicationRootUrl", "http://epimed.univ-grenoble-alpes.fr/database");
		return webApplicationContext;
	}

}
