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

@Controller
public class NotificationController {
	private final Gson GSON = new GsonBuilder().create();
	
	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "false");
		o.addProperty("error", "bad_parameter");
		
		return GSON.toJson(o);
	}
	
	//�˸� ����
	@ResponseBody
	@RequestMapping("/setNotification")
	public String setNotification(
			HttpSession session
			// type : ( "reaction" | "like" | "follow" ) # �˸� ����
			// value : ( "on" : "off" )                  # �˸� ������ (����/����)
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//�ֱ�Ȱ��
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
	
	//�˸�Ȯ��
	@ResponseBody
	@RequestMapping("/markNotificationAsRead")
	public String markNotificationAsRead(
			HttpSession session,
			@RequestParam int user
			//type : ( "reaction" | "like" | "follow" ) # �˸� Ÿ��
			//date : <Int64>???   # �˸� ��¥, notifyDate Ȯ��
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
}
