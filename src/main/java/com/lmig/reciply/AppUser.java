package com.lmig.reciply;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "AppUser")
public class AppUser implements Serializable {
	@Id
	@GeneratedValue
	int id;

	String name;
	String location;
	
//	@CreationTimestamp
//	@Temporal(TemporalType.TIMESTAMP)
//	Date dateJoined;

////	still in testing phase
//	@ManyToMany(cascade=CascadeType.ALL)
//	private Set<Movie> movies;

	public AppUser() {
//		still in testing phase
//		this.movies = new HashSet<Movie>();
	}

	public AppUser(String name, String location, Date dateJoined) {
		this();
		this.name = name;
		this.location = location;
//		this.dateJoined = dateJoined;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

//	public Date getDateJoined() {
//		return dateJoined;
//	}
//	
//	public Date setDateJoined() {
//		return dateJoined;
//	}

////still in testing phase
//	public Set<Movie> getMovies() {
//		return movies;
//	}
//
//	public void setMovies(Set<Movie> movies) {
//		this.movies = movies;
//	}

    //PUT method
    public void merge(AppUser other) {
        if (other.location != null) {
            this.location = other.location;
        }
        if (other.name != null){
            this.name=other.name;
        }
    }
}