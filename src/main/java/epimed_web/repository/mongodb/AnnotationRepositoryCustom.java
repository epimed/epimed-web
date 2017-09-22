package epimed_web.repository.mongodb;

import java.util.List;

public interface AnnotationRepositoryCustom {

	List<String> findDistinctAnnotations();
	
}
