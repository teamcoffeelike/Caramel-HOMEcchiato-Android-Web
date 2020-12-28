package com.hanul.coffeelike.caramelweb.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.Post;

@Repository
public class PostDAO {

	@Autowired private SqlSession sql;
	
	public Post post(int id, Integer loginUser) {
		HashMap<String, Integer> m = new HashMap<String, Integer>();
		m.put("id", id);
		m.put("userId", loginUser);
		return sql.selectOne("post.postDetail", m);
	}
	
	public int generatePostId() {
		return sql.selectOne("post.generatePostId");
	}
		
	public int writePost(int postId, int loginUser, String text) {
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("postId", postId);
		m.put("loginUser", loginUser);
		m.put("text", text);
		return sql.insert("post.writePost", m);
	}
	
	public Post findPostData(int loginUser, int post) {
		HashMap<String, Integer> m = new HashMap<String, Integer>();
		m.put("loginUser", loginUser);
		m.put("post", post);
		return sql.selectOne("post.findPostData", m);
	}
	
	public void editPost(int post, String text) {
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("post", post);
		m.put("text", text);
		sql.update("post.editPost", m);
	}
	
	public void deletePost(int post) {
		sql.update("post.deletePost", post);
	}

	public void likePost() {
		//like update
	}
	
	public void topPosts() {
		//like select > dense rank 
	}

	
}
