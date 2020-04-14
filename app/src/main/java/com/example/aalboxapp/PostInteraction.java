package com.example.aalboxapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Post_interaction_table")
public class PostInteraction {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "mobile_id")
    private String mobileId;

    @ColumnInfo (name = "post_id")
    private int postId;

    @ColumnInfo (name = "is_liked")
    private boolean isLiked;

    @ColumnInfo (name = "is_disliked")
    private boolean isDisliked;

    public PostInteraction(String mobileId, int postId, boolean isLiked, boolean isDisliked) {
        this.mobileId = mobileId;
        this.postId = postId;
        this.isLiked = isLiked;
        this.isDisliked = isDisliked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobileId() {
        return mobileId;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isDisliked() {
        return isDisliked;
    }

    public void setDisliked(boolean disliked) {
        isDisliked = disliked;
    }
}

