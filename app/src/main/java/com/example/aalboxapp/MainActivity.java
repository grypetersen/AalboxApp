package com.example.aalboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PostViewModel postViewModel;
    private final List<PostWithInteractions> postWithInteractions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final PostAdapter postAdapter = new PostAdapter();
        recyclerView.setAdapter(postAdapter);

        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);

        postViewModel.getPostWithInteractionLiveData().observe(this, new Observer<List<PostWithInteractions>>() {
            @Override
            public void onChanged(List<PostWithInteractions> postWithInteractions) {
                postAdapter.setPosts(postWithInteractions);
            }
        });

        postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(PostWithInteractions post, boolean isLike) {
                String mobileid = "123"; //change later to right mobile number on phone

                if(isLike){
                    boolean interaction_found = false;
                    for (PostInteraction interaction:post.postInteractions) {
                        if(interaction.getMobileId().equals(mobileid)){
                            interaction_found = true;
                            //Hvis vi liker en post, vi allerede har liket
                            if(interaction.isLiked()){
                                post.post.setLike(post.post.getLike() - 1);
                                postViewModel.delete(interaction);
                                break;
                            }
                            //Hvis vi liker en post, vi har disliked
                            else if(interaction.isDisliked()){
                                post.post.setLike(post.post.getLike() + 1);
                                post.post.setDislike(post.post.getDislike() - 1);
                                interaction.setDisliked(false);
                                interaction.setLiked(true);
                                postViewModel.update(interaction);
                                break;
                            }
                        }
                    }

                    if(!interaction_found){
                        postViewModel.insert(new PostInteraction("123",post.post.getId(),true,false));
                        post.post.setLike(post.post.getLike() + 1);
                    }
                }
                else{
                    boolean interaction_found = false;
                    for (PostInteraction interaction:post.postInteractions) {
                        if(interaction.getMobileId().equals(mobileid)){
                            interaction_found = true;
                            //Hvis vi disliker en post, vi allerede har liked
                            if(interaction.isLiked()){
                                post.post.setLike(post.post.getLike() - 1);
                                post.post.setDislike(post.post.getDislike() + 1);
                                interaction.setLiked(false);
                                interaction.setDisliked(true);
                                postViewModel.update(interaction);
                                break;
                            }
                            //Hvis vi disliker en post, vi har disliked
                            else if(interaction.isDisliked()){
                                post.post.setDislike(post.post.getDislike() - 1);
                                postViewModel.delete(interaction);
                                break;
                            }
                        }
                    }

                    if(!interaction_found){
                        postViewModel.insert(new PostInteraction("123",post.post.getId(),false,true));
                        post.post.setDislike(post.post.getDislike() + 1);
                    }
                }

                Post newpost = new Post(post.post.getLocation(), post.post.getCategory(), post.post.getDescription(), post.post.getLike(), post.post.getDislike(),post.post.getMobileId());
                newpost.setId(post.post.getId());
                postViewModel.update(newpost);


                postAdapter.notifyDataSetChanged();

            }
        });
    }

    public void changeToAddPostView(View view){
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivity(intent);
    }
}
