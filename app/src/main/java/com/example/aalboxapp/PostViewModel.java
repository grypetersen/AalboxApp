package com.example.aalboxapp;

import android.app.Application;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PostViewModel extends AndroidViewModel {

    private PostRepo repo;
    LiveData<List<PostWithInteractions>> postWithInteractionLiveData;


    public PostViewModel(@NonNull Application application) {
        super(application);
        repo = new PostRepo(application);
        postWithInteractionLiveData = repo.getAllPostWithInteractions();
    }

    //Post
    public void insert(Post post){
        repo.insert(post);
    }
    public void update(Post post){
        repo.update(post);
    }


    //post interactions
    public void insert(PostInteraction postInteraction){
        repo.insert(postInteraction);
    }
    public void update(PostInteraction postInteraction){
        repo.update(postInteraction);
    }
    public void delete(PostInteraction postInteraction){
        repo.delete(postInteraction);
    }

    public LiveData<List<PostWithInteractions>> getPostWithInteractionLiveData(){
        return postWithInteractionLiveData;
    }



}
