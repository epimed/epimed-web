package epimed_web.entity.neo4j;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;


@NodeEntity
public class Protein {

	@GraphId 
	private Long graphId;

	@Id
	@Index
	private String uid;
	
	private DataSource source = DataSource.uniprot;
	
	
	@Relationship(type="LINKS", direction=Relationship.OUTGOING)
	private  Set<Gene> genes = new HashSet<Gene>();


	public Protein() {
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


	public DataSource getSource() {
		return source;
	}


	public void setSource(DataSource source) {
		this.source = source;
	}


	public Set<Gene> getGenes() {
		return genes;
	}


	public void setGenes(Set<Gene> genes) {
		this.genes = genes;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Protein other = (Protein) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Protein [graphId=" + graphId + ", uid=" + uid + ", source=" + source + ", genes=" + genes + "]";
	}
	
	
	
}
