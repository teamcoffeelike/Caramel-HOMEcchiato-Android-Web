package com.hanul.coffeelike.caramelweb.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.UserLoginData;

@Repository
public class LoginDAO {
	@Autowired
	private SqlSession sql;
	
	public UserLoginData findUserWithEmail(String email) {
		return sql.selectOne("login.findUserWithEmail", email);
	}

	public UserLoginData findUserWithPhoneNumber(String phoneNumber) {
		return sql.selectOne("login.findUserWithEmail", phoneNumber);
	}
}
