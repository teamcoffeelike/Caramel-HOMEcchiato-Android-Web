package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.NotificationDAO;
import com.hanul.coffeelike.caramelweb.data.FollowNotification;
import com.hanul.coffeelike.caramelweb.data.LikeNotification;
import com.hanul.coffeelike.caramelweb.data.Notification;
import com.hanul.coffeelike.caramelweb.data.ReactionNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

	/**
	 * 매 1시간마다 실행. 읽지 않은 상태로 2주가 지났거나 읽은 후 2시간이 지난 알림을 데이터베이스에서 삭제합니다.
	 */
	// http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/crontrigger.html
	// https://www.freeformatter.com/cron-expression-generator-quartz.html
	@Scheduled(cron = "0 0 * * * ?")
	public void removeOldNotifications(){
		System.out.println("removeOldNotifications");
		dao.removeOldFollowNotifications();
		dao.removeOldLikeNotifications();
		dao.removeOldReactionNotifications();
	}

	/**
	 * 매 10초마다 실행. 1분이 지난 알림을 활성화시키고 각각의 유저에게 알림을 보냅니다.
	 */
	@Scheduled(cron = "0/10 * * ? * *")
	public void processNotifications(){
		System.out.println("processNotifications");

		List<Integer> users = dao.getUsersNotified();
		dao.notifyFollow();
		dao.notifyLike();
		dao.notifyReaction();

		for(int user : users){
			// TODO notify
		}
	}
}
