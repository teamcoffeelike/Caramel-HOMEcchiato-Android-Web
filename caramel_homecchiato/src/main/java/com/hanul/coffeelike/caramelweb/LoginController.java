package com.hanul.coffeelike.caramelweb;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.service.LoginService;
import com.hanul.coffeelike.caramelweb.service.LoginService.LoginResult;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	//success가 false인 결과는 모두 String 타입의 error 값을 가집니다.
	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex) {
		return JsonHelper.failure("bad_parameter");
	}
	
	//이메일을 사용한 로그인
	@ResponseBody
	@RequestMapping("/loginWithEmail")
	public String loginWithEmail(
			HttpSession session,
			@RequestParam String email,
			@RequestParam String password
	) {
		LoginResult result = loginService.loginWithEmail(email, password);
		if(result.success()) {
			session.setAttribute("loginUser", result.asSuccess().getUserId());
		}
		return result.toJson();
	}
	
	//폰 번호를 사용한 로그인
	@ResponseBody
	@RequestMapping("/loginWithPhoneNumber")
	public String loginWithPhoneNumber(
			HttpSession session,
			@RequestParam String phoneNumber,
			@RequestParam String password
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//카카오 계정 연동을 사용한 로그인
	@ResponseBody
	@RequestMapping("/loginWithKakao")
	public String loginWithKakao(
			HttpSession session,
			@RequestParam String kakaoLoginToken
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("token", 1231231323);
		
		return GSON.toJson(o);
	}
	
}
