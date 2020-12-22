package com.hanul.coffeelike.caramelweb;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.service.LoginService;
import com.hanul.coffeelike.caramelweb.service.LoginService.LoginResult;
import com.hanul.coffeelike.caramelweb.util.HttpConnectionHelper;
import com.hanul.coffeelike.caramelweb.util.HttpConnectionHelper.Response;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;

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
	 *
	 * <pre>
	 * <code> {
	 *   userId: Integer
	 * }</code>
	 * </pre>
	 *
	 * <b>에러: </b><br>
	 * bad_email : 유효하지 않은 email 인자<br>
	 * bad_password : 유효하지 않은 password 인자<br>
	 * login_failed : 로그인 실패
	 */
	@ResponseBody
	@RequestMapping("/loginWithEmail")
	public String loginWithEmail(HttpSession session,
			@RequestParam String email,
			@RequestParam String password) {
		LoginResult result = loginService.loginWithEmail(email, password);
		if (result.getUserId()!=null) {
			session.setAttribute(SessionAttributes.LOGIN_USER, result.getUserId());
		}
		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 폰 사용한 로그인<br>
	 * <br>
	 * <b>성공 시:</b>
	 *
	 * <pre>
	 * <code> {
	 *   userId: Integer
	 * }</code>
	 * </pre>
	 *
	 * <b>에러: </b><br>
	 * bad_phone_number : 유효하지 않은 phoneNumber 인자<br>
	 * bad_password : 유효하지 않은 password 인자<br>
	 * login_failed : 로그인 실패
	 */
	@ResponseBody
	@RequestMapping("/loginWithPhoneNumber")
	public String loginWithPhoneNumber(HttpSession session,
			@RequestParam String phoneNumber,
			@RequestParam String password) {
		LoginResult result = loginService.loginWithPhoneNumber(phoneNumber, password);
		if (result.getUserId()!=null) {
			session.setAttribute(SessionAttributes.LOGIN_USER, result.getUserId());
		}
		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 로그아웃<br>
	 * <br>
	 * <b>성공 시:</b>
	 *
	 * <pre>추가 데이터 없음
	 * </pre>
	 *
	 * <b>에러: </b><br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		Integer loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		session.removeAttribute(SessionAttributes.LOGIN_USER);
		return "{}";
	}

	/**
	 * 카카오 계정 연동을 사용한 로그인<br>
	 * <br>
	 * <b>성공 시:</b>
	 *
	 * <pre>
	 * <code> {
	 *   userId: Integer
	 * }</code>
	 * </pre>
	 *
	 * <b>에러: </b><br>
	 * bad_kakao_login_token : 유효하지 않은 kakaoLoginToken 인자<br>
	 * kakao_service_unavailable : 카카오 플랫폼 서비스의 일시적 문제 등으로 인해 서비스 제공이 불가<br>
	 */
	@ResponseBody
	@RequestMapping("/loginWithKakao")
	public String loginWithKakao(
			HttpSession session,
			@RequestParam String kakaoLoginToken
	) throws IOException {
		Response<JsonObject> response = HttpConnectionHelper.create("https://kapi.kakao.com/v1/user/access_token_info")
				.setRequestMethod("GET")
				.setRequestProperty("Content-type", "application/json")
				.setRequestProperty("Authorization", "Bearer "+kakaoLoginToken)
				.readAsJsonObject();
		if(response.isSuccess()) {
			long kakaoUserId = response.getResponse().get("id").getAsLong();
			LoginResult result = loginService.loginWithKakao(kakaoUserId);
			if(result.getUserId()!=null) {
				session.setAttribute(SessionAttributes.LOGIN_USER, result.getUserId());
				return JsonHelper.GSON.toJson(result);
			}

			// TODO 새 유저로 회원가입
			return JsonHelper.failure("unknown");
		}else{
			int errorCode = response.getResponse().get("code").getAsInt();
			switch(errorCode) {
			case -1: // 카카오 사망
				return JsonHelper.failure("kakao_service_unavailable");
			case -2: // 몬가이상함
			case -401: // 만료됨
				return JsonHelper.failure("bad_kakao_login_token");
			default:
				// TODO 카카오 로그아웃?
				return JsonHelper.failure("unknown");
			}
		}
	}
}
