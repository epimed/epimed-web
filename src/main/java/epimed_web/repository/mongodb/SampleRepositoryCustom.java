package epimed_web.repository.mongodb;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import epimed_web.entity.mongodb.Sample;

public interface SampleRepositoryCustom {

	public List<Document> getTopologyDistribution();
	public List<Sample> findInSeries (Object [] listIdSeries);
	public List<Document> listMorphologies();
	public List<Document> listTopologies();
	public List<String> listPlatforms (Bson filter);
	public List<String> listSeries (Bson filter);
	public List<Document> listSamples ();
	public List<Document> listSamples (Bson filter);
	public Long count (Bson filter);
	public List<Document> getSamples (String[] listIdSamples, String projectionName);
	public List<Document> getSamples (Bson filter, String projectionName);
	public List<Document> findSamples (String[] listIdSeries, String projectionName);
	public List<Document> getSeries (String[] listIdSamples);
	public boolean updateSample (String idSample, String projectionName, String key, String value);

}
