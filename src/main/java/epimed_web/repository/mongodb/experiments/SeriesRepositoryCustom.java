package epimed_web.repository.mongodb.experiments;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import epimed_web.entity.mongodb.experiments.Series;

public interface SeriesRepositoryCustom {

	public List<Document> list();
	public List<Document> list (Bson filter);
	public List<Document> listWithNumberOfSamples();
	public List<Series> findByText(String text);
	
}
