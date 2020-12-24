package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class UserProfileData{
	private int id;

	@Nullable private String name;
	@Nullable private String motd;
	@Nullable private String profileImage;

	@Nullable private Boolean isFollowingYou;
	@Nullable private Boolean isFollowedByYou;



	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getMotd(){
		return motd;
	}
	public void setMotd(String motd){
		this.motd = motd;
	}
	public String getProfileImage(){
		return profileImage;
	}
	public void setProfileImage(String profileImage){
		this.profileImage = profileImage;
	}
	public Boolean getIsFollowingYou(){
		return isFollowingYou;
	}
	public void setIsFollowingYou(Boolean isFollowingYou){
		this.isFollowingYou = isFollowingYou;
	}
	public Boolean getIsFollowedByYou(){
		return isFollowedByYou;
	}
	public void setIsFollowedByYou(Boolean isFollowedByYou){
		this.isFollowedByYou = isFollowedByYou;
	}
}
