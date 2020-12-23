package com.hanul.coffeelike.caramelweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanul.coffeelike.caramelweb.dao.PostDAO;
import com.hanul.coffeelike.caramelweb.data.Post;

@Service
public class PostService {

	@Autowired private PostDAO dao;
	
	public Post post(int id, int userId) {
		return dao.post(id, userId);
	}
		
	public int writePost(int loginUser, String text) {
		return dao.writePost(loginUser, text);
	}
	
	public void editPost() {
		
	}
	
	public void deletePost() {
		
	}
	
	public void likePost() {
		
	}
	
	public void topPosts() {
		
	}
	
}
