package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.LoginDAO;
import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.LoginResult;
import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService{
	@Autowired
	private LoginDAO dao;
	@Autowired
	private UserAuthService authService;

	public LoginResult loginWithEmail(String email, String password){
		UserLoginData user = dao.findUserWithEmail(email);
		if(user==null||!password.equals(user.getPassword())){
			return new LoginResult("login_failed");
		}

		return new LoginResult(user.getId(), authService.generateAuthToken(user.getId()));
	}

	public LoginResult loginWithPhoneNumber(String phoneNumber, String password){
		UserLoginData user = dao.findUserWithPhoneNumber(phoneNumber);
		if(user==null||!password.equals(user.getPassword())){
			return new LoginResult("login_failed");
		}
		return new LoginResult(user.getId(), authService.generateAuthToken(user.getId()));
	}

	public LoginResult loginWithKakao(long kakaoUserId){
		Integer userId = dao.findUserWithKakaoUserId(kakaoUserId);
		if(userId==null){
			return new LoginResult("login_failed");
		}
		return new LoginResult(userId, authService.generateAuthToken(userId));
	}

	public LoginResult loginWithAuthToken(UUID uuid){
		AuthToken token = authService.getAuthTokenInformation(uuid);
		if(token==null){
			return new LoginResult("login_failed");
		}
		authService.updateAuthToken(uuid);
		return new LoginResult(token.getUserId(), uuid);
	}
}
