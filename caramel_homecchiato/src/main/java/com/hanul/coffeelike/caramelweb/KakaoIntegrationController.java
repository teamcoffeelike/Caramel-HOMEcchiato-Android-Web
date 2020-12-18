package com.hanul.coffeelike.caramelweb;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.service.JoinService;
import com.hanul.coffeelike.caramelweb.service.JoinService.JoinResult;
import com.hanul.coffeelike.caramelweb.service.JoinService.JoinSuccess;
import com.hanul.coffeelike.caramelweb.service.LoginService;
import com.hanul.coffeelike.caramelweb.service.LoginService.LoginResult;
import com.hanul.coffeelike.caramelweb.service.LoginService.LoginSuccess;
import com.hanul.coffeelike.caramelweb.util.HttpConnector;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class KakaoIntegrationController{
	@Autowired
	private LoginService loginService;
	@Autowired
	private JoinService joinService;

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
	 * bad_kakao_login_token     : 유효하지 않은 kakaoLoginToken 인자<br>
	 * kakao_service_unavailable : 카카오 플랫폼 서비스의 일시적 문제 등으로 인해 서비스 제공이 불가<br>
	 * needs_agreement           : 카카오 계정 정보를 전달받기 위해 동의가 필요
	 */
	@ResponseBody
	@RequestMapping("/loginWithKakao")
	public String loginWithKakao(
			HttpSession session,
			@RequestParam String kakaoLoginToken
	) throws IOException{
		HttpConnector.Response<JsonObject> response = HttpConnector.create("https://kapi.kakao.com/v1/user/access_token_info")
				.setRequestProperty("Content-type", "application/json")
				.setRequestProperty("Authorization", "Bearer "+kakaoLoginToken)
				.readAsJsonObject();

		if(!response.isSuccess()) return getResponseError(response);

		long kakaoUserId = response.getResponse().get("id").getAsLong();
		LoginResult loginResult = loginService.loginWithKakao(kakaoUserId);
		if(loginResult instanceof LoginSuccess){
			session.setAttribute(SessionAttributes.LOGIN_USER, ((LoginSuccess)loginResult).getUserId());
			return loginResult.toJson();
		}

		return join(session, kakaoLoginToken);
	}

	/**
	 * 카카오 계정 연동을 사용한 회원가입<br>
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
	 * bad_kakao_login_token     : 유효하지 않은 kakaoLoginToken 인자<br>
	 * kakao_service_unavailable : 카카오 플랫폼 서비스의 일시적 문제 등으로 인해 서비스 제공이 불가<br>
	 * user_exists               : 동일한 카카오 계정으로 회원가입한 유저가 이미 존재<br>
	 * needs_agreement           : 카카오 계정 정보를 전달받기 위해 동의가 필요
	 */
	@ResponseBody
	@RequestMapping("/joinWithKakao")
	public String joinWithKakao(
			HttpSession session,
			@RequestParam String kakaoLoginToken) throws IOException{
		HttpConnector.Response<JsonObject> response = HttpConnector.create("https://kapi.kakao.com/v1/user/access_token_info")
				.setRequestProperty("Content-type", "application/json")
				.setRequestProperty("Authorization", "Bearer "+kakaoLoginToken)
				.readAsJsonObject();

		if(!response.isSuccess()) return getResponseError(response);

		return join(session, kakaoLoginToken);
	}

	private String join(
			HttpSession session,
			String kakaoLoginToken) throws IOException{
		HttpConnector.Response<JsonObject> response = HttpConnector.create("https://kapi.kakao.com/v2/user/me")
				.setRequestProperty("Content-type", "application/json")
				.setRequestProperty("Authorization", "Bearer "+kakaoLoginToken)
				.setRequestProperty("property_keys", "[\"properties.nickname\"]")
				.readAsJsonObject();
		if(!response.isSuccess()) return getResponseError(response);

		// 닉네임 존재 여부 체크
		JsonElement kakaoAccount = response.getResponse().get("kakao_account");
		if(kakaoAccount==null) return JsonHelper.failure("needs_agreement"); // 닉네임 제공을 위한 동의 필요
		JsonElement profile = kakaoAccount.getAsJsonObject().get("profile");
		if(profile==null) return JsonHelper.failure("needs_agreement"); // 닉네임 제공을 위한 동의 필요
		JsonElement nickname = profile.getAsJsonObject().get("nickname");
		if(nickname==null) return JsonHelper.failure("needs_agreement"); // 닉네임 제공을 위한 동의 필요
		// 가입
		JoinResult joinResult = joinService.joinWithKakaoAccount(nickname.getAsString(), response.getResponse().get("id").getAsLong());
		if(joinResult instanceof JoinSuccess){
			session.setAttribute(SessionAttributes.LOGIN_USER, ((JoinSuccess)joinResult).getUserId());
		}
		return joinResult.toJson();
	}

	private static String getResponseError(HttpConnector.Response<JsonObject> response){
		if(response.isSuccess()) throw new IllegalArgumentException("Response not failed");

		int errorCode = response.getResponse().get("code").getAsInt();
		switch(errorCode){
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
