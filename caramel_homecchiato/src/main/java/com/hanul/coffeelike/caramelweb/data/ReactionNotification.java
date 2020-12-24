package com.hanul.coffeelike.caramelweb.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;

import java.lang.reflect.Type;
import java.sql.Date;

public class ReactionNotification extends Notification{
	private int reaction;

	public ReactionNotification(int notifiedUser,
	                            Date notifyDate,
	                            Date readDate,
	                            int reaction,
	                            boolean notified,
	                            boolean pushNotificationSent){
		super(notifiedUser, notifyDate, readDate, notified, pushNotificationSent);
		this.reaction = reaction;
	}

	public ReactionNotification(int notifiedUser, int reaction){
		super(notifiedUser, null, null, false, false);
		this.reaction = reaction;
	}

	@Override public NotificationType type(){
		return NotificationType.REACTION;
	}

	public int getReaction(){
		return reaction;
	}
	public void setReaction(int reaction){
		this.reaction = reaction;
	}


	public enum Json implements JsonSerializer<ReactionNotification>{
		INSTANCE;

		@Override public JsonElement serialize(ReactionNotification src,
		                                       Type typeOfSrc,
		                                       JsonSerializationContext context){
			return JsonHelper.create()
					.with("type", "reaction")
					.with("notifiedUser", src.getNotifiedUser())
					.with("notifyDate", src.getNotifyDate())
					.with("readDate", src.getReadDate())
					.with("reaction", src.reaction)
					.asObject();
		}
	}
}
