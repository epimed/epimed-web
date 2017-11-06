package epimed_web.entity.mongodb.genes;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "expression")
@TypeAlias("Expression")
public class Expression {

	@Id
	private String id; // idGene:idSample

	@Field("id_gene")
	private Integer idGene;

	@Field("id_sample")
	private String idSample;

	@Field("id_tissue_stage")
	private Integer idTissueStage;

	@Field("tissue_stage")
	private String tissueStage;

	@Field("id_tissue_status")
	private Integer idTissueStatus;

	@Field("tissue_status")
	private String tissueStatus;

	@Field("id_topology")
	private String idTopology;

	@Field("topology")
	private String topology;

	@Field("id_topology_group")
	private String idTopologyGroup;

	@Field("topology_group")
	private String topologyGroup;

	private String tissue;
	private Double value;
	private String unit;


	public Expression() {
		super();
	}

	public Expression(Integer idGene, String idSample) {
		super();
		this.idGene = idGene;
		this.idSample = idSample;
		this.generateId(idGene, idSample); 
	}

	public Expression(Integer idGene, String idSample, Integer idTissueStage, String tissueStage,
			Integer idTissueStatus, String tissueStatus, String idTopology, String topology, String idTopologyGroup,
			String topologyGroup, String tissue, Double value, String unit) {
		super();
		this.idGene = idGene;
		this.idSample = idSample;
		this.idTissueStage = idTissueStage;
		this.tissueStage = tissueStage;
		this.idTissueStatus = idTissueStatus;
		this.tissueStatus = tissueStatus;
		this.idTopology = idTopology;
		this.topology = topology;
		this.idTopologyGroup = idTopologyGroup;
		this.topologyGroup = topologyGroup;
		this.tissue = tissue;
		this.value = value;
		this.unit = unit;
		this.generateId(idGene, idSample); 
	}





	public String getIdSample() {
		return idSample;
	}
	public void setIdSample(String idSample) {
		this.idSample = idSample;
	}
	public Integer getIdTissueStage() {
		return idTissueStage;
	}
	public void setIdTissueStage(Integer idTissueStage) {
		this.idTissueStage = idTissueStage;
	}
	public Integer getIdTissueStatus() {
		return idTissueStatus;
	}
	public void setIdTissueStatus(Integer idTissueStatus) {
		this.idTissueStatus = idTissueStatus;
	}




	public String getTissueStage() {
		return tissueStage;
	}



	public void setTissueStage(String tissueStage) {
		this.tissueStage = tissueStage;
	}



	public String getTissueStatus() {
		return tissueStatus;
	}



	public void setTissueStatus(String tissueStatus) {
		this.tissueStatus = tissueStatus;
	}



	public String getIdTopology() {
		return idTopology;
	}



	public void setIdTopology(String idTopology) {
		this.idTopology = idTopology;
	}



	public String getTopology() {
		return topology;
	}



	public void setTopology(String topology) {
		this.topology = topology;
	}



	public String getIdTopologyGroup() {
		return idTopologyGroup;
	}



	public void setIdTopologyGroup(String idTopologyGroup) {
		this.idTopologyGroup = idTopologyGroup;
	}



	public String getTopologyGroup() {
		return topologyGroup;
	}



	public void setTopologyGroup(String topologyGroup) {
		this.topologyGroup = topologyGroup;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Integer getIdGene() {
		return idGene;
	}



	public void setIdGene(Integer idGene) {
		this.idGene = idGene;
	}



	public String getTissue() {
		return tissue;
	}
	public void setTissue(String tissue) {
		this.tissue = tissue;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}



	@Override
	public String toString() {
		return "Expression [id=" + id + ", idGene=" + idGene + ", idSample=" + idSample + ", idTissueStage="
				+ idTissueStage + ", tissueStage=" + tissueStage + ", idTissueStatus=" + idTissueStatus
				+ ", tissueStatus=" + tissueStatus + ", idTopology=" + idTopology + ", topology=" + topology
				+ ", idTopologyGroup=" + idTopologyGroup + ", topologyGroup=" + topologyGroup + ", tissue=" + tissue
				+ ", value=" + value + ", unit=" + unit + "]";
	}

	public void generateId(Integer idGene, String idSample) {
		this.id = idGene + ":" + idSample;
	}



}
