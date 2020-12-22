package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.LoginDAO;
import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class LoginService{
	@Autowired
	private LoginDAO dao;

	public LoginResult loginWithEmail(String email, String password){
		UserLoginData user = dao.findUserWithEmail(email);
		if(user==null||!password.equals(user.getPassword())){
			return new LoginResult("login_failed");
		}
		return new LoginResult(user.getId());
	}

	public LoginResult loginWithPhoneNumber(String phoneNumber, String password){
		UserLoginData user = dao.findUserWithPhoneNumber(phoneNumber);
		if(user==null||!password.equals(user.getPassword())){
			return new LoginResult("login_failed");
		}
		return new LoginResult(user.getId());
	}

	public LoginResult loginWithKakao(long kakaoUserId){
		Integer userId = dao.findUserWithKakaoUserId(kakaoUserId);
		if(userId==null){
			return new LoginResult("login_failed");
		}
		return new LoginResult(userId);
	}


	public static class LoginResult{
		@Nullable private String error;
		@Nullable private Integer userId;

		public LoginResult(String error){
			this.error = error;
		}
		public LoginResult(Integer userId){
			this.userId = userId;
		}

		@Nullable public String getError(){
			return error;
		}
		public void setError(@Nullable String error){
			this.error = error;
		}
		@Nullable public Integer getUserId(){
			return userId;
		}
		public void setUserId(@Nullable Integer userId){
			this.userId = userId;
		}
	}
}
