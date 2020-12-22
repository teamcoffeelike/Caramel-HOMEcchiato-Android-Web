package com.hanul.coffeelike.caramelweb.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.UserLoginData;

@Repository
public class LoginDAO {
	@Autowired
	private SqlSession sql;
	
	@Nullable public UserLoginData findUserWithEmail(String email) {
		return sql.selectOne("login.findUserWithEmail", email);
	}

	@Nullable public UserLoginData findUserWithPhoneNumber(String phoneNumber) {
		return sql.selectOne("login.findUserWithPhoneNumber", phoneNumber);
	}

	@Nullable public Integer findUserWithKakaoUserId(long kakaoUserId) {
		return sql.selectOne("login.findUserWithKakaoUserId", kakaoUserId);
	}
}
