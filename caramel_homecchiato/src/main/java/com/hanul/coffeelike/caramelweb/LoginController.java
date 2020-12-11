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
	
	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex) {
		return JsonHelper.failure("bad_parameter");
	}
	
	/**
	 * 이메일을 사용한 로그인<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre><code> {
	 *   userId: Integer
	 * }</code></pre>
	 * 
	 * <b>에러: </b><br>
	 * bad_email    : 유효하지 않은 email 인자<br>
	 * bad_password : 유효하지 않은 password 인자<br>
	 * login_failed : 로그인 실패
	 */
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

	/**
	 * 폰 사용한 로그인<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre><code> {
	 *   userId: Integer
	 * }</code></pre>
	 * 
	 * <b>에러: </b><br>
	 * bad_phone_number : 유효하지 않은 phoneNumber 인자<br>
	 * bad_password     : 유효하지 않은 password 인자<br>
	 * login_failed     : 로그인 실패
	 */
	@ResponseBody
	@RequestMapping("/loginWithPhoneNumber")
	public String loginWithPhoneNumber(
			HttpSession session,
			@RequestParam String phoneNumber,
			@RequestParam String password
	) {
		LoginResult result = loginService.loginWithPhoneNumber(phoneNumber, password);
		if(result.success()) {
			session.setAttribute("loginUser", result.asSuccess().getUserId());
		}
		return result.toJson();
	}
	
	//카카오 계정 연동을 사용한 로그인
	@ResponseBody
	@RequestMapping("/loginWithKakao")
	public String loginWithKakao(
			HttpSession session,
			@RequestParam String kakaoLoginToken
	) {
		// TODO
		
	}
	
}
