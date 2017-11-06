package epimed_web.repository.mongodb.experiments;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

public interface PlatformRepositoryCustom {

	public List<String> listPlatformTypes();
	public List<Document> list();
	public List<Document> list (Bson filter);
	public List<Document> listPlatforms(String type);
	public List<String> listIdPlatforms(String type);
	
}
