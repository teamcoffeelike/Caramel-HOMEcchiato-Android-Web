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
public class JoinController {
	private final Gson GSON = new GsonBuilder().create();

	// success가 false인 결과는 모두 String 타입의 error 값을 가집니다.
	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex) {
		return JsonHelper.failure("bad_parameter");
	}

	// 이메일을 사용한 회원가입
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

	// 폰 번호를 사용한 회원가입
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

	// 카카오 계정 연동을 사용한 회원가입
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
