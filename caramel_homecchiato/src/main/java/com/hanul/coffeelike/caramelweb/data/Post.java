package com.hanul.coffeelike.caramelweb.data;

import java.sql.Date;

import org.springframework.lang.Nullable;

public class Post {
	private int id;
	private String text;
	private int author;
	private Date postDate;
	@Nullable
	private Date lastEditDate;

	private int likes;
	private int reactions;
	@Nullable
	private boolean likedByYou;

	public Post(int id, String text, int ahtuor, Date postDate, @Nullable Date lastEditDate) {
		this.id = id;
		this.text = text;
		this.author = ahtuor;
		this.postDate = postDate;
		this.lastEditDate = lastEditDate;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getAuthor() {
		return author;
	}
	public void setAuthor(int author) {
		this.author = author;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	@Nullable
	public Date getLastEditDate() {
		return lastEditDate;
	}
	public void setLastEditDate(@Nullable Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getReactions() {
		return reactions;
	}
	public void setReactions(int reactions) {
		this.reactions = reactions;
	}
	public boolean isLikedByYou() {
		return likedByYou;
	}
	public void setLikedByYou(boolean likedByYou) {
		this.likedByYou = likedByYou;
	}
}
