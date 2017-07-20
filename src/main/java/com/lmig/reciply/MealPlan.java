package com.lmig.reciply;

import java.io.Serializable;
import java.time.LocalDate;
//import java.util.Date;
//import java.sql.Date
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.lmig.reciply.Recipe;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "MealPlan")
public class MealPlan  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	int planId;
	private String userID;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate weekBeginDate;
	@ManyToMany(cascade = CascadeType.ALL) 
	@JoinColumn(name = "recipeId")
	private List<Recipe> recipes; 
//	private int recipeId;

	public int getPlanId() {
		return planId;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public LocalDate getWeekBeginDate() {
		return weekBeginDate;
	}

	public void setWeekBeginDate(LocalDate weekBeginDate) {
		this.weekBeginDate = weekBeginDate;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipe(List<Recipe> recipes) {
		recipes = recipes;
	}

	@Override
	public String toString() {
		return "MealPlan [planId=" + planId + ", userID=" + userID + ", weekBeginDate=" + weekBeginDate + ", Recipe="
				+ recipes + "]";
	}

//	public int getRecipeId() {
//		return recipeId;
//	}
//
//	public void setRecipeId(int recipeId) {
//		this.recipeId = recipeId;
//	}

}