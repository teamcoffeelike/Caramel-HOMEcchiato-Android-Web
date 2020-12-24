package com.hanul.coffeelike.caramelweb.data;

import java.util.Objects;
import java.util.UUID;

public class AuthToken{
	private UUID authToken;
	private int userId;
	private boolean isValid;

	public AuthToken(UUID authToken, int userId){
		this(authToken, userId, true);
	}
	public AuthToken(UUID authToken, int userId, boolean isValid){
		this.authToken = Objects.requireNonNull(authToken);
		this.userId = userId;
		this.isValid = isValid;
	}

	public UUID getAuthToken(){
		return authToken;
	}
	public void setAuthToken(UUID authToken){
		this.authToken = authToken;
	}
	public int getUserId(){
		return userId;
	}
	public void setUserId(int userId){
		this.userId = userId;
	}
	public boolean isValid(){
		return isValid;
	}
	public void setValid(boolean valid){
		isValid = valid;
	}
}
