package com.hanul.coffeelike.caramelweb;

import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.LoginResult;
import com.hanul.coffeelike.caramelweb.service.LoginService;
import com.hanul.coffeelike.caramelweb.service.UserAuthService;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class LoginController{
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserAuthService authService;

	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex){
		return JsonHelper.failure("bad_parameter");
	}

	/**
	 * 이메일을 사용한 로그인<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   userId: Integer
	 *   authToken: UUID
	 * }
	 * }</pre>
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
	                             @RequestParam String password){
		LoginResult result = loginService.loginWithEmail(email, password);
		if(result.getError()==null){
			SessionAttributes.setLoginUser(session, result.createAuthToken());
		}
		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 폰 사용한 로그인<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * {
	 *   userId: Integer
	 *   authToken: UUID
	 * }
	 * }</pre>
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
	                                   @RequestParam String password){
		LoginResult result = loginService.loginWithPhoneNumber(phoneNumber, password);
		if(result.getError()==null){
			SessionAttributes.setLoginUser(session, result.createAuthToken());
		}
		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 로그아웃<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		SessionAttributes.setLoginUser(session, null);
		authService.removeAuthToken(loginUser.getAuthToken());
		return "{}";
	}

	/**
	 * 인증 토큰을 사용한 로그인<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   userId: Integer
	 *   authToken: UUID
	 * }
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * bad_auth_token : 유효하지 않은 authToken 인자<br>
	 * login_failed : 로그인 실패
	 */
	@ResponseBody
	@RequestMapping("/loginWithAuthToken")
	public String loginWithAuthToken(HttpSession session,
	                                 @RequestParam String authToken){
		UUID uuid;
		try{
			uuid = UUID.fromString(authToken);
		}catch(IllegalArgumentException ex){
			return JsonHelper.failure("bad_auth_token");
		}
		LoginResult result = loginService.loginWithAuthToken(uuid);
		if(result.getError()==null){
			SessionAttributes.setLoginUser(session, result.createAuthToken());
		}
		return JsonHelper.GSON.toJson(result);
	}
}
