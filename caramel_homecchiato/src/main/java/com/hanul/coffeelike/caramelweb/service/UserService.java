package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanul.coffeelike.caramelweb.dao.UserDAO;
import com.hanul.coffeelike.caramelweb.data.UserSettingData;

import java.util.List;

@Service
public class UserService {

	@Autowired private UserDAO dao;

	public UserSettingData userSettings(int id) {
		return dao.userSettings(id);
	}

	public List<UserProfileData> getFollower(int loginUser){
		return dao.getFollower(loginUser);
	}
}
