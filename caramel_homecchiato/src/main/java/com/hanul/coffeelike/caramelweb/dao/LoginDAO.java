package com.hanul.coffeelike.caramelweb.dao;

import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDAO{
	@Autowired
	private SqlSession sql;

	@Nullable public UserLoginData findUserWithEmail(String email){
		return sql.selectOne("login.findUserWithEmail", email);
	}
	@Nullable public UserLoginData findUserWithPhoneNumber(String phoneNumber){
		return sql.selectOne("login.findUserWithEmail", phoneNumber);
	}
	@Nullable public Integer findUserWithKakaoUserId(long kakaoUserId){
		return sql.selectOne("login.findUserWithKakaoUserId", kakaoUserId);
	}
}
