package com.hanul.coffeelike.caramelweb.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.Post;

@Repository
public class PostDAO {

	@Autowired private SqlSession sql;
	
	public Post post(int id, int userId) {
		HashMap<String, Integer> m = new HashMap<String, Integer>();
		m.put("id", id);
		m.put("userId", userId);
		return sql.selectOne("post.postDetail", m);
	}
		
	public int writePost(int loginUser, String text) {
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("loginUser", loginUser);
		m.put("text", text);
		return sql.insert("post.writePost", text);
	}
	
	public void editPost() {
		//update
	}
	
	public void deletePost() {
		//delete
	}
	
	public void likePost() {
		//like update
	}
	
	public void topPosts() {
		//like select > dense rank 
	}
	
}
