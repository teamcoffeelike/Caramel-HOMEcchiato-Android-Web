package com.hanul.coffeelike.caramelweb;

import com.hanul.coffeelike.caramelweb.data.FollowNotification;
import com.hanul.coffeelike.caramelweb.data.LikeNotification;
import com.hanul.coffeelike.caramelweb.data.Notification;
import com.hanul.coffeelike.caramelweb.data.ReactionNotification;
import com.hanul.coffeelike.caramelweb.service.NotificationService;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

// TODO TRIGGER
@Controller
public class NotificationController{
	@Autowired
	private NotificationService notificationService;

	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex){
		return JsonHelper.failure("bad_parameter");
	}

	/**
	 * # 성공 시
	 * {
	 * notifications : [( # 시간 순서로 정렬된 알림
	 * {
	 * type : "reaction"
	 * notifyDate : <Date>  # 알림을 받은 시간
	 * user : <Integer>     # 댓글을 단 유저 ID
	 * post : <Integer>     # 댓글이 달린 포스트 ID
	 * reaction : <Integer> # 댓글 ID
	 * }
	 * | {
	 * type : "like"
	 * notifyDate : <Date>  # 알림을 받은 시간
	 * user : <Integer>     # 좋아요를 누른 유저 ID
	 * post : <Integer>     # 좋아요가 눌린 포스트 ID
	 * }
	 * | {
	 * type : "follow"
	 * notifyDate : <Date>  # 알림을 받은 시간
	 * user : <Integer>     # 팔로우 누른 유저 ID
	 * }
	 * )]
	 * }
	 * # 에러
	 * not_logged_in : 로그인 상태가 아님
	 */
	@ResponseBody
	@RequestMapping("/notification")
	public String notification(HttpSession session){
		Integer loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		List<Notification> l = notificationService.notification(loginUser);
		return JsonHelper.create()
				.with("notifications", l)
				.render();
	}

	/**
	 * # 성공 시
	 * {}
	 * <p>
	 * # 에러
	 * not_logged_in   : 로그인 상태가 아님
	 * no_notification : 일치하는 알림이 없음
	 */
	@ResponseBody
	@RequestMapping("/markFollowNotificationAsRead")
	public String markFollowNotificationAsRead(
			HttpSession session,
			@RequestParam int user){
		Integer loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		if(!notificationService.markFollowNotificationAsRead(
				new FollowNotification(loginUser, user))){
			return JsonHelper.failure("no_notification");
		}
		return "{}";
	}

	/**
	 * # 성공 시
	 * {}
	 * <p>
	 * # 에러
	 * not_logged_in   : 로그인 상태가 아님
	 * no_notification : 일치하는 알림이 없음
	 */
	@ResponseBody
	@RequestMapping("/markLikeNotificationAsRead")
	public String markLikeNotificationAsRead(
			HttpSession session,
			@RequestParam int user,
			@RequestParam int post){
		Integer loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		if(!notificationService.markLikeNotificationAsRead(
				new LikeNotification(loginUser, user, post))){
			return JsonHelper.failure("no_notification");
		}
		return "{}";
	}

	/**
	 * # 성공 시
	 * {}
	 * <p>
	 * # 에러
	 * not_logged_in   : 로그인 상태가 아님
	 * no_notification : 일치하는 알림이 없음
	 */
	@ResponseBody
	@RequestMapping("/markReactionNotificationAsRead")
	public String markReactionNotificationAsRead(
			HttpSession session,
			@RequestParam int reaction){
		Integer loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		if(!notificationService.markReactionNotificationAsRead(
				new ReactionNotification(loginUser, reaction))){
			return JsonHelper.failure("no_notification");
		}
		return "{}";
	}

	/**
	 * 매 1시간마다 실행. 읽지 않은 상태로 2주가 지났거나 읽은 후 2시간이 지난 알림을 데이터베이스에서 삭제합니다.
	 */
	// http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/crontrigger.html
	// https://www.freeformatter.com/cron-expression-generator-quartz.html
	@Scheduled(cron = "0 0 * * * ?")
	public void removeOldNotifications(){
		notificationService.removeOldNotifications();
	}

	/**
	 * 매 10초마다 실행. 1분이 지난 알림을 활성화시키고 각각의 유저에게 알림을 보냅니다.
	 */
	@Scheduled(cron = "0/10 * * ? * *")
	public void processNotifications(){
		List<Integer> users = notificationService.processNotifications();
		for(int user : users){
			// TODO notify
		}
	}
}
