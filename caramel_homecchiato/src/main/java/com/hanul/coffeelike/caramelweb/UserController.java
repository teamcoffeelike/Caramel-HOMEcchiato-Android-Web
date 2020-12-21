package com.hanul.coffeelike.caramelweb;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import com.hanul.coffeelike.caramelweb.data.UserSettingData;
import com.hanul.coffeelike.caramelweb.service.UserService;
import com.hanul.coffeelike.caramelweb.service.UserService.SetPasswordResult;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;

	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex) {
		return JsonHelper.failure("bad_parameter");
	}

	/**
	# 유저 설정
	userSettings
	
	# 성공 시
	{
		user : {
			id : <Integer>                 # ID
			name : <String>                # 이름
			[motd : <String>]              # motd
			[profileImage : <String>]      # 프로필 사진 이미지로 향하는 URL
		}
		notifyReactions : <Boolean> # 댓글 알림
		notifyLikes : <Boolean>     # 좋아요 알림
		notifyFollows : <Boolean>   # 팔로우 알림
	}

	# 에러
	not_logged_in : 로그인 상태가 아님*/

	// 유저설정
	@ResponseBody
	@RequestMapping("/userSettings")
	public String userSettings(HttpSession session) {
		Integer loginUser = (Integer) session.getAttribute("loginUser");
		if (loginUser == null) return JsonHelper.failure("not_logged_in");
		
		UserSettingData userSettingData = service.userSettings(loginUser);
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
	# 유저의 프로필 요청
	# 프로필 화면에서 표시될 이름, motd, 프로필 사진을 가져옵니다.
	profile
	 
	-> userId : <Integer>

	# 성공시
	{
		id : <Integer>                 # ID
		name : <String>                # 이름
		[motd : <String>]              # motd
		[profileImage : <String>]      # 프로필 사진 이미지로 향하는 URL
		[isFollowingYou : <Boolean>]   # 해당 유저가 로그인된 본인을 팔로잉하는지 여부
		                               # 로그인 상태의 다른 사람 프로필에만 존재
		[isFollowedByYou : <Boolean>]  # 로그인된 본인이 해당 유저를 팔로잉하는지 여부
		                               # 로그인 상태의 다른 사람 프로필에만 존재
	}

	# 에러
	no_user : 존재하지 않는 유저
	*/

	// 프로필요청
	@ResponseBody
	@RequestMapping("/profile")
	public String profile(HttpSession session
						, @RequestParam int userId) {
		UserProfileData userProfileData = service.profile(userId);
		if (userProfileData == null) return JsonHelper.failure("no_user");
		
		return JsonHelper.GSON.toJson(userProfileData);
	}
	
	/**
	# 닉네임 설정
	setName
	
	-> name : <String>

	# 성공 시 추가 데이터 없음

	# 에러
	bad_name      : 존재하지 않거나 유효하지 않은 name 인자
	not_logged_in : 로그인 상태가 아님
	*/

	// 닉네임 설정
	@ResponseBody
	@RequestMapping("/setName")
	public String setName(HttpSession session
						, @RequestParam String name) {
		Integer loginUser = (Integer) session.getAttribute("loginUser");
		if (loginUser == null) return JsonHelper.failure("not_logged_in");
		if (name == null) return JsonHelper.failure("bad_name");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", loginUser);
		map.put("name", name);
		service.setName(map);
			
		return JsonHelper.GSON.toJson(new JsonObject());
	}
	
	/**
	# 비밀번호 재설정
	setPassword

	-> password : <String>
	-> newPassword : <String>

	# 성공 시 추가 데이터 없음

	# 에러
	not_logged_in      : 로그인 상태가 아님
	no_password        : password를 사용하지 않는 계정(aka 소셜 로그인 사용 중인 계정)
	incorrect_password : 기존 암호와 일치하지 않는 password 인자
	bad_new_password   : 존재하지 않거나 유효하지 않은 newPassword 인자
	*/

	// 비밀번호 재설정
	@ResponseBody
	@RequestMapping("/setPassword")
	public String setPassword(HttpSession session,
							@RequestParam String password,
							@RequestParam String newPassword) {
		Integer loginUser = (Integer) session.getAttribute("loginUser");
		if (loginUser == null) return JsonHelper.failure("not_logged_in");
		if (newPassword.isEmpty()||password.equals(newPassword))
			return JsonHelper.failure("bad_new_password"); 
		
		
		SetPasswordResult result = service.setPassword(loginUser, password, newPassword);

		return JsonHelper.GSON.toJson(result);
	}
	
	//following
	@ResponseBody
	@RequestMapping("/setFollowing")
	public String setFollowing(HttpSession session
							, @RequestParam int followingId
							, @RequestParam boolean following) {
		Integer loginUser = (Integer) session.getAttribute("loginUser");
		if(loginUser==null) return JsonHelper.failure("not_logged_in");
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("loginUser", loginUser);
		map.put("followingId", followingId);
		
		boolean result = service.setFollowing(map, following);
		if (!result) return JsonHelper.failure("not_following");
		
		return JsonHelper.GSON.toJson(result);
	}	
}
