package epimed_web.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.Gene;

public interface GeneRepository extends MongoRepository<Gene, Integer>, GeneRepositoryCustom {

	public Gene findById(Integer idGene);
}
