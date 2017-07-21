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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "YOUR_ENTITY_SEQ")
	@SequenceGenerator(name = "YOUR_ENTITY_SEQ", sequenceName = "YOUR_ENTITY_SEQ", allocationSize = 1)
	int recipeId;
	private String dayNo;
	private String recipeName;
	private String recipeLink;
	private String recipeImgLink;
	

	//	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
//	@ManyToMany(cascade=CascadeType.ALL)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="joinToRecipe")
	private List<Ingredient> ingredients;
	public int getRecipeId() {
		return recipeId;
	}
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public String getRecipeLink() {
		return recipeLink;
	}
	public void setRecipeLink(String recipeLink) {
		this.recipeLink = recipeLink;
	}
	public String getRecipeImgLink() {
		return recipeImgLink;
	}

	public void setRecipeImgLink(String recipeImgLink) {
		this.recipeImgLink = recipeImgLink;
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
		return "Recipe [recipeId=" + recipeId + ", dayNo=" + dayNo + ", recipeName=" + recipeName + ", recipeLink="
				+ recipeLink + ", recipeImgLink=" + recipeImgLink + ", ingredients=" + ingredients + "]";
	}

}