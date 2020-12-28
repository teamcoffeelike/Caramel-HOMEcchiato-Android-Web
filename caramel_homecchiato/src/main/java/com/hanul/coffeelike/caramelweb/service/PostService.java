package com.hanul.coffeelike.caramelweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.hanul.coffeelike.caramelweb.dao.PostDAO;
import com.hanul.coffeelike.caramelweb.data.Post;

@Service
public class PostService {

	@Autowired private PostDAO dao;
	
	public Post post(int id, @Nullable Integer loginUser) {
		return dao.post(id, loginUser);
	}
		
	public int writePost(int loginUser, String text) {
		int postId = dao.generatePostId();
		dao.writePost(postId, loginUser, text);
		return postId;
	}
	
	public PostResult editPost(int loginUser, int post, String text) {
		Post postData = dao.findPostData(loginUser, post);

		if(postData==null)
			return new PostResult("no_post");
		if (loginUser != postData.getAuthor())
			return new PostResult("cannot_edit");
		
		dao.editPost(post, text);
		return new PostResult();
	}
	
	public PostResult deletePost(int loginUser, int post) {
		Post postData = dao.findPostData(loginUser, post);
		
		if(postData == null)
			return new PostResult("no_post");
		if(loginUser != postData.getAuthor())
			return new PostResult("cannot_delete");
			
		dao.deletePost(post);
		return new PostResult();
	}
	
	public PostResult likePost(int loginUser, int post) {
		Post postData = dao.findPostData(loginUser, post);
		
		if(postData == null)
			return new PostResult("no_post");
		
		dao.likePost();
		return new PostResult();
	}
	
	public void topPosts() {
		
	}
	
	
	public static class PostResult {
		@Nullable private String error;
		
		public PostResult() {
			this.error = null;
		}
		public PostResult(String error) {
			this.error = error;
		}
	}
}
