package epimed_web.repository.mongodb;

import java.util.List;

import epimed_web.entity.mongodb.genes.Gene;

public interface GeneRepositoryCustom {
	
	public List<Gene> findInAnnotations(String annotation);
	public List<Gene> findInAnnotations(List<String> listAnnotations);

}
