package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.JoinDAO;
import com.hanul.coffeelike.caramelweb.dao.LoginDAO;
import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinService{
	@Autowired
	private JoinDAO joinDAO;
	@Autowired
	private LoginDAO loginDAO;

	public JoinResult joinWithEmail(String name, String email, String password){
		if(joinDAO.createUserWithEmail(name, email, password)==0) return JoinFailure.USER_EXISTS;
		UserLoginData user = loginDAO.findUserWithEmail(email);
		if(user==null) return JoinFailure.JOIN_FAILED;
		return new JoinSuccess(user.getId());
	}

	public JoinResult joinWithPhoneNumber(String name, String phoneNumber, String password){
		if(joinDAO.createUserWithPhoneNumber(name, phoneNumber, password)==0) return JoinFailure.USER_EXISTS;
		UserLoginData user = loginDAO.findUserWithPhoneNumber(phoneNumber);
		if(user==null) return JoinFailure.JOIN_FAILED;
		return new JoinSuccess(user.getId());
	}

	public JoinResult joinWithKakaoAccount(String nickname, long kakaoUserId){
		if(joinDAO.createUserWithKakaoAccount(nickname, kakaoUserId)==0) return JoinFailure.USER_EXISTS;
		Integer user = loginDAO.findUserWithKakaoUserId(kakaoUserId);
		if(user==null) return JoinFailure.JOIN_FAILED;
		return new JoinSuccess(user);
	}


	public interface JoinResult{
		String toJson();
	}

	public static class JoinSuccess implements JoinResult{
		private final int userId;

		public JoinSuccess(int userId){
			this.userId = userId;
		}

		public int getUserId(){
			return userId;
		}

		@Override public String toJson(){
			return JsonHelper.success().with("userId", userId).render();
		}
	}

	public enum JoinFailure implements JoinResult{
		USER_EXISTS,
		JOIN_FAILED;

		@Override public String toJson(){
			return JsonHelper.failure(name().toLowerCase());
		}
	}
}
