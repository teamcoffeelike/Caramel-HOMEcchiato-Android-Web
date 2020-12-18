package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class JoinData{
	@Nullable private String name;
	@Nullable private String email;
	@Nullable private String phoneNumber;
	@Nullable private String password;
	@Nullable private Long kakaoAccountId;

	public JoinData(@Nullable String name,
	                @Nullable String email,
	                @Nullable String phoneNumber,
	                @Nullable String password,
	                @Nullable Long kakaoAccountId){
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.kakaoAccountId = kakaoAccountId;
	}

	@Nullable public String getName(){
		return name;
	}
	public void setName(@Nullable String name){
		this.name = name;
	}
	@Nullable public String getEmail(){
		return email;
	}
	public void setEmail(@Nullable String email){
		this.email = email;
	}
	@Nullable public String getPhoneNumber(){
		return phoneNumber;
	}
	public void setPhoneNumber(@Nullable String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	@Nullable public String getPassword(){
		return password;
	}
	public void setPassword(@Nullable String password){
		this.password = password;
	}
	@Nullable public Long getKakaoAccountId(){
		return kakaoAccountId;
	}
	public void setKakaoAccountId(@Nullable Long kakaoAccountId){
		this.kakaoAccountId = kakaoAccountId;
	}
}
