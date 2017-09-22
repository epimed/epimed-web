package epimed_web.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.Annotation;

public interface AnnotationRepository extends MongoRepository<Annotation, String>, AnnotationRepositoryCustom {

}
