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


    @Transaction
    @Query("SELECT * FROM Post_table where mobile_id = :mobileID AND category IN (:categories) ORDER BY id DESC")
    LiveData<List<PostWithInteractions>> getMyPostsWithInteractions(List<String> categories, String mobileID);

    @Transaction
    @Query("SELECT * FROM Post_table where id IN (SELECT post_id FROM Post_interaction_table where mobile_id = :mobileID AND is_liked = 1) AND category IN (:categories) ORDER BY id DESC")
    LiveData<List<PostWithInteractions>> getMyLikedPostsWithInteractions(List<String> categories, String mobileID);

    @Transaction
    @Query("SELECT * FROM Post_table where category IN (:categories) ORDER BY id DESC")
    LiveData<List<PostWithInteractions>> getPostsWithInteractions(List<String> categories);







}
