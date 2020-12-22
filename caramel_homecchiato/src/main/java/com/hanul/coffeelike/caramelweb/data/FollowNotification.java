package com.hanul.coffeelike.caramelweb.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;

import java.lang.reflect.Type;
import java.sql.Date;

public class FollowNotification extends Notification{
	private int followedUser;

	public FollowNotification(int notifiedUser,
	                          Date notifyDate,
	                          Date readDate,
	                          int followedUser,
	                          boolean notified,
	                          boolean pushNotificationSent){
		super(notifiedUser, notifyDate, readDate, notified, pushNotificationSent);
		this.followedUser = followedUser;
	}


	public FollowNotification(int notifiedUser, int followedUser){
		super(notifiedUser, null, null, false, false);
		this.followedUser = followedUser;
	}

	@Override public NotificationType type(){
		return NotificationType.FOLLOW;
	}

	public int getFollowedUser(){
		return followedUser;
	}
	public void setFollowedUser(int followedUser){
		this.followedUser = followedUser;
	}


	public enum Json implements JsonSerializer<FollowNotification>{
		INSTANCE;

		@Override public JsonElement serialize(FollowNotification src,
		                                       Type typeOfSrc,
		                                       JsonSerializationContext context){
			return JsonHelper.create()
					.with("type", "follow")
					.with("notifiedUser", src.getNotifiedUser())
					.with("notifyDate", src.getNotifyDate())
					.with("readDate", src.getReadDate())
					.with("followedUser", src.followedUser)
					.asObject();
		}
	}
}
