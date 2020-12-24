package com.hanul.coffeelike.caramelweb.dao;

import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.UserSettingData;

import java.util.List;

@Repository
public class UserDAO {
	
	@Autowired private SqlSession sql;

	public UserSettingData userSettings(int id) {
		return sql.selectOne("user.userSettings", id);
	}

	public List<UserProfileData> getFollower(int loginUser){
		return sql.selectList("user.getFollower", loginUser);
	}
}
