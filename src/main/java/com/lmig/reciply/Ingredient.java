package com.lmig.reciply;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
@Table(name = "Ingredient")
public class Ingredient  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	int id;
	private String ingredientName;
	private String ingredientMissing;
	public int getId() {
		return id;
	}
	public String getIngredientName() {
		return ingredientName;
	}
	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}
	public String getIngredientMissing() {
		return ingredientMissing;
	}
	public void setIngredientMissing(String ingredientMissing) {
		this.ingredientMissing = ingredientMissing;
	}
	
	@Override
	public String toString() {
		return "Ingredient [Id=" + id + ", ingredientName=" + ingredientName + ", ingredientMissing="
				+ ingredientMissing + "]";
	}
	
}