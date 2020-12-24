package com.hanul.coffeelike.caramelweb.dao;

import com.hanul.coffeelike.caramelweb.data.AuthToken;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserAuthDAO{
	@Autowired
	private SqlSession sql;

	@Nullable public AuthToken getAuthTokenInformation(UUID uuid){
		return sql.selectOne("auth.getAuthTokenInformation", uuid);
	}

	public void addAuthToken(UUID uuid, int userId){
		sql.insert("auth.addAuthToken", new AuthToken(uuid, userId, true));
	}

	public void removeAuthToken(UUID uuid){
		sql.delete("auth.removeAuthToken", uuid);
	}

	public void updateAuthToken(UUID uuid){
		sql.update("auth.updateAuthToken", uuid);
	}

	public int removeStaleAuthTokens(){
		return sql.delete("auth.removeStaleAuthTokens");
	}
}
