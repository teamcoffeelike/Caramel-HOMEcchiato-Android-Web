package com.hanul.coffeelike.caramelweb.data;

import com.google.gson.*;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;

public enum RecipeCategory{
	HOT_COFFEE,
	ICE_COFFEE,
	TEA,
	ADE,
	SMOOTHIE,
	ETC;

	@Override public String toString(){
		return name().toLowerCase();
	}

	@Nullable public static RecipeCategory fromString(String string){
		try{
			return valueOf(string.toUpperCase());
		}catch(IllegalArgumentException ex){
			return null;
		}
	}

	public enum Json implements JsonSerializer<RecipeCategory>,
			JsonDeserializer<RecipeCategory>{
		INSTANCE;

		@Override public JsonElement serialize(RecipeCategory recipeCategory,
		                                       Type type,
		                                       JsonSerializationContext jsonSerializationContext){
			return new JsonPrimitive(recipeCategory.toString());
		}

		@Override public RecipeCategory deserialize(JsonElement jsonElement,
		                                            Type type,
		                                            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException{
			return fromString(jsonElement.getAsString());
		}
	}
}
