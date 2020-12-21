package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

import java.sql.Timestamp;

public class Post {
    private int id;
    private String text;
    private Timestamp postDate;
    @Nullable private Timestamp lastEditDate;

    public Post(int id, String text, Timestamp postDate, @Nullable Timestamp lastEditDate) {
        this.id = id;
        this.text = text;
        this.postDate = postDate;
        this.lastEditDate = lastEditDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Timestamp getPostDate() { return postDate; }
    public void setPostDate(Timestamp postDate) { this.postDate = postDate; }
    @Nullable public Timestamp getLastEditDate() { return lastEditDate; }
    public void setLastEditDate(@Nullable Timestamp lastEditDate) { this.lastEditDate = lastEditDate; }
}
