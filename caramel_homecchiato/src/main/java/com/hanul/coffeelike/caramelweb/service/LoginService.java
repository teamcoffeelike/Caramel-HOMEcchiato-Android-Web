package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.LoginDAO;
import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	@Autowired
	private LoginDAO dao;

	public LoginResult loginWithEmail(String email, String password) {
		UserLoginData user = dao.findUserWithEmail(email);
		if(user==null||!password.equals(user.getPassword())) {
			return LoginFailure.LOGIN_FAILED;
		}
		return new LoginSuccess(user.getId());
	}

	public LoginResult loginWithPhoneNumber(String phoneNumber, String password) {
		UserLoginData user = dao.findUserWithPhoneNumber(phoneNumber);
		if(user==null||!password.equals(user.getPassword())) {
			return LoginFailure.LOGIN_FAILED;
		}
		return new LoginSuccess(user.getId());
	}

	public LoginResult loginWithKakao(long kakaoUserId) {
		Integer userId = dao.findUserWithKakaoUserId(kakaoUserId);
		if(userId==null) {
			return LoginFailure.LOGIN_FAILED;
		}
		return new LoginSuccess(userId);
	}


	public interface LoginResult {
		String toJson();
	}

	public static class LoginSuccess implements LoginResult{
		private final int userId;

		public LoginSuccess(int userId) {
			this.userId = userId;
		}

		public int getUserId() {
			return this.userId;
		}

		@Override public String toJson() {
			return JsonHelper.success()
					.with("userId", userId)
					.render();
		}
	}

	public enum LoginFailure implements LoginResult{
		LOGIN_FAILED;

		@Override public String toJson() {
			return JsonHelper.failure(name().toLowerCase());
		}
	}
}
