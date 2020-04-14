package com.example.aalboxapp;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Post_table")
public class Post {

    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo (name = "location")
    private String location;

    @ColumnInfo (name = "category")
    private String category;

    @ColumnInfo (name = "description")
    private String description;

    @ColumnInfo (name = "dislike_amount")
    private int dislike;

    @ColumnInfo (name = "like_amount")
    private int like;

    //Not used in current version
    @ColumnInfo (name = "mobile_id")
    private String mobileId;

    //Not used in current version
    @ColumnInfo (name = "link")
    private String link;



    public Post(String location, String category, String description, int like, int dislike, String mobileId) {
        this.location = location;
        this.category = category;
        this.description = description;
        this.like = like;
        this.dislike = dislike;
        this.mobileId = mobileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getMobileId() {
        return mobileId;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
