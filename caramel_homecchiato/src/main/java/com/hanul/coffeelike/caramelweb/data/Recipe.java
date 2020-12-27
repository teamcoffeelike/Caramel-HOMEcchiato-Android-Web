package com.hanul.coffeelike.caramelweb.data;

import java.util.List;

public class Recipe {
	private RecipeCover cover;
	private List<RecipeStep> steps;

	public Recipe(RecipeCover cover, List<RecipeStep> steps){
		this.cover = cover;
		this.steps = steps;
	}

	public RecipeCover getCover(){
		return cover;
	}
	public void setCover(RecipeCover cover){
		this.cover = cover;
	}
	public List<RecipeStep> getSteps(){
		return steps;
	}
	public void setSteps(List<RecipeStep> steps){
		this.steps = steps;
	}
}
