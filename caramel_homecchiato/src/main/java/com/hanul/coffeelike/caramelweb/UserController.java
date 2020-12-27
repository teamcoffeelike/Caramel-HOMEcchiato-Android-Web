package com.hanul.coffeelike.caramelweb;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.NotificationType;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import com.hanul.coffeelike.caramelweb.data.UserSettingData;
import com.hanul.coffeelike.caramelweb.service.UserService;
import com.hanul.coffeelike.caramelweb.service.UserService.SetPasswordResult;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import com.hanul.coffeelike.caramelweb.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController{
	@Autowired
	private UserService service;

	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex){
		return JsonHelper.failure("bad_parameter");
	}

	/**
	 * 유저 설정<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   user: {
	 *     id: Integer
	 *     name: String
	 *     [ motd ]: String
	 *     [ profileImage ]: URL
	 *   }
	 *   notifyReaction: Boolean
	 *   notifyLike: Boolean
	 *   notifyFollow: Boolean
	 * }
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 */
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
	 * 유저의 프로필 요청<br>
	 * 프로필 화면에서 표시될 이름, motd, 프로필 사진을 가져옵니다.<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   id: Integer
	 *   name: String
	 *   [ motd ]: String
	 *   [ profileImage ]: URL
	 *   [ isFollowingYou ]: Boolean
	 *   [ isFollowedByYou ]: Boolean
	 * }
	 * }</pre>
	 * <b>에러: </b><br>
	 * no_user : 존재하지 않는 유저<br>
	 */
	@RequestMapping("/profile")
	public String profile(HttpSession session,
	                      @RequestParam int userId){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		UserProfileData userProfileData = service.profile(loginUser==null ? null : loginUser.getUserId(), userId);
		if(userProfileData==null) return JsonHelper.failure("no_user");

		return JsonHelper.GSON.toJson(userProfileData);
	}

	/**
	 * 닉네임 설정<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 * <b>에러: </b><br>
	 * bad_name : 유효하지 않은 name 인자<br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 */
	@RequestMapping("/setName")
	public String setName(HttpSession session,
	                      @RequestParam String name){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");
		if(!Validate.name(name)) return JsonHelper.failure("bad_name");

		service.setName(loginUser.getUserId(), name);

		return "{}";
	}

	/**
	 * 패스워드 변경<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 * <b>에러: </b><br>
	 * not_logged_in      : 로그인 상태가 아님<br>
	 * no_password        : password를 사용하지 않는 계정(aka 소셜 로그인 사용 중인 계정)<br>
	 * incorrect_password : 기존 암호와 일치하지 않는 password 인자<br>
	 * bad_new_password   : 존재하지 않거나 유효하지 않은 newPassword 인자<br>
	 */
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
	 * 팔로워 가져오기<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   users: [
	 *     {
	 *       id: Integer
	 *       name: String
	 *       [ profileImage ]: URL
	 *     }
	 *   ]
	 * }
	 * }</pre>
	 * <b>에러: </b><br>
	 * not_logged_in      : 로그인 상태가 아님<br>
	 * no_password        : password를 사용하지 않는 계정(aka 소셜 로그인 사용 중인 계정)<br>
	 * incorrect_password : 기존 암호와 일치하지 않는 password 인자<br>
	 * bad_new_password   : 존재하지 않거나 유효하지 않은 newPassword 인자<br>
	 */
	@RequestMapping("/getFollower")
	public String getFollower(HttpSession session){
		Integer loginUser = (Integer)session.getAttribute("loginUser");
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		List<UserProfileData> users = service.getFollower(loginUser);
		JsonElement e = JsonHelper.GSON.toJsonTree(users);

		JsonObject o = new JsonObject();
		o.add("users", e);

		return JsonHelper.GSON.toJson(o);
	}

	/**
	 * 팔로잉 여부 설정<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 * <b>에러: </b><br>
	 * not_logged_in<br>
	 * same_user<br>
	 * already_following<br>
	 * not_following<br>
	 */
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
	 * 알림 설정<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 * <b>에러: </b><br>
	 * bad_type      : 존재하지 않거나 유효하지 않은 type 인자<br>
	 * bad_value     : 존재하지 않거나 유효하지 않은 value 인자<br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 */
	@RequestMapping("/setNotification")
	public String setNotification(HttpSession session,
	                              @RequestParam String type,
	                              @RequestParam String value /* on / off */){
		NotificationType notificationType = NotificationType.fromString(type);
		if(notificationType==null) return JsonHelper.failure("bad_type");
		boolean flag;

		switch(value){
			case "on":
				flag = true;
				break;
			case "off":
				flag = false;
				break;
			default:
				return JsonHelper.failure("bad_value");
		}

		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		service.setNotification(loginUser.getUserId(), notificationType, flag ? "Y" : "N");
		return "{}";
	}
}
