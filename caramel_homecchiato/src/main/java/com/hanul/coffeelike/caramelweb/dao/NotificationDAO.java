package com.hanul.coffeelike.caramelweb.dao;

import com.hanul.coffeelike.caramelweb.data.FollowNotification;
import com.hanul.coffeelike.caramelweb.data.LikeNotification;
import com.hanul.coffeelike.caramelweb.data.ReactionNotification;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationDAO{
	@Autowired
	private SqlSession sql;

	public List<FollowNotification> selectNotifiedFollows(int user){
		return sql.selectList("notification.selectNotifiedFollows", user);
	}
	public List<LikeNotification> selectNotifiedLikes(int user){
		return sql.selectList("notification.selectNotifiedLikes", user);
	}
	public List<ReactionNotification> selectNotifiedReactions(int user){
		return sql.selectList("notification.selectNotifiedReactions", user);
	}

	public List<Integer> getUsersNotified(){
		return sql.selectList("notification.getUsersNotified");
	}

	public int notifyFollow(){
		return sql.update("notification.notifyFollow");
	}
	public int notifyLike(){
		return sql.update("notification.notifyLike");
	}
	public int notifyReaction(){
		return sql.update("notification.notifyReaction");
	}

	public int markFollowAsRead(FollowNotification notification){
		return sql.update("notification.markFollowAsRead", notification);
	}
	public int markLikeAsRead(LikeNotification notification){
		return sql.update("notification.markLikeAsRead", notification);
	}
	public int markReactionAsRead(ReactionNotification notification){
		return sql.update("notification.markReactionAsRead", notification);
	}

	public void removeOldFollowNotifications(){
		sql.delete("notification.removeOldFollowNotifications");
	}
	public void removeOldLikeNotifications(){
		sql.delete("notification.removeOldLikeNotifications");
	}
	public void removeOldReactionNotifications(){
		sql.delete("notification.removeOldReactionNotifications");
	}
}
