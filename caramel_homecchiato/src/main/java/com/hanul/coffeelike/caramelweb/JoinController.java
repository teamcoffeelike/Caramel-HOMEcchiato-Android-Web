package com.hanul.coffeelike.caramelweb;

import com.hanul.coffeelike.caramelweb.service.JoinService;
import com.hanul.coffeelike.caramelweb.service.JoinService.JoinResult;
import com.hanul.coffeelike.caramelweb.service.JoinService.JoinSuccess;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import static com.hanul.coffeelike.caramelweb.util.SessionAttributes.LOGIN_USER;

@Controller
public class JoinController{
	@Autowired
	private JoinService joinService;

	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex){
		return JsonHelper.failure("bad_parameter");
	}

	/**
	 * 이메일을 사용한 회원가입<br>
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
	 * bad_name     : 유효하지 않은 이름<br>
	 * bad_email    : 유효하지 않은 이메일<br>
	 * bad_password : 유효하지 않은 비밀번호<br>
	 * user_exists  : 동일한 이메일을 가진 유저가 이미 존재
	 */
	@ResponseBody
	@RequestMapping("/joinWithEmail")
	public String joinWithEmail(
			HttpSession session,
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String password){
		if(!Validate.name(name)) return JsonHelper.failure("bad_email");
		if(!Validate.email(email)) return JsonHelper.failure("bad_email");
		if(!Validate.password(password)) return JsonHelper.failure("bad_password");

		JoinResult result = joinService.joinWithEmail(name, email, password);
		if(result instanceof JoinSuccess){
			//session.setAttribute(LOGIN_USER, result.asSuccess().getUserId());
			session.setAttribute(LOGIN_USER, ((JoinSuccess)result).getUserId());
		}
		return result.toJson();
	}

	/**
	 * 휴대폰 전화번호를 사용한 회원가입<br>
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
	 * bad_name         : 유효하지 않은 이름<br>
	 * bad_phone_number : 유효하지 않은 휴대폰 전화번호<br>
	 * bad_password     : 유효하지 않은 비밀번호<br>
	 * user_exists      : 동일한 이메일을 가진 유저가 이미 존재
	 */
	@ResponseBody
	@RequestMapping("/joinWithPhoneNumber")
	public String joinWithPhoneNumber(
			HttpSession session,
			@RequestParam String name,
			@RequestParam String phoneNumber,
			@RequestParam String password){
		if(!Validate.name(name)) return JsonHelper.failure("bad_email");
		if(!Validate.phoneNumber(phoneNumber)) return JsonHelper.failure("bad_email");
		if(!Validate.password(password)) return JsonHelper.failure("bad_password");

		JoinResult result = joinService.joinWithPhoneNumber(name, phoneNumber, password);
		if(result instanceof JoinSuccess){
			session.setAttribute(LOGIN_USER, ((JoinSuccess)result).getUserId());
		}
		return result.toJson();
	}
}
