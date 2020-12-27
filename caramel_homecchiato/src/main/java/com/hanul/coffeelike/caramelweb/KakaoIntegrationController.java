package com.hanul.coffeelike.caramelweb;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.data.LoginResult;
import com.hanul.coffeelike.caramelweb.service.JoinService;
import com.hanul.coffeelike.caramelweb.service.LoginService;
import com.hanul.coffeelike.caramelweb.util.HttpConnector;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class KakaoIntegrationController{
	@Autowired
	private LoginService loginService;
	@Autowired
	private JoinService joinService;

	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex){
		return JsonHelper.failure("bad_parameter");
	}

	/**
	 * 카카오 계정 연동을 사용한 로그인<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   userId: Integer
	 *   authToken: UUID
	 * }
	 * }</pre>
	 * <b>에러: </b><br>
	 * bad_kakao_login_token     : 유효하지 않은 kakaoLoginToken 인자<br>
	 * kakao_service_unavailable : 카카오 플랫폼 서비스의 일시적 문제 등으로 인해 서비스 제공이 불가<br>
	 * needs_agreement           : 카카오 계정 정보를 전달받기 위해 동의가 필요
	 */
	@RequestMapping("/loginWithKakao")
	public String loginWithKakao(HttpSession session,
	                             @RequestParam String kakaoLoginToken) throws IOException{
		HttpConnector.Response<JsonObject> response = HttpConnector.create("https://kapi.kakao.com/v1/user/access_token_info")
				.setRequestProperty("Content-type", "application/json")
				.setRequestProperty("Authorization", "Bearer "+kakaoLoginToken)
				.readAsJsonObject();

		if(!response.isSuccess()) return getResponseError(response);

		long kakaoUserId = response.getResponse().get("id").getAsLong();
		LoginResult result = loginService.loginWithKakao(kakaoUserId);
		if(result.getUserId()!=null){
			session.setAttribute(SessionAttributes.LOGIN_USER, result.getUserId());
			return JsonHelper.GSON.toJson(result);
		}

		return join(session, kakaoLoginToken, null);
	}

	/**
	 * 카카오 계정 연동을 사용한 회원가입<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   userId: Integer
	 *   authToken: UUID
	 * }
	 * }</pre>
	 * <b>에러: </b><br>
	 * bad_kakao_login_token     : 유효하지 않은 kakaoLoginToken 인자<br>
	 * kakao_service_unavailable : 카카오 플랫폼 서비스의 일시적 문제 등으로 인해 서비스 제공이 불가<br>
	 * user_exists               : 동일한 카카오 계정으로 회원가입한 유저가 이미 존재<br>
	 * needs_agreement           : 카카오 계정 정보를 전달받기 위해 동의가 필요
	 */
	@RequestMapping("/joinWithKakao")
	public String joinWithKakao(HttpSession session,
	                            @RequestParam String kakaoLoginToken,
	                            @RequestParam(required = false) @Nullable String name) throws IOException{
		HttpConnector.Response<JsonObject> response = HttpConnector.create("https://kapi.kakao.com/v1/user/access_token_info")
				.setRequestProperty("Content-type", "application/json")
				.setRequestProperty("Authorization", "Bearer "+kakaoLoginToken)
				.readAsJsonObject();

		if(!response.isSuccess()) return getResponseError(response);

		return join(session, kakaoLoginToken, name);
	}

	private String join(HttpSession session,
	                    String kakaoLoginToken,
	                    @Nullable String name) throws IOException{
		HttpConnector.Response<JsonObject> response = HttpConnector.create("https://kapi.kakao.com/v2/user/me")
				.setRequestProperty("Content-type", "application/json")
				.setRequestProperty("Authorization", "Bearer "+kakaoLoginToken)
				.setRequestProperty("property_keys", "[\"properties.nickname\"]")
				.readAsJsonObject();
		if(!response.isSuccess()) return getResponseError(response);

		if(name==null){
			name = getName(response);
			if(name==null) return JsonHelper.failure("needs_agreement");
		}

		// 가입
		LoginResult result = joinService.joinWithKakaoAccount(name, response.getResponse().get("id").getAsLong());
		if(result.getError()==null){
			SessionAttributes.setLoginUser(session, result.createAuthToken());
		}
		return JsonHelper.GSON.toJson(result);
	}

	@Nullable private String getName(HttpConnector.Response<JsonObject> response){
		JsonElement kakaoAccount = response.getResponse().get("kakao_account");
		if(kakaoAccount==null) return null;
		JsonElement profile = kakaoAccount.getAsJsonObject().get("profile");
		if(profile==null) return null;
		JsonElement nickname = profile.getAsJsonObject().get("nickname");
		if(nickname==null) return null;

		return nickname.getAsString();
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
