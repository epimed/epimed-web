package epimed_web.entity.neo4j;

import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;

@NodeEntity(label="Annotation")
public class Annotation2 {

	@GraphId 
	private Long graphId;

	@Id
	@Index
	private String uid;
	
	private String parameter;
	private String source;
	private String type;
	private String description;
	
	@Property(name="tissue_status")
	private String tissueStatus;
	
	@Property(name="tissue_stage")
	private String tissueStage;
	
	@Property(name="tissue_group_level1")
	private String tissueGroupLevel1;
	
	@Property(name="tissue_group_level2")
	private String tissueGroupLevel2;
	
	@Property(name="tissue_group_level3")
	private String tissueGroupLevel3;
	
	@Relationship(type="BELONGS_TO", direction=Relationship.INCOMING)
	private List<Gene> genes;
	
	
	public Annotation2() {
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


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
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

	

	public String getTissueStatus() {
		return tissueStatus;
	}


	public void setTissueStatus(String tissueStatus) {
		this.tissueStatus = tissueStatus;
	}


	public String getTissueStage() {
		return tissueStage;
	}


	public void setTissueStage(String tissueStage) {
		this.tissueStage = tissueStage;
	}


	public String getTissueGroupLevel1() {
		return tissueGroupLevel1;
	}


	public void setTissueGroupLevel1(String tissueGroupLevel1) {
		this.tissueGroupLevel1 = tissueGroupLevel1;
	}


	public String getTissueGroupLevel2() {
		return tissueGroupLevel2;
	}


	public void setTissueGroupLevel2(String tissueGroupLevel2) {
		this.tissueGroupLevel2 = tissueGroupLevel2;
	}


	public String getTissueGroupLevel3() {
		return tissueGroupLevel3;
	}


	public void setTissueGroupLevel3(String tissueGroupLevel3) {
		this.tissueGroupLevel3 = tissueGroupLevel3;
	}


	public List<Gene> getGenes() {
		return genes;
	}


	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}


	@Override
	public String toString() {
		return "Annotation [graphId=" + graphId + ", uid=" + uid + ", parameter=" + parameter + ", source=" + source
				+ ", type=" + type + ", description=" + description + ", tissueStatus=" + tissueStatus
				+ ", tissueStage=" + tissueStage + ", tissueGroupLevel1=" + tissueGroupLevel1 + ", tissueGroupLevel2="
				+ tissueGroupLevel2 + ", tissueGroupLevel3=" + tissueGroupLevel3 + "]";
	}



}