package epimed_web.entity.mongodb;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "gene")
@TypeAlias("Gene")
public class Gene {

	@Id
	private Integer id;
	
	@Field("gene_symbol")
	private String geneSymbol;
	private String title;
	private String location;
	private String chrom;
	
	private String status;
	private String type;
	private String source;
	
	@Field("replaced_by")
	private Integer replacedBy;
	
	private Boolean removed;
	private String locusGroup;
	
	@Field("date_modified")
	private Date dateModified;
	
	@Field("last_update")
	private Date lastUpdate;
	
	private Set<String> aliases = new HashSet<String>();
	private Set<String> annotations = new HashSet<String>();
	
	private org.bson.Document expression;
	
	public Gene() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getReplacedBy() {
		return replacedBy;
	}

	public void setReplacedBy(Integer replacedBy) {
		this.replacedBy = replacedBy;
	}

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}

	public String getLocusGroup() {
		return locusGroup;
	}

	public void setLocusGroup(String locusGroup) {
		this.locusGroup = locusGroup;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set<String> getAliases() {
		return aliases;
	}

	public void setAliases(Set<String> aliases) {
		this.aliases = aliases;
	}

	public Set<String> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Set<String> annotations) {
		this.annotations = annotations;
	}

	

	public org.bson.Document getExpression() {
		return expression;
	}

	public void setExpression(org.bson.Document expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "Gene [id=" + id + ", geneSymbol=" + geneSymbol + ", title=" + title + ", location=" + location
				+ ", chrom=" + chrom + ", status=" + status + ", type=" + type + ", source=" + source + ", replacedBy="
				+ replacedBy + ", removed=" + removed + ", locusGroup=" + locusGroup + ", dateModified=" + dateModified
				+ ", lastUpdate=" + lastUpdate + ", aliases=" + aliases + ", annotations=" + annotations + "]";
	}

	
}
