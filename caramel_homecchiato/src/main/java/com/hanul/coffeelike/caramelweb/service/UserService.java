package com.hanul.coffeelike.caramelweb.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.hanul.coffeelike.caramelweb.dao.UserDAO;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import com.hanul.coffeelike.caramelweb.data.UserSettingData;

@Service
public class UserService {

	@Autowired private UserDAO dao;

	public UserSettingData userSettings(int loginUser) {
		return dao.userSettings(loginUser);
	}
	
	public UserProfileData profile(int userId) {
		return dao.profile(userId);
	}
	
	public void setName(HashMap<String, Object> map) {
		dao.setName(map);
	}
	
	public SetPasswordResult setPassword(int userId, String password, String newPassword) {
		
		String initPw = dao.getUserPasswordById(userId);
		if(initPw == null) {
			return new SetPasswordResult("no_password");
		}
		if(!initPw.equals(password)) {
			return new SetPasswordResult("incorrect_password");
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("newPassword", newPassword);
		
		dao.setPassword(map);
		
		return new SetPasswordResult(null);
	}

	public boolean setFollowing(HashMap<String, Integer> map, boolean following) {
		int result = following ? dao.follow(map) :  dao.unfollow(map);
		// result 체크
		return result != 0;
	}
	
	
	public static class SetPasswordResult {
		@Nullable private String error;
		
		public SetPasswordResult(@Nullable String error) {
			this.error = error;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}
	}
}
