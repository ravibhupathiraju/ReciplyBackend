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
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "Ingredient")
public class Ingredient  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
//	@GeneratedValue
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Ingredient_ENTITY_SEQ")
	@SequenceGenerator(name = "Ingredient_ENTITY_SEQ", sequenceName = "Ingredient_ENTITY_SEQ", allocationSize = 1)
//	@ApiModelProperty(value = "Ingredient ID", required = true)
//	@NotNull 
	int id;
//	@ApiModelProperty(value = "Ingredient name", required = true)
//	@NotNull
	private String ingredientName;
//	@ApiModelProperty(value = "Ingredient Missing Y", required = true)
//	@NotNull
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