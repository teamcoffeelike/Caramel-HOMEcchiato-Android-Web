package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

import java.sql.Date;

public abstract class Notification{
	private int notifiedUser;
	private Date notifyDate;
	@Nullable private Date readDate;

	private boolean notified;
	private boolean pushNotificationSent;

	public Notification(int notifiedUser,
	                    Date notifyDate,
	                    @Nullable Date readDate, boolean notified, boolean pushNotificationSent){
		this.notifiedUser = notifiedUser;
		this.notifyDate = notifyDate;
		this.readDate = readDate;
		this.notified = notified;
		this.pushNotificationSent = pushNotificationSent;
	}

	public abstract NotificationType type();

	public int getNotifiedUser(){
		return notifiedUser;
	}
	public void setNotifiedUser(int notifiedUser){
		this.notifiedUser = notifiedUser;
	}
	public Date getNotifyDate(){
		return notifyDate;
	}
	public void setNotifyDate(Date notifyDate){
		this.notifyDate = notifyDate;
	}
	@Nullable public Date getReadDate(){
		return readDate;
	}
	public void setReadDate(@Nullable Date readDate){
		this.readDate = readDate;
	}
	public boolean isNotified(){
		return notified;
	}
	public void setNotified(boolean notified){
		this.notified = notified;
	}
	public boolean isPushNotificationSent(){
		return pushNotificationSent;
	}
	public void setPushNotificationSent(boolean pushNotificationSent){
		this.pushNotificationSent = pushNotificationSent;
	}
}
