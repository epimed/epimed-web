package epimed_web.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.Series;

public interface SeriesRepository extends MongoRepository<Series,String>, SeriesRepositoryCustom {

}
