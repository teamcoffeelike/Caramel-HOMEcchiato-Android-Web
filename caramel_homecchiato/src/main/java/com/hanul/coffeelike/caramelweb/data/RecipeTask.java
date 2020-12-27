package com.hanul.coffeelike.caramelweb.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public abstract class RecipeTask{
	public abstract String getTask();

	public static class Timer extends RecipeTask{
		private int seconds;
		private Purpose purpose;

		public Timer(int seconds, Purpose purpose){
			this.seconds = seconds;
			this.purpose = purpose;
		}

		@Override public String getTask(){
			return "timer";
		}

		public int getSeconds(){
			return seconds;
		}
		public void setSeconds(int seconds){
			this.seconds = seconds;
		}
		public Purpose getPurpose(){
			return purpose;
		}
		public void setPurpose(Purpose purpose){
			this.purpose = purpose;
		}

		public enum Purpose{
			COOK,
			WAIT;

			@Override public String toString(){
				return name().toLowerCase();
			}
		}
	}

	public enum Json implements JsonSerializer<RecipeTask>{
		INSTANCE;

		@Override
		public JsonElement serialize(RecipeTask src,
		                             Type typeOfSrc,
		                             JsonSerializationContext context){
			// TODO
			return null;
		}
	}
}
