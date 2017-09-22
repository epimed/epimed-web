package epimed_web.repository.mongodb;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

public interface SeriesRepositoryCustom {

	public List<Document> list();
	public List<Document> list (Bson filter);
	public List<Document> listWithNumberOfSamples();
	
}
