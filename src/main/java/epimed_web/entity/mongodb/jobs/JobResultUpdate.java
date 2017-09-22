package epimed_web.entity.mongodb.jobs;

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

@TypeAlias("JobResultUpdate")
public class JobResultUpdate extends JobResult {

	@Field("tax_id")
	private Integer taxid;

	private Integer entrez;

	@Field("gene_symbol")
	private String geneSymbol;

	private String title;
	private String location;
	private String chrom;
	private String status;
	private Set<String> aliases;
	private String database;
	
	public JobResultUpdate() {
		super();
	}

	public Integer getTaxid() {
		return taxid;
	}

	public void setTaxid(Integer taxid) {
		this.taxid = taxid;
	}

	public Integer getEntrez() {
		return entrez;
	}

	public void setEntrez(Integer entrez) {
		this.entrez = entrez;
	}

	public String getGeneSymbol() {
		return geneSymbol;
	}

	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getChrom() {
		return chrom;
	}

	public void setChrom(String chrom) {
		this.chrom = chrom;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<String> getAliases() {
		return aliases;
	}

	public void setAliases(Set<String> aliases) {
		this.aliases = aliases;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	@Override
	public String toString() {
		return "JobResultUpdate [taxid=" + taxid + ", entrez=" + entrez + ", geneSymbol=" + geneSymbol + ", title="
				+ title + ", location=" + location + ", chrom=" + chrom + ", status=" + status + ", aliases=" + aliases
				+ ", database=" + database + ", getInput()=" + getInput() + "]";
	}

	
}
