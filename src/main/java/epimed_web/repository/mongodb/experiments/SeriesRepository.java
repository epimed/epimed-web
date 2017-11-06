package epimed_web.repository.mongodb.experiments;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.experiments.Series;

public interface SeriesRepository extends MongoRepository<Series,String>, SeriesRepositoryCustom {

}
