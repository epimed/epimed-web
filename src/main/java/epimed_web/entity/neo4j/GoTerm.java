package epimed_web.entity.neo4j;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.annotation.Id;

@NodeEntity
public class GoTerm {

	@GraphId 
	private Long graphId;

	@Id
	@Index
	private String uid;
	private String name;
	private String aspect;
	private String description;
	

	
	public GoTerm() {
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAspect() {
		return aspect;
	}
	public void setAspect(String aspect) {
		this.aspect = aspect;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "GoTerm [graphId=" + graphId + ", uid=" + uid + ", name=" + name + ", aspect=" + aspect
				+ ", description=" + description + "]";
	}
	
	
	
	
}