package com.example.aalboxapp;

import android.app.Application;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class PostViewModel extends AndroidViewModel {

    private PostRepo repo;
    LiveData<List<PostWithInteractions>> postWithInteractionLiveData;
    MutableLiveData<PostFilter> filterlivedata = new MutableLiveData<>();


    public PostViewModel(@NonNull Application application) {
        super(application);
        repo = new PostRepo(application);
        postWithInteractionLiveData =
                Transformations.switchMap(filterlivedata, new Function<PostFilter, LiveData<List<PostWithInteractions>>>() {
                    @Override
                    public LiveData<List<PostWithInteractions>> apply(PostFilter newFilter) {
                        return repo.getPostWithInteractions(newFilter);
                    }
                });
    }

    public void setFilterlivedata(PostFilter postfilter){
        filterlivedata.setValue(postfilter);
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
