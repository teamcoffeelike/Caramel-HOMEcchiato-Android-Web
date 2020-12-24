package com.hanul.coffeelike.caramelweb;

import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.NotificationType;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import com.hanul.coffeelike.caramelweb.data.UserSettingData;
import com.hanul.coffeelike.caramelweb.service.UserService;
import com.hanul.coffeelike.caramelweb.service.UserService.SetPasswordResult;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController{
	@Autowired
	private UserService service;

	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex){
		return JsonHelper.failure("bad_parameter");
	}

	/**
	 * # 성공 시
	 * {
	 * user : {
	 * id : <Integer>                 # ID
	 * name : <String>                # 이름
	 * [motd : <String>]              # motd
	 * [profileImage : <String>]         # 프로필 사진 이미지로 향하는 URL
	 * }
	 * notifyReactions : <Boolean> # 댓글 알림
	 * notifyLikes : <Boolean>     # 좋아요 알림
	 * notifyFollows : <Boolean>   # 팔로우 알림
	 * }
	 * <p>
	 * # 에러
	 * not_logged_in : 로그인 상태가 아님
	 */
	@ResponseBody
	@RequestMapping("/userSettings")
	public String userSettings(HttpSession session){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		UserSettingData userSettingData = service.userSettings(loginUser.getUserId());
		JsonObject jsonObject = new JsonObject();
		{
			JsonObject subObject = new JsonObject();
			subObject.addProperty("id", userSettingData.getId());
			subObject.addProperty("name", userSettingData.getName());
			subObject.addProperty("motd", userSettingData.getMotd());
			subObject.addProperty("profileImage", userSettingData.getProfileImage());

			jsonObject.add("user", subObject);
		}
		jsonObject.addProperty("notifyReactions", userSettingData.isNotifyReactions());
		jsonObject.addProperty("notifyLikes", userSettingData.isNotifyLikes());
		jsonObject.addProperty("notifyFollows", userSettingData.isNotifyFollows());

		return JsonHelper.GSON.toJson(jsonObject);
	}

	/**
	 * 유저의 프로필 요청
	 * 프로필 화면에서 표시될 이름, motd, 프로필 사진을 가져옵니다.
	 * <p>
	 * # 성공시
	 * {
	 * id : <Integer>                 # ID
	 * name : <String>                # 이름
	 * [motd : <String>]              # motd
	 * [profileImage : <String>]      # 프로필 사진 이미지로 향하는 URL
	 * [isFollowingYou : <Boolean>]   # 해당 유저가 로그인된 본인을 팔로잉하는지 여부
	 * # 로그인 상태의 다른 사람 프로필에만 존재
	 * [isFollowedByYou : <Boolean>]  # 로그인된 본인이 해당 유저를 팔로잉하는지 여부
	 * # 로그인 상태의 다른 사람 프로필에만 존재
	 * }
	 * <p>
	 * # 에러
	 * no_user : 존재하지 않는 유저
	 */
	@ResponseBody
	@RequestMapping("/profile")
	public String profile(HttpSession session,
	                      @RequestParam int userId){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		UserProfileData userProfileData = service.profile(loginUser==null ? null : loginUser.getUserId(), userId);
		if(userProfileData==null) return JsonHelper.failure("no_user");

		return JsonHelper.GSON.toJson(userProfileData);
	}

	/**
	 * 닉네임 설정
	 * <p>
	 * 성공 시 추가 데이터 없음
	 * <p>
	 * # 에러
	 * bad_name      : 존재하지 않거나 유효하지 않은 name 인자
	 * not_logged_in : 로그인 상태가 아님
	 */
	@ResponseBody
	@RequestMapping("/setName")
	public String setName(HttpSession session,
	                      @RequestParam String name){
		AuthToken loginUser = SessionAttributes.getLoginUser (session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");
		if(name==null) return JsonHelper.failure("bad_name");

		service.setName(loginUser.getUserId(), name);

		return "{}";
	}

	/**
	 * 패스워드 변경
	 * <p>
	 * 성공 시 추가 데이터 없음
	 * <p>
	 * # 에러
	 * not_logged_in      : 로그인 상태가 아님
	 * no_password        : password를 사용하지 않는 계정(aka 소셜 로그인 사용 중인 계정)
	 * incorrect_password : 기존 암호와 일치하지 않는 password 인자
	 * bad_new_password   : 존재하지 않거나 유효하지 않은 newPassword 인자
	 */
	@ResponseBody
	@RequestMapping("/setPassword")
	public String setPassword(HttpSession session,
	                          @RequestParam String password,
	                          @RequestParam String newPassword){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");
		if(newPassword.isEmpty()||password.equals(newPassword))
			return JsonHelper.failure("bad_new_password");

		SetPasswordResult result = service.setPassword(loginUser.getUserId(), password, newPassword);

		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 팔로잉 여부 설정
	 * <p>
	 * 성공 시 추가 데이터 없음
	 * <p>
	 * 에러
	 * not_logged_in
	 * same_user
	 * already_following
	 * not_following
	 */
	@ResponseBody
	@RequestMapping("/setFollowing")
	public String setFollowing(HttpSession session,
	                           @RequestParam int followingId,
	                           @RequestParam boolean following){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		if(loginUser.getUserId()==followingId){
			return JsonHelper.failure("same_user");
		}

		boolean result = service.setFollowing(loginUser.getUserId(), followingId, following);
		if(!result){
			return JsonHelper.failure(following ? "already_following" : "not_following");
		}

		return "{}";
	}

	/**
	 * # 성공 시 추가 데이터 없음
	 * <p>
	 * # 에러
	 * bad_type      : 존재하지 않거나 유효하지 않은 type 인자
	 * bad_value     : 존재하지 않거나 유효하지 않은 value 인자
	 * not_logged_in : 로그인 상태가 아님
	 */
	@ResponseBody
	@RequestMapping("/setNotification")
	public String setNotification(HttpSession session,
	                              @RequestParam String type,
	                              @RequestParam String value /* on / off */){
		NotificationType notificationType;
		switch(type){
		case "reaction":
			notificationType = NotificationType.REACTION;
			break;
		case "like":
			notificationType = NotificationType.LIKE;
			break;
		case "follow":
			notificationType = NotificationType.FOLLOW;
			break;
		default: return JsonHelper.failure("bad_type");
		}

		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		switch(value){
		case "on":
			service.setNotification(loginUser.getUserId(), notificationType, "Y");
			break;
		case "off":
			service.setNotification(loginUser.getUserId(), notificationType, "N");
			break;
		default:
			return JsonHelper.failure("bad_value");
		}
		return "{}";
	}
}
