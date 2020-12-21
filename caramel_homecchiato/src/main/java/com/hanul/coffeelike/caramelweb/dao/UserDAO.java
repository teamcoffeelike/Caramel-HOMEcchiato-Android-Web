package com.hanul.coffeelike.caramelweb.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import com.hanul.coffeelike.caramelweb.data.UserSettingData;

@Repository
public class UserDAO {
	
	@Autowired private SqlSession sql;

	public UserSettingData userSettings(int loginUser) {
		return sql.selectOne("user.userSettings", loginUser);
	}
	
	public UserProfileData profile(int userId) {
		return sql.selectOne("user.selectProfile", userId);
	}
	
	public void setName(HashMap<String, Object> map) {
		sql.update("user.setName", map);
	}
	
	public String getUserPasswordById(int userId) {
		return sql.selectOne("user.getUserPasswordById", userId);
	}
	
	public void setPassword(HashMap<String, Object> map) {
		sql.update("user.setPassword", map);
	}

	public int follow(HashMap<String, Integer> map) {
		return sql.insert("user.follow", map);
	}

	public int unfollow(HashMap<String, Integer> map) {
		return sql.delete("user.unfollow", map);
	}
	
	
}
