package com.hanul.coffeelike.caramelweb.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.data.FollowNotification;
import com.hanul.coffeelike.caramelweb.data.LikeNotification;
import com.hanul.coffeelike.caramelweb.data.ReactionNotification;

/**
 * 귀찮은 Gson 인스턴스를 거치지 않고 JSON 객체를 생성할 수 있도록 하는 유틸리티 클래스.
 */
public final class JsonHelper {
	public static final Gson GSON = new GsonBuilder()
			.registerTypeAdapter(FollowNotification.class, FollowNotification.Json.INSTANCE)
			.registerTypeAdapter(LikeNotification.class, LikeNotification.Json.INSTANCE)
			.registerTypeAdapter(ReactionNotification.class, ReactionNotification.Json.INSTANCE)
			.create();

	/**
	 * {@code success} 키 값으로 {@code false}를 가지며 {@code error} 키 값으로 전달받은 에러 메시지를 가지는 JSON 오브젝트를 텍스트 형태로 반환합니다.
	 * @param error 에러 메시지
	 * @return 텍스트 형태의 JSON
	 */
	public static String failure(String error) {
		JsonObject o = new JsonObject();
		o.addProperty("error", error);

		return GSON.toJson(o);
	}
	
	/**
	 * {@code success} 키 값으로 {@code true}를 가지는 {@code JsonHelper} 인스턴스를 생성하여 반환합니다.
	 * @return 새 {@code JsonHelper} 인스턴스
	 */
	public static JsonHelper create(){
		return new JsonHelper();
	}
	
	private final JsonObject jsonObject = new JsonObject();
	
	public JsonHelper with(String key, String value) {
		jsonObject.addProperty(key, value);
		return this;
	}
	
	public JsonHelper with(String key, boolean value) {
		jsonObject.addProperty(key, value);
		return this;
	}
	
	public JsonHelper with(String key, int value) {
		jsonObject.addProperty(key, value);
		return this;
	}
	
	public JsonHelper with(String key, double value) {
		jsonObject.addProperty(key, value);
		return this;
	}
	
	public JsonHelper with(String key, JsonElement jsonElement) {
		jsonObject.add(key, jsonElement);
		return this;
	}

	public JsonHelper with(String key, Object obj) {
		jsonObject.add(key, GSON.toJsonTree(obj));
		return this;
	}
	
	/**
	 * {@code JsonHelper}의 내용물을 텍스트 형태로 전환시킵니다.
	 * @return 텍스트 형태의 JSON
	 */
	public String render() {
		return GSON.toJson(jsonObject);
	}

	/**
	 * {@code JsonHelper}의 내용물을 {@code JsonObject}의 형태로 반환합니다.
	 */
	public JsonObject asObject(){
		return jsonObject;
	}
}
