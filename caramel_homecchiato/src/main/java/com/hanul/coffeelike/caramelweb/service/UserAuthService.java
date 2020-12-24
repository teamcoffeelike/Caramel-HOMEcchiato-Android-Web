package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.UserAuthDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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

	@Nullable public Integer findUserWithAuthToken(UUID uuid){
		return dao.findUserWithAuthToken(uuid);
	}

	public void updateAuthToken(UUID uuid){
		dao.updateAuthToken(uuid);
	}
}
