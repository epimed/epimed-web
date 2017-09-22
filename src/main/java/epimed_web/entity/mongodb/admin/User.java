package epimed_web.entity.mongodb.admin;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "user")
@TypeAlias("User")
public class User {
	
	@Id
	private ObjectId id;
	
	@Field("first_name")
	private String firstName;
	
	@Field("last_name")
	private String lastName;
	
	private String organization;
	Role role = Role.ROLE_USER;
	List<String> ip = new ArrayList<String>();
	

	public User() {
		super();
	}


	public User(String firstName, String lastName, String organization, Role role, List<String> ip) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.organization = organization;
		this.role = role;
		this.ip = ip;
	}


	public ObjectId getId() {
		return id;
	}


	public void setId(ObjectId id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getOrganization() {
		return organization;
	}


	public void setOrganization(String organization) {
		this.organization = organization;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public List<String> getIp() {
		return ip;
	}


	public void setIp(List<String> ip) {
		this.ip = ip;
	}


	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", organization="
				+ organization + ", role=" + role + ", ip=" + ip + "]";
	}

}
