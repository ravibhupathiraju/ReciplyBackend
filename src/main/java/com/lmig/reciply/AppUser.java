package com.lmig.reciply;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "AppUser")
public class AppUser implements Serializable {
	@Id
	@GeneratedValue
	private int id;

	@NotNull(message = "User Name is required input", groups = New.class)
	@Null(groups = Existing.class)
	@Size(min=6, max=15)
	String userId;
	
	@NotNull(message = "Email is required input", groups = New.class)
	String email;
	
	@NotNull(message = "First Name is required input", groups = New.class)
	@Size(min=2, max=30)
	String firstName;
	
	@NotNull(message = "Last Name is required input", groups = New.class)
	@Size(min=2, max=30)
	String lastName;
	
	@NotNull(message = "Password is required input", groups = New.class)
	@Size(min=6, max=16)
	String password;

	public AppUser() {

	}

	public AppUser(String userId, String email, String firstName,
			String lastName, String password) {
		super();
		this.userId = userId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AppUser [id=" + id + ", userId=" + userId + ", email=" + email
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + "]";
	}

	// Method to update any changes to User fields during PUT call
	public void merge(AppUser other) {
		if (other.firstName != null) {
			this.firstName = other.firstName;
		}
		if (other.lastName != null) {
			this.lastName = other.lastName;
		}
		if (other.email != null) {
			this.email = other.email;
		}
		if (other.userId != null) {
			this.userId = other.userId;
		}
		if (other.password != null) {
			this.password = other.password;
		}
	}

	// Added for field validations using the JPA annotations
    public interface Existing {
    }

    public interface New {
    }	
	
}