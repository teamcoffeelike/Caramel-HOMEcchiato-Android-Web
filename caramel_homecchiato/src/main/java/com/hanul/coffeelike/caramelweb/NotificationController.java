package com.hanul.coffeelike.caramelweb;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;

@Controller
public class NotificationController {
	private final Gson GSON = new GsonBuilder().create();
	
	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex) {
		return JsonHelper.failure("bad_parameter");
	}
	
	//알림 설정
	@ResponseBody
	@RequestMapping("/setNotification")
	public String setNotification(
			HttpSession session
			// type : ( "reaction" | "like" | "follow" ) # 알림 종류
			// value : ( "on" : "off" )                  # 알림 설정값 (설정/해제)
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//최근활동
	@ResponseBody
	@RequestMapping("/notification")
	public String notification(
			HttpSession session
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//알림확인
	@ResponseBody
	@RequestMapping("/markNotificationAsRead")
	public String markNotificationAsRead(
			HttpSession session,
			@RequestParam int user
			//type : ( "reaction" | "like" | "follow" ) # 알림 타입
			//date : <Int64>???   # 알림 날짜, notifyDate 확인
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
}
