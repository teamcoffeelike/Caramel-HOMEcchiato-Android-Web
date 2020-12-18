package com.hanul.coffeelike.caramelweb.util;

import java.util.regex.Pattern;

public final class Validate{
	private Validate(){}

	private static final Pattern EMAIL_NAME = Pattern.compile("[^\t\n]{1,25}");
	private static final Pattern EMAIL_REGEX = Pattern.compile("[^@]+@[^@]+");
	private static final Pattern PHONE_NUMBER_REGEX = Pattern.compile("\\d{3}[ -]?\\d{4}[ -]?\\d{4}");
	private static final Pattern PASSWORD_REGEX = Pattern.compile("\\S{3,63}");

	public static boolean name(String name){
		return EMAIL_NAME.matcher(name).matches();
	}
	public static boolean email(String email){
		return EMAIL_REGEX.matcher(email).matches();
	}
	public static boolean phoneNumber(String phoneNumber){
		return PHONE_NUMBER_REGEX.matcher(phoneNumber).matches();
	}
	public static boolean password(String password){
		return PASSWORD_REGEX.matcher(password).matches();
	}
}
