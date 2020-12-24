package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.JoinDAO;
import com.hanul.coffeelike.caramelweb.dao.LoginDAO;
import com.hanul.coffeelike.caramelweb.data.LoginResult;
import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinService{
	@Autowired
	private JoinDAO joinDAO;
	@Autowired
	private LoginDAO loginDAO;
	@Autowired
	private UserAuthService authService;

	public LoginResult joinWithEmail(String name, String email, String password){
		if(joinDAO.createUserWithEmail(name, email, password)==0) return new LoginResult("user_exists");
		UserLoginData user = loginDAO.findUserWithEmail(email);
		if(user==null) return new LoginResult("join_failed");
		return new LoginResult(user.getId(), authService.generateAuthToken(user.getId()));
	}

	public LoginResult joinWithPhoneNumber(String name, String phoneNumber, String password){
		if(joinDAO.createUserWithPhoneNumber(name, phoneNumber, password)==0) return new LoginResult("user_exists");
		UserLoginData user = loginDAO.findUserWithPhoneNumber(phoneNumber);
		if(user==null) return new LoginResult("join_failed");
		return new LoginResult(user.getId(), authService.generateAuthToken(user.getId()));
	}

	public LoginResult joinWithKakaoAccount(String nickname, long kakaoUserId){
		if(joinDAO.createUserWithKakaoAccount(nickname, kakaoUserId)==0) return new LoginResult("user_exists");
		Integer user = loginDAO.findUserWithKakaoUserId(kakaoUserId);
		if(user==null) return new LoginResult("join_failed");
		return new LoginResult(user, authService.generateAuthToken(user));
	}
}
