package com.lmig.reciply;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "Recipe")
public class Recipe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
//	@GeneratedValue
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Recipe_ENTITY_SEQ")
	@SequenceGenerator(name = "Recipe_ENTITY_SEQ", sequenceName = "Recipe_ENTITY_SEQ", allocationSize = 1)
	int recipeId;
	private String dayNo;
	private String title;
	private String href;
	private String thumbnail;
	

	//	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
//	@ManyToMany(cascade=CascadeType.ALL)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="joinToRecipe")  
	private List<Ingredient> ingredients;
	public int getRecipeId() {
		return recipeId;
	}
	public String gettitle() {
		return title;
	}
	public void settitle(String title) {
		this.title = title;
	}
	public String gethref() {
		return href;
	}
	public void sethref(String href) {
		this.href = href;
	}
	public String getthumbnail() {
		return thumbnail;
	}

	public void setthumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	public String getDayNo() {
		return dayNo;
	}
	public void setDayNo(String dayNo) {
		this.dayNo = dayNo;
	}
	@Override
	public String toString() {
		return "Recipe [recipeId=" + recipeId + ", dayNo=" + dayNo + ", title=" + title + ", href="
				+ href + ", thumbnail=" + thumbnail + ", ingredients=" + ingredients + "]";
	}

}