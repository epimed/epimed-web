package epimed_web.repository.mongodb;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

@SuppressWarnings("unchecked")
public class AnnotationRepositoryImpl implements AnnotationRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	/** ========================================================== */
	
	
	@Override
	public List<String> findDistinctAnnotations() {
		List<String> list = mongoTemplate.getCollection("annotation").distinct("_id");
		Collections.sort(list);
		return list;
	}
	
	/** ========================================================== */

}
