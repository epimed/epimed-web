package epimed_web.form;

import java.util.List;

public class SampleForm {

	private String platformType;
	private String idPlatform;
	private String idTopology;
	private Integer idTissueStatus=3;
	private Integer idTissueStage=1;
	private String idMorphology;
	private Boolean survival=false;

	private Long nbSamples;
	private List<String> listSeries;
	private List<String> listPlatforms;
	private String urlParameter;

	public String getIdPlatform() {
		return idPlatform;
	}

	public void setIdPlatform(String idPlatform) {
		this.idPlatform = idPlatform;
	}

	public String getIdTopology() {
		return idTopology;
	}

	public void setIdTopology(String idTopology) {
		this.idTopology = idTopology;
	}

	public Integer getIdTissueStatus() {
		return idTissueStatus;
	}

	public void setIdTissueStatus(Integer idTissueStatus) {
		this.idTissueStatus = idTissueStatus;
	}

	public Integer getIdTissueStage() {
		return idTissueStage;
	}

	public void setIdTissueStage(Integer idTissueStage) {
		this.idTissueStage = idTissueStage;
	}

	public String getIdMorphology() {
		return idMorphology;
	}

	public void setIdMorphology(String idMorphology) {
		this.idMorphology = idMorphology;
	}



	public Boolean getSurvival() {
		return survival;
	}

	public void setSurvival(Boolean survival) {
		this.survival = survival;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public List<String> getListSeries() {
		return listSeries;
	}

	public void setListSeries(List<String> listSeries) {
		this.listSeries = listSeries;
	}

	public Long getNbSamples() {
		return nbSamples;
	}

	public void setNbSamples(Long nbSamples) {
		this.nbSamples = nbSamples;
	}


	public String getUrlParameter() {
		return urlParameter;
	}

	public void setUrlParameter(String urlParameter) {
		this.urlParameter = urlParameter;
	}

	public List<String> getListPlatforms() {
		return listPlatforms;
	}

	public void setListPlatforms(List<String> listPlatforms) {
		this.listPlatforms = listPlatforms;
	}


	@Override
	public String toString() {
		return "SampleForm [platformType=" + platformType + ", idPlatform=" + idPlatform + ", idTopology="
				+ idTopology + ", idTissueStatus=" + idTissueStatus + ", idTissueStage=" + idTissueStage
				+ ", idMorphology=" + idMorphology + ", survival=" + survival + ", nbSamples=" + nbSamples
				+ ", listSeries=" + listSeries + ", listPlatforms=" + listPlatforms + ", urlParameter=" + urlParameter
				+ "]";
	}

	/** =============================================================== */

	public void clearResults() {
		this.nbSamples = null;
		this.listSeries = null;
		this.listPlatforms = null;
		this.urlParameter = null;
	}

	/** =============================================================== */

	public void generateUrlParameter () {

		String result = "id_tissue_status=" + this.idTissueStatus + "&id_tissue_stage=" + this.idTissueStage;

		if (idPlatform!=null && !idPlatform.isEmpty()) {
			result = result + "&id_platform=" + this.idPlatform;
		}
		else {
			if (platformType!=null && !platformType.isEmpty()) {
				result = result + "&platform_type=" + this.platformType;
			}
		}

		if (idTopology!=null && !idTopology.isEmpty()) {
			result = result + "&id_topology=" + this.idTopology;
		}

		if (idMorphology!=null && !idMorphology.isEmpty()) {
			result = result + "&id_morphology=" + this.idMorphology;
		}

		if (survival!=null && survival) {
			result = result + "&survival=" + survival;
		}

		result = result + "&format=csv"; 

		this.setUrlParameter(result);
	}

	/** =============================================================== */

}
