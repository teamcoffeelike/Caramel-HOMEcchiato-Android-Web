package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.UserAuthDAO;
import com.hanul.coffeelike.caramelweb.data.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserAuthService{
	@Autowired
	private UserAuthDAO dao;

	public UUID generateAuthToken(int userId){
		UUID uuid = UUID.randomUUID();
		dao.addAuthToken(uuid, userId);
		return uuid;
	}

	public void removeAuthToken(UUID authToken){
		dao.removeAuthToken(authToken);
	}

	@Nullable public AuthToken getAuthTokenInformation(UUID uuid){
		return dao.getAuthTokenInformation(uuid);
	}

	public void updateAuthToken(UUID uuid){
		dao.updateAuthToken(uuid);
	}


	/**
	 * 하루에 한 번 실행. 마지막 접속 이후 2달이 지난 로그인 토큰을 데이터베이스에서 삭제.
	 */
	@Scheduled(cron = "0 0 0 * * ? *")
	public void removeStaleAuthTokens(){
		System.out.println("removeStaleAuthTokens");
		int removed = dao.removeStaleAuthTokens();
		if(removed>0){
			System.out.println("Removed "+removed+" auth tokens.");
		}
	}
}
