package com.hanul.coffeelike.caramelweb.dao;

import com.hanul.coffeelike.caramelweb.data.JoinData;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public class JoinDAO{
	@Autowired
	private SqlSession sql;

	public int createUserWithEmail(String name, String email, String password){
		return sql.insert("join.createUserWithEmailAndPassword",
				new JoinData(name,
						email,
						null,
						password,
						null));
	}
	public int createUserWithPhoneNumber(String name, String phoneNumber, String password){
		return sql.insert("join.createUserWithPhoneNumber",
				new JoinData(name,
						null,
						phoneNumber,
						password,
						null));
	}
	public int createUserWithKakaoAccount(@Nullable String name, long kakaoAccountId){
		return sql.insert("join.createUserWithKakaoAccount",
				new JoinData(name,
						null,
						null,
						null,
						kakaoAccountId));
	}
}
