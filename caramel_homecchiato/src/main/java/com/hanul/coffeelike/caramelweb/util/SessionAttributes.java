package com.hanul.coffeelike.caramelweb.util;

/**
 * 세션의 Attribute에 사용되는 키 값
 */
public final class SessionAttributes {
	private SessionAttributes() {}
	
	/**
	 * 현재 세션에서 로그인 중인 유저의 ID
	 */
	public static final String LOGIN_USER = "loginUser";
}
