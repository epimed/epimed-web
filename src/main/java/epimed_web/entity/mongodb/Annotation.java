package epimed_web.entity.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "annotation")
@TypeAlias("Annotation")
public class Annotation {
	
	@Id
	public String id; // source_type_tissue
	public String source; // EST, CT
	public String type; // restricted
	public String tissue; // bone marrow
	public String comment;
	
	public Annotation(String source, String type, String tissue) {
		super();
		this.source = source;
		this.type = type;
		this.tissue = tissue;
		this.generateId(source, type, tissue);
	}
	
	public void generateId(String source, String type, String tissue) {
		String tissueUnder = tissue.replaceAll(" ", "_");
		this.id = source.trim() + "_" + type.trim() + "_" + tissueUnder.trim();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public String getTissue() {
		return tissue;
	}


	public void setTissue(String tissue) {
		this.tissue = tissue;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	@Override
	public String toString() {
		return "Annotation [id=" + id + ", source=" + source + ", type=" + type + ", tissue=" + tissue + ", comment="
				+ comment + "]";
	}
	
	
	
}
