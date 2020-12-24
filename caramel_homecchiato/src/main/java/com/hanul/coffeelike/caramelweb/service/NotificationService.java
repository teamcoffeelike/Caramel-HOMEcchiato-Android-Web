package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.NotificationDAO;
import com.hanul.coffeelike.caramelweb.data.FollowNotification;
import com.hanul.coffeelike.caramelweb.data.LikeNotification;
import com.hanul.coffeelike.caramelweb.data.Notification;
import com.hanul.coffeelike.caramelweb.data.ReactionNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class NotificationService{
	@Autowired
	private NotificationDAO dao;

	public List<Notification> notification(int userId){
		List<FollowNotification> follows = dao.selectNotifiedFollows(userId);
		List<LikeNotification> likes = dao.selectNotifiedLikes(userId);
		List<ReactionNotification> reactions = dao.selectNotifiedReactions(userId);

		List<Notification> notifications = new ArrayList<>();
		notifications.addAll(follows);
		notifications.addAll(likes);
		notifications.addAll(reactions);

		notifications.sort(Comparator.comparing(Notification::getNotifyDate).reversed());

		return notifications;
	}

	public boolean markFollowNotificationAsRead(FollowNotification notification){
		return dao.markFollowAsRead(notification)>0;
	}

	public boolean markLikeNotificationAsRead(LikeNotification notification){
		return dao.markLikeAsRead(notification)>0;
	}

	public boolean markReactionNotificationAsRead(ReactionNotification notification){
		return dao.markReactionAsRead(notification)>0;
	}

	public void removeOldNotifications(){
		dao.removeOldFollowNotifications();
		dao.removeOldLikeNotifications();
		dao.removeOldReactionNotifications();
	}

	public List<Integer> processNotifications(){
		List<Integer> usersNotified = dao.getUsersNotified();
		dao.notifyFollow();
		dao.notifyLike();
		dao.notifyReaction();
		return usersNotified;
	}
}
