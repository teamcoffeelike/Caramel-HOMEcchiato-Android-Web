package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class RecipeCover{
	private int id;
	private RecipeCategory category;
	private String title;
	@Nullable private String coverImage;
	private int author;
	private int ratings;
	@Nullable private Double averageRating;

	public RecipeCover(int id,
	                   RecipeCategory category,
	                   String title,
	                   @Nullable String coverImage,
	                   int author,
	                   int ratings,
	                   @Nullable Double averageRating){
		this.id = id;
		this.category = category;
		this.title = title;
		this.coverImage = coverImage;
		this.author = author;
		this.ratings = ratings;
		this.averageRating = averageRating;
	}

	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public RecipeCategory getCategory(){
		return category;
	}
	public void setCategory(RecipeCategory category){
		this.category = category;
	}
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	@Nullable public String getCoverImage(){
		return coverImage;
	}
	public void setCoverImage(@Nullable String coverImage){
		this.coverImage = coverImage;
	}
	public int getAuthor(){
		return author;
	}
	public void setAuthor(int author){
		this.author = author;
	}
	public int getRatings(){
		return ratings;
	}
	public void setRatings(int ratings){
		this.ratings = ratings;
	}
	@Nullable public Double getAverageRating(){
		return averageRating;
	}
	public void setAverageRating(@Nullable Double averageRating){
		this.averageRating = averageRating;
	}
}
