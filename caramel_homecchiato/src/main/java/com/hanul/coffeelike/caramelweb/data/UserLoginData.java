package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

/**
 * 유저의 로그인 체크에 필요한 데이터
 */
public class UserLoginData {
	private int id;
	@Nullable private String email;
	@Nullable private String phoneNumber;
	@Nullable private String password;
	
	public UserLoginData(int id, @Nullable String email, @Nullable String phoneNumber, @Nullable String password) {
		super();
		this.id = id;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Nullable public String getEmail() {
		return email;
	}
	public void setEmail(@Nullable String email) {
		this.email = email;
	}
	@Nullable public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(@Nullable String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Nullable public String getPassword() {
		return password;
	}
	public void setPassword(@Nullable String password) {
		this.password = password;
	}
}
