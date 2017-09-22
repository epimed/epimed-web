package epimed_web.form;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import epimed_web.entity.mongodb.jobs.JobType;
import epimed_web.entity.neo4j.Gene;

public class AjaxForm {

	private JobType jobtype;
	private String symbol;
	private Integer taxid;
	private String parameter;
	
	private String idPlatform;	
	
	private boolean success = false;
	private int nbGenes = 0;
	private String source;
	
	private List<Gene> listGenes = new ArrayList<Gene>();
	private Set<String> probesets = new HashSet<String>();
	
	public AjaxForm() {
		super();
	}

	public JobType getJobtype() {
		return jobtype;
	}

	public void setJobtype(JobType jobtype) {
		this.jobtype = jobtype;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getTaxid() {
		return taxid;
	}

	public void setTaxid(Integer taxid) {
		this.taxid = taxid;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getIdPlatform() {
		return idPlatform;
	}

	public void setIdPlatform(String idPlatform) {
		this.idPlatform = idPlatform;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getNbGenes() {
		return nbGenes;
	}

	public void setNbGenes(int nbGenes) {
		this.nbGenes = nbGenes;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<Gene> getListGenes() {
		return listGenes;
	}

	public void setListGenes(List<Gene> listGenes) {
		this.listGenes = listGenes;
	}

	public Set<String> getProbesets() {
		return probesets;
	}

	public void setProbesets(Set<String> probesets) {
		this.probesets = probesets;
	}

	@Override
	public String toString() {
		return "AjaxForm [jobtype=" + jobtype + ", symbol=" + symbol + ", taxid=" + taxid + ", parameter=" + parameter
				+ ", idPlatform=" + idPlatform + ", success=" + success + ", nbGenes=" + nbGenes + ", source=" + source
				+ ", listGenes=" + listGenes + ", probesets=" + probesets + "]";
	}

}

