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
public class UserController {
	private final Gson GSON = new GsonBuilder().create();
	
	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "false");
		o.addProperty("error", "bad_parameter");
		
		return GSON.toJson(o);
	}
	
	//������ ������ ��û
	@ResponseBody
	@RequestMapping("/profile")
	public String profile(
			HttpSession session,
			@RequestParam int userId
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//���� ����
	@ResponseBody
	@RequestMapping("/userSettings")
	public String userSettings(
			HttpSession session
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//�г��� ����
	@ResponseBody
	@RequestMapping("/setName")
	public String setName(
			HttpSession session,
			@RequestParam String name
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//��й�ȣ �缳��
	@ResponseBody
	@RequestMapping("/setPassword")
	public String setPassword(
			HttpSession session,
			@RequestParam String password,
			@RequestParam String newPassword
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
}
