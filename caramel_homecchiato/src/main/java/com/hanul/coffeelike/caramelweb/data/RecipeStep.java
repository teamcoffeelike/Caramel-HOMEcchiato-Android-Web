package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class RecipeStep{
	private int index;
	@Nullable private String image;
	private String text;
	@Nullable private RecipeTask task;
}
