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
	private String isDeleted;
	
	private int postLikes;
	private int reactions;
	@Nullable
	private Boolean likedByYou;

	public Post(int id, String text, int autuor, Date postDate, @Nullable Date lastEditDate) {
		this.id = id;
		this.text = text;
		this.author = autuor;
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
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getPostLikes() {
		return postLikes;
	}
	public void setPostLikes(int postLikes) {
		this.postLikes = postLikes;
	}
	public int getReactions() {
		return reactions;
	}
	public void setReactions(int reactions) {
		this.reactions = reactions;
	}
	@Nullable
	public Boolean getLikedByYou() {
		return likedByYou;
	}
	public void setLikedByYou(@Nullable Boolean likedByYou) {
		this.likedByYou = likedByYou;
	}
	
}
