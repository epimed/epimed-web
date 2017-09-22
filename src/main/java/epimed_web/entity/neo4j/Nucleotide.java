package epimed_web.entity.neo4j;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;


@NodeEntity
public class Nucleotide {

	@GraphId 
	private Long graphId;

	@Id
	@Index
	private String uid;
	
	private DataSource source = DataSource.unknown;

	@Relationship(type="LINKS", direction=Relationship.UNDIRECTED)
	private Set<Gene> genes = new HashSet<Gene>();
	
	@Relationship(type="LINKS", direction=Relationship.UNDIRECTED)
	private Set<Nucleotide> nucleotides = new HashSet<Nucleotide>();

	public Nucleotide() {
		super();
	}

	public Long getGraphId() {
		return graphId;
	}

	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}



	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Set<Gene> getGenes() {
		return genes;
	}

	public void setGenes(Set<Gene> genes) {
		this.genes = genes;
	}

	public DataSource getSource() {
		return source;
	}

	public void setSource(DataSource source) {
		this.source = source;
	}

	
	
	public Set<Nucleotide> getNucleotides() {
		return nucleotides;
	}

	public void setNucleotides(Set<Nucleotide> nucleotides) {
		this.nucleotides = nucleotides;
	}

	@Override
	public String toString() {
		return "Nucleotide [graphId=" + graphId + ", uid=" + uid + ", source=" + source + ", genes=" + genes + "]";
	}


}
