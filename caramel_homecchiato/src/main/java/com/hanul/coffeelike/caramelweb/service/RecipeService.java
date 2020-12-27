package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.RecipeDAO;
import com.hanul.coffeelike.caramelweb.data.Recipe;
import com.hanul.coffeelike.caramelweb.data.RecipeCover;
import com.hanul.coffeelike.caramelweb.data.RecipeStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService{
	@Autowired
	private RecipeDAO recipeDAO;

	public List<RecipeCover> list(@Nullable String category,
	                              @Nullable Integer author){
		return recipeDAO.list(category, author);
	}

	@Nullable public Recipe recipe(int id){
		RecipeCover cover = recipeDAO.getCover(id);
		if(cover==null) return null;
		List<RecipeStep> steps = recipeDAO.steps(id);
		return new Recipe(cover, steps);
	}

	public RecipeCover getCover(int id){
		return recipeDAO.getCover(id);
	}

	public boolean checkIfRecipeExists(int id){
		return recipeDAO.checkIfRecipeExists(id);
	}

	public void delete(int recipe){
		// TODO
	}
}
