package com.hanul.coffeelike.caramelweb.dao;

import com.hanul.coffeelike.caramelweb.data.RecipeCover;
import com.hanul.coffeelike.caramelweb.data.RecipeStep;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class RecipeDAO{
	@Autowired
	private SqlSession sql;

	public List<RecipeCover> list(String category, Integer author){
		Map<String, Object> m = new HashMap<>();
		m.put("category", category);
		m.put("author", author);
		return sql.selectList("recipe.list", m);
	}

	@Nullable public RecipeCover getCover(int id){
		return sql.selectOne("recipe.getCover", id);
	}

	public List<RecipeStep> steps(int id){
		List<Map<String, Object>> result = sql.selectOne("recipe.steps", id);
		// TODO debug code
		System.out.println(result.stream().map(m -> m.entrySet()
				.stream()
				.map(e -> e.getKey()+":"+e.getValue())
				.collect(Collectors.joining(", "))));
		return null; // TODO TODO
	}

	public boolean checkIfRecipeExists(int id){
		return sql.selectOne("recipe.checkIfRecipeExists", id)!=null;
	}
}
