package com.hanul.coffeelike.caramelweb;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class JoinController {
	private final Gson GSON = new GsonBuilder().create();

	// success�� false�� ����� ��� String Ÿ���� error ���� �����ϴ�.
	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "false");
		o.addProperty("error", "bad_parameter");

		return GSON.toJson(o);
	}

	// �̸����� ����� ȸ������
	@ResponseBody
	@RequestMapping("/joinWithEmail")
	public String joinWithEmail(
			HttpSession session, 
			@RequestParam String email, 
			@RequestParam String password) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);

		return GSON.toJson(o);
	}

	// �� ��ȣ�� ����� ȸ������
	@ResponseBody
	@RequestMapping("/joinWithPhoneNumber")
	public String joinWithPhoneNumber(
			HttpSession session, 
			@RequestParam String phoneNumber,
			@RequestParam String password) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);

		return GSON.toJson(o);
	}

	// īī�� ���� ������ ����� ȸ������
	@ResponseBody
	@RequestMapping("/joinWithKakao")
	public String joinWithKakao(
			HttpSession session,
			@RequestParam String kakaoLoginToken) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("token", 1231231323);

		return GSON.toJson(o);
	}

}
