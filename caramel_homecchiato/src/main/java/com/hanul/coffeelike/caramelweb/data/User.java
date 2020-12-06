package com.hanul.coffeelike.caramelweb.data;

public class User {
	
	private int id;
	//로그인 데이터
	private String email;
	private String phoneNumber;
	private String kakaoAccountCi;
	private String password;
	//프로필
	private String name;
	private String motd;		//상태메세지
	private int profileImage;
	//세팅
	private boolean notifyReactions;
	private boolean notifyFollows;
	private boolean notifyLikes;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getKakaoAccountCi() {
		return kakaoAccountCi;
	}
	public void setKakaoAccountCi(String kakaoAccountCi) {
		this.kakaoAccountCi = kakaoAccountCi;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMotd() {
		return motd;
	}
	public void setMotd(String motd) {
		this.motd = motd;
	}
	public int getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(int profileImage) {
		this.profileImage = profileImage;
	}
	public boolean isNotifyReactions() {
		return notifyReactions;
	}
	public void setNotifyReactions(boolean notifyReactions) {
		this.notifyReactions = notifyReactions;
	}
	public boolean isNotifyFollows() {
		return notifyFollows;
	}
	public void setNotifyFollows(boolean notifyFollows) {
		this.notifyFollows = notifyFollows;
	}
	public boolean isNotifyLikes() {
		return notifyLikes;
	}
	public void setNotifyLikes(boolean notifyLikes) {
		this.notifyLikes = notifyLikes;
	}
	
}
