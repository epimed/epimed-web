package epimed_web.entity.neo4j;

import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;

@NodeEntity(label="Annotation")
public class Annotation {

	@GraphId 
	private Long graphId;

	@Id
	@Index
	private String uid;
	private String parameter;
	private DataSource source = DataSource.unknown;
	private String type;
	private String description;
	

	@Relationship(type="BELONGS_TO", direction=Relationship.INCOMING)
	private List<Gene> genes;
	
	
	public Annotation() {
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getParameter() {
		return parameter;
	}


	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public List<Gene> getGenes() {
		return genes;
	}


	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}


	@Override
	public String toString() {
		return "Neo4jAnnotation [graphId=" + graphId + ", uid=" + uid + ", parameter=" + parameter + ", source="
				+ source + ", type=" + type + ", description=" + description + "]";
	}

}