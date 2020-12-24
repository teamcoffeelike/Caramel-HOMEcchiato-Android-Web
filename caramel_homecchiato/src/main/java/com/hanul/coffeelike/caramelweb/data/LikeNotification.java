package com.hanul.coffeelike.caramelweb.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;

import java.lang.reflect.Type;
import java.sql.Date;

public class LikeNotification extends Notification{
	private int likedUser;
	private int post;

	public LikeNotification(int notifiedUser,
	                        Date notifyDate,
	                        Date readDate,
	                        int likedUser,
	                        int post,
	                        boolean notified,
	                        boolean pushNotificationSent){
		super(notifiedUser, notifyDate, readDate, notified, pushNotificationSent);
		this.likedUser = likedUser;
		this.post = post;
	}

	public LikeNotification(int notifiedUser,
	                        int likedUser,
	                        int post){
		super(notifiedUser, null, null, false, false);
		this.likedUser = likedUser;
		this.post = post;
	}

	@Override public NotificationType type(){
		return NotificationType.LIKE;
	}

	public int getLikedUser(){
		return likedUser;
	}
	public void setLikedUser(int likedUser){
		this.likedUser = likedUser;
	}
	public int getPost(){
		return post;
	}
	public void setPost(int post){
		this.post = post;
	}


	public enum Json implements JsonSerializer<LikeNotification>{
		INSTANCE;

		@Override public JsonElement serialize(LikeNotification src,
		                                       Type typeOfSrc,
		                                       JsonSerializationContext context){
			return JsonHelper.create()
					.with("type", "like")
					.with("notifiedUser", src.getNotifiedUser())
					.with("notifyDate", src.getNotifyDate())
					.with("readDate", src.getReadDate())
					.with("likedUser", src.likedUser)
					.with("post", src.post)
					.asObject();
		}
	}
}
