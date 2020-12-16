package com.hanul.coffeelike.caramelweb.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.UserSettingData;

@Repository
public class UserDAO {
	
	@Autowired private SqlSession sql;

	public UserSettingData userSettings(int id) {
		return sql.selectOne("user.userSettings", id);
	}
	
	
	
}
