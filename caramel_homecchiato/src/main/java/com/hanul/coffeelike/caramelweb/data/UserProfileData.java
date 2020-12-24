package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class UserProfileData{
	private int id;

	private String name;
	@Nullable private String motd;
	@Nullable private String profileImage;

	@Nullable private Boolean isFollowingYou;
	@Nullable private Boolean isFollowedByYou;

	public UserProfileData(int id,
	                       String name,
	                       @Nullable String motd,
	                       @Nullable String profileImage,
	                       @Nullable Boolean isFollowingYou,
	                       @Nullable Boolean isFollowedByYou){
		this.id = id;
		this.name = name;
		this.motd = motd;
		this.profileImage = profileImage;
		this.isFollowingYou = isFollowingYou;
		this.isFollowedByYou = isFollowedByYou;
	}

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
	@Nullable public String getMotd(){
		return motd;
	}
	public void setMotd(@Nullable String motd){
		this.motd = motd;
	}
	@Nullable public String getProfileImage(){
		return profileImage;
	}
	public void setProfileImage(@Nullable String profileImage){
		this.profileImage = profileImage;
	}
	@Nullable public Boolean getFollowingYou(){
		return isFollowingYou;
	}
	public void setFollowingYou(@Nullable Boolean followingYou){
		isFollowingYou = followingYou;
	}
	@Nullable public Boolean getFollowedByYou(){
		return isFollowedByYou;
	}
	public void setFollowedByYou(@Nullable Boolean followedByYou){
		isFollowedByYou = followedByYou;
	}
}
