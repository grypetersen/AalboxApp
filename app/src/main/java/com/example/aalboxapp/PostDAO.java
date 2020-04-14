package com.example.aalboxapp;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public interface PostDAO {

    //Post
    @Insert
    void addPost(Post post);

    @Update
    void updatePost(Post post);

    @Query("SELECT * from Post_table ORDER BY id DESC")
    LiveData<List<Post>> getAllPost();

    //PostInteraction
    @Query("SELECT * from Post_interaction_table")
    LiveData<List<PostInteraction>> getAllPostInteractions();

    @Insert
    void addPostInteraction(PostInteraction postInteraction);

    @Update
    void updatePostInteraction(PostInteraction postInteraction);

    @Delete
    void deletePostInteraction(PostInteraction postInteraction);

    //AllPostWithLikes
    @Transaction
    @Query("SELECT * FROM Post_table ORDER BY id DESC")
    LiveData<List<PostWithInteractions>> getAllPostsWithInteractions();





}
