package com.hanul.coffeelike.caramelweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanul.coffeelike.caramelweb.dao.LoginDAO;
import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;

@Service
public class LoginService {
	@Autowired
	private LoginDAO dao;
	
	public LoginResult loginWithEmail(
			String email,
			String password
	) {
		UserLoginData user = dao.findUserWithEmail(email);
		if(user==null||!user.getPassword().equals(password)) {
			return LoginFailure.LOGIN_FAILED;
		}
		return new LoginSuccess(user.getId());
	}
	
	public LoginResult loginWithPhoneNumber(
			String phoneNumber,
			String password
	) {
		UserLoginData user = dao.findUserWithPhoneNumber(phoneNumber);
		if(user==null||!user.getPassword().equals(password)) {
			return LoginFailure.LOGIN_FAILED;
		}
		return new LoginSuccess(user.getId());
	}
	
	
	public interface LoginResult {
		boolean success();
		String toJson();
		
		default LoginSuccess asSuccess() {
			return (LoginSuccess)this;
		}
		default LoginFailure asFailure() {
			return (LoginFailure)this;
		}
	}
	
	public static class LoginSuccess implements LoginResult{
		private int userId;
		
		public LoginSuccess(int userId) {
			this.userId = userId;
		}

		@Override
		public boolean success() {
			return true;
		}
		
		public int getUserId() {
			return this.userId;
		}

		@Override
		public String toJson() {
			return JsonHelper.success()
					.with("userId", userId)
					.render();
		}
	}
	
	public enum LoginFailure implements LoginResult{
		LOGIN_FAILED;

		@Override
		public boolean success() {
			return false;
		}
		@Override
		public String toJson() {
			return JsonHelper.failure(name().toLowerCase());
		}
	}
}
