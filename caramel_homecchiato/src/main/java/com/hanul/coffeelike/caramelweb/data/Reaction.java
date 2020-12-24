package com.hanul.coffeelike.caramelweb.data;

import java.sql.Timestamp;

import org.springframework.lang.Nullable;

public class Reaction {
	private int id;
	private int post;
	private int userId;
	private Timestamp postDate;
	@Nullable private Timestamp lastEditDate;
	private String text;
	
	public Reaction(int id, int post, int userId, Timestamp postDate, @Nullable Timestamp lastEditDate, String text) {
		this.id = id;
		this.post = post;
		this.userId = userId;
		this.postDate = postDate;
		this.lastEditDate = lastEditDate;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPost() {
		return post;
	}

	public void setPost(int post) {
		this.post = post;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Timestamp getPostDate() {
		return postDate;
	}

	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}

	public Timestamp getLastEditDate() {
		return lastEditDate;
	}

	public void setLastEditDate(Timestamp lastEditDate) {
		this.lastEditDate = lastEditDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}