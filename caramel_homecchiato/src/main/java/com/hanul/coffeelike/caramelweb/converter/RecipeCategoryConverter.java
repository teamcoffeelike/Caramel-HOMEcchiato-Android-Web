package com.hanul.coffeelike.caramelweb.converter;

import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import org.springframework.core.convert.converter.Converter;

final class RecipeCategoryConverter implements Converter<String, RecipeCategory>{
	@Override
	public RecipeCategory convert(String source){
		return RecipeCategory.fromString(source);
	}
}
