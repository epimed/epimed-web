package epimed_web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Configuration
@EnableMongoRepositories(basePackages = "epimed_web.repository.mongodb")
public class MongoConfig {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private MongoClient mongoClient;
	
	protected String getDatabaseName() {
		return mongoTemplate.getDb().getName();
	}

	@Bean
	public MongoDatabase mongoDatabase() {
		return mongoClient.getDatabase(getDatabaseName());
	}

}
