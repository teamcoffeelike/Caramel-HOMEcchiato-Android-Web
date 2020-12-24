package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class UserProfileData{
	private int id;
	private String name;
	@Nullable private String profileImage;

	public UserProfileData(int id, String name, @Nullable String profileImage){
		this.id = id;
		this.name = name;
		this.profileImage = profileImage;
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
	@Nullable public String getProfileImage(){
		return profileImage;
	}
	public void setProfileImage(@Nullable String profileImage){
		this.profileImage = profileImage;
	}
}
