package com.hanul.coffeelike.caramelweb;

import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.Recipe;
import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import com.hanul.coffeelike.caramelweb.data.RecipeCover;
import com.hanul.coffeelike.caramelweb.service.RecipeService;
import com.hanul.coffeelike.caramelweb.service.UserService;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;

@RestController
public class RecipeController{
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private UserService userService;

	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex){
		return JsonHelper.failure("bad_parameter");
	}

	/**
	 * 레시피 리스트, 최신순 정렬<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * {
	 *   recipes: [
	 *     {
	 *       id: Integer
	 *       category: String
	 *                  ( "hot_coffee" | "ice_coffee" | "tea" | "ade" | "smoothie" | "etc" )
	 *       title: String
	 *       [ coverImage ]: URL
	 *       author: Integer
	 *       postDate: Date
	 *       [ lastEditDate ]: Date
	 *       ratings: Integer # 평가받은 수
	 *       [ averageRating ]: Number # 평가 평균
	 *     }
	 *   ]
	 * }
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * bad_request : category와 author 모두 존재하지 않음<br>
	 * bad_category : 유효하지 않은 category<br>
	 * no_user : 존재하지 않는 author<br>
	 */
	@RequestMapping("/recipeList")
	public String recipeList(HttpSession session,
	                         @Nullable @RequestParam(required = false) String category,
	                         @Nullable @RequestParam(required = false) Integer author){
		if(category==null&&author==null) return JsonHelper.failure("bad_request");
		if(category!=null&&RecipeCategory.fromString(category)==null){
			return JsonHelper.failure("bad_category");
		}
		if(author!=null&&!userService.checkIfUserExists(author)){
			return JsonHelper.failure("no_user");
		}

		return JsonHelper.GSON.toJson(recipeService.list(category, author));
	}

	/**
	 * 해당 레시피의 스텝을 포함한 모든 정보<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * {
	 *   id: Integer
	 *   category: String
	 *              ( "hot_coffee" | "ice_coffee" | "tea" | "ade" | "smoothie" | "etc" )
	 *   title: String
	 *   [ coverImage ]: URL
	 *   author: Integer
	 *   ratings: Integer # 평가받은 수
	 *   averageRating: Number # 평가 평균
	 *   steps: [
	 *     {
	 *       index: Integer
	 *       [ image ]: URL
	 *       text: String
	 *       [ task ]: (
	 *         {
	 *           type: "timer"
	 *           seconds: Integer # 시각을 잴 시간
	 *           purpose: ( "cook" | "wait" ) # 타이머의 용도 (예: ~초 동안 기다리세요, ~초 동안 조리하세요...)
	 *         }
	 *       )
	 *     }
	 *   ]
	 * }
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * no_recipe : 존재하지 않는 레시피 id<br>
	 */
	@RequestMapping("/recipe")
	public String recipe(HttpSession session,
	                     @RequestParam int id){
		Recipe recipe = recipeService.recipe(id);
		if(recipe==null) return JsonHelper.failure("no_recipe");
		return JsonHelper.GSON.toJson(recipe);
	}

	/**
	 * 레시피 작성<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * {
	 *   id: Integer # 작성한 레시피 ID
	 * }
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 */
	@RequestMapping("/writeRecipe")
	public String writeRecipe(HttpSession session,
	                          @RequestParam String text,
	                          @RequestParam File titleImage){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		// TODO 멀티파트 직접 핸들링 필요?

		return JsonHelper.failure("work_in_progress");
	}

	/**
	 * 레시피 수정<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * {
	 *   id: Integer # 수정한 레시피 ID
	 * }
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * no_recipe : 해당 ID의 레시피가 존재하지 않음<br>
	 * cannot_edit : 해당 글을 수정할 수 없음 (비 로그인 상태 포함)<br>
	 */
	@RequestMapping("/editRecipe")
	public String editRecipe(HttpSession session,
	                         @RequestParam int recipe,
	                         @RequestParam String text,
	                         @RequestParam File titleImage){


		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		// TODO 멀티파트 직접 핸들링 필요?

		return JsonHelper.failure("work_in_progress");
	}

	/**
	 * 레시피 삭제<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * no_recipe : 해당 ID의 레시피가 존재하지 않음<br>
	 * cannot_delete : 해당 글을 삭제할 수 없음 (비 로그인 상태 포함)<br>
	 */
	@RequestMapping("/deleteRecipe")
	public String deleteRecipe(HttpSession session,
	                           @RequestParam int recipe){
		RecipeCover cover = recipeService.getCover(recipe);
		if(cover==null) return JsonHelper.failure("no_recipe");

		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null||loginUser.getUserId()==cover.getAuthor())
			return JsonHelper.failure("cannot_delete");

		recipeService.delete(recipe);

		return "{}";
	}

	/**
	 * 레시피 평가<br>
	 * 기존 평가를 덮어쓸 수 있음<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 * bad_rating : 유효하지 않은 rating 인자<br>
	 * no_recipe : 해당 ID의 레시피가 존재하지 않음<br>
	 */
	@RequestMapping("/rateRecipe")
	public String rateRecipe(HttpSession session,
	                         @RequestParam int recipe,
	                         @RequestParam double rating){
		// TODO
		return "{}";
	}
}
