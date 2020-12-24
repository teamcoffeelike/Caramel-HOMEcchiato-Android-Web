package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

import java.util.UUID;

public class LoginResult{
	@Nullable private String error;
	@Nullable private Integer userId;
	@Nullable private UUID authToken;

	public LoginResult(String error){
		this.error = error;
	}
	public LoginResult(Integer userId, UUID authToken){
		this.userId = userId;
		this.authToken = authToken;
	}

	@Nullable public String getError(){
		return error;
	}
	public void setError(@Nullable String error){
		this.error = error;
	}
	@Nullable public Integer getUserId(){
		return userId;
	}
	public void setUserId(@Nullable Integer userId){
		this.userId = userId;
	}
	@Nullable public UUID getAuthToken(){
		return authToken;
	}
	public void setAuthToken(@Nullable UUID authToken){
		this.authToken = authToken;
	}

	public AuthToken createAuthToken(){
		return new AuthToken(authToken, userId);
	}
}
