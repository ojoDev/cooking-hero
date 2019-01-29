package com.ojodev.cookinghero.recipes.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Recipe created by a user
 */
@ApiModel(description = "Recipe created by a user")
@Validated

@javax.annotation.Generated(value = "io.swagger.codegen.languages.java.SpringCodegen", date = "2019-01-28T21:05:32.614+01:00[Europe/Paris]")

public class Recipe {

	@JsonProperty("id")

	private String id = null;

	@JsonProperty("name")

	private String name = null;

	@JsonProperty("description")

	private String description = null;

	@JsonProperty("cousine-type")

	@Valid
	private List<String> cousineType = new ArrayList<>();

	@JsonProperty("preparation-time")

	private BigDecimal preparationTime = null;

	@JsonProperty("cooking-time")

	private BigDecimal cookingTime = null;

	@JsonProperty("difficulty")

	private BigDecimal difficulty = null;

	@JsonProperty("photo")

	private PhotoRef photo = null;

	@JsonProperty("steps")

	@Valid
	private List<Step> steps = null;

	@JsonProperty("ingredients")

	@Valid
	private List<Ingredient> ingredients = null;

	@JsonProperty("user")

	private String user = null;

	@JsonProperty("creationDate")

	private DateTime creationDate = null;

	public Recipe id(String id) {
		this.id = id;
		return this;
	}

	/**
	 * 
	 * recipe id
	 * 
	 * 
	 * 
	 * 
	 * @return id
	 **/

	@ApiModelProperty(example = "8899457821", required = true, value = "recipe id")

	@NotNull

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Recipe name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 
	 * name of the recipe
	 * 
	 * 
	 * 
	 * 
	 * @return name
	 **/

	@ApiModelProperty(example = "spanish tortilla", required = true, value = "name of the recipe")

	@NotNull

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Recipe description(String description) {
		this.description = description;
		return this;
	}

	/**
	 * 
	 * general description of the recipe
	 * 
	 * 
	 * 
	 * 
	 * @return description
	 **/

	@ApiModelProperty(example = "classic Spanish omelette filled with pan-fried potatoes and onion.", required = true, value = "general description of the recipe")

	@NotNull

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Recipe cousineType(List<String> cousineType) {
		this.cousineType = cousineType;
		return this;
	}

	public Recipe addCousineTypeItem(String cousineTypeItem) {

		this.cousineType.add(cousineTypeItem);
		return this;
	}

	/**
	 * 
	 * cousine type
	 * 
	 * 
	 * 
	 * 
	 * @return cousineType
	 **/

	@ApiModelProperty(example = "[\"spanish\",\"veggie\"]", required = true, value = "cousine type")

	@NotNull

	public List<String> getCousineType() {
		return cousineType;
	}

	public void setCousineType(List<String> cousineType) {
		this.cousineType = cousineType;
	}

	public Recipe preparationTime(BigDecimal preparationTime) {
		this.preparationTime = preparationTime;
		return this;
	}

	/**
	 * 
	 * ingredient preparation time in minutes (cut potatoes, ...)
	 * 
	 * 
	 * 
	 * 
	 * @return preparationTime
	 **/

	@ApiModelProperty(example = "15", required = true, value = "ingredient preparation time in minutes (cut potatoes, ...)")

	@NotNull

	@Valid

	public BigDecimal getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(BigDecimal preparationTime) {
		this.preparationTime = preparationTime;
	}

	public Recipe cookingTime(BigDecimal cookingTime) {
		this.cookingTime = cookingTime;
		return this;
	}

	/**
	 * 
	 * cooking time in minutes (boil, ...)
	 * 
	 * 
	 * 
	 * 
	 * @return cookingTime
	 **/

	@ApiModelProperty(example = "40", required = true, value = "cooking time in minutes (boil, ...)")

	@NotNull

	@Valid

	public BigDecimal getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(BigDecimal cookingTime) {
		this.cookingTime = cookingTime;
	}

	public Recipe difficulty(BigDecimal difficulty) {
		this.difficulty = difficulty;
		return this;
	}

	/**
	 * 
	 * difficult level
	 * 
	 * 
	 * 
	 * minimum: 1
	 * 
	 * 
	 * maximum: 5
	 * 
	 * @return difficulty
	 **/

	@ApiModelProperty(example = "4", required = true, value = "difficult level")

	@NotNull

	@Valid
	@DecimalMin("1")
	@DecimalMax("5")
	public BigDecimal getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(BigDecimal difficulty) {
		this.difficulty = difficulty;
	}

	public Recipe photo(PhotoRef photo) {
		this.photo = photo;
		return this;
	}

	/**
	 * 
	 * 
	 * Get photo
	 * 
	 * 
	 * 
	 * @return photo
	 **/

	@ApiModelProperty(value = "")

	@Valid

	public PhotoRef getPhoto() {
		return photo;
	}

	public void setPhoto(PhotoRef photo) {
		this.photo = photo;
	}

	public Recipe steps(List<Step> steps) {
		this.steps = steps;
		return this;
	}

	public Recipe addStepsItem(Step stepsItem) {

		if (this.steps == null) {
			this.steps = new ArrayList<>();
		}

		this.steps.add(stepsItem);
		return this;
	}

	/**
	 * 
	 * 
	 * Get steps
	 * 
	 * 
	 * 
	 * @return steps
	 **/

	@ApiModelProperty(value = "")

	@Valid

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public Recipe ingredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
		return this;
	}

	public Recipe addIngredientsItem(Ingredient ingredientsItem) {

		if (this.ingredients == null) {
			this.ingredients = new ArrayList<>();
		}

		this.ingredients.add(ingredientsItem);
		return this;
	}

	/**
	 * 
	 * 
	 * Get ingredients
	 * 
	 * 
	 * 
	 * @return ingredients
	 **/

	@ApiModelProperty(value = "")

	@Valid

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Recipe user(String user) {
		this.user = user;
		return this;
	}

	/**
	 * 
	 * owner user name
	 * 
	 * 
	 * 
	 * 
	 * @return user
	 **/

	@ApiModelProperty(example = "ojodev", value = "owner user name")

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Recipe creationDate(DateTime creationDate) {
		this.creationDate = creationDate;
		return this;
	}

	/**
	 * 
	 * recipe creation date
	 * 
	 * 
	 * 
	 * 
	 * @return creationDate
	 **/

	@ApiModelProperty(value = "recipe creation date")

	@Valid

	public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Recipe recipe = (Recipe) o;
		return Objects.equals(this.id, recipe.id) && Objects.equals(this.name, recipe.name)
				&& Objects.equals(this.description, recipe.description)
				&& Objects.equals(this.cousineType, recipe.cousineType)
				&& Objects.equals(this.preparationTime, recipe.preparationTime)
				&& Objects.equals(this.cookingTime, recipe.cookingTime)
				&& Objects.equals(this.difficulty, recipe.difficulty) && Objects.equals(this.photo, recipe.photo)
				&& Objects.equals(this.steps, recipe.steps) && Objects.equals(this.ingredients, recipe.ingredients)
				&& Objects.equals(this.user, recipe.user) && Objects.equals(this.creationDate, recipe.creationDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, cousineType, preparationTime, cookingTime, difficulty, photo, steps,
				ingredients, user, creationDate);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Recipe {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    cousineType: ").append(toIndentedString(cousineType)).append("\n");
		sb.append("    preparationTime: ").append(toIndentedString(preparationTime)).append("\n");
		sb.append("    cookingTime: ").append(toIndentedString(cookingTime)).append("\n");
		sb.append("    difficulty: ").append(toIndentedString(difficulty)).append("\n");
		sb.append("    photo: ").append(toIndentedString(photo)).append("\n");
		sb.append("    steps: ").append(toIndentedString(steps)).append("\n");
		sb.append("    ingredients: ").append(toIndentedString(ingredients)).append("\n");
		sb.append("    user: ").append(toIndentedString(user)).append("\n");
		sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}