package epimed_web.repository.mongodb.download;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class DownloadRepositoryImpl implements DownloadRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	/** ========================================================== */
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findDistinctCategories() {
		List<String> list = mongoTemplate.getCollection("download").distinct("category");
		Collections.sort(list);
		return list;
	}
	
	/** ========================================================== */
	
}
