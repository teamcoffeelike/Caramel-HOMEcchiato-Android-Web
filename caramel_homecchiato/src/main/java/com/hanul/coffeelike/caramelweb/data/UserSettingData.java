package com.hanul.coffeelike.caramelweb.data;

public class UserSettingData {
	
	private int id;
	
	private String name;	//닉네임
	private String motd;	//자기소개(상태메세지)
	private String profileImage;
	//알림
	private boolean notifyReactions;
	private boolean notifyFollows;
	private boolean notifyLikes;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
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
