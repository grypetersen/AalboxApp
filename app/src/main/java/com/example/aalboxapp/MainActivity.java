package com.example.aalboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    private PostViewModel postViewModel;
    private final List<PostWithInteractions> postWithInteractions = null;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "click..!!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

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

    // Bottom navigation. "overridePendingTransition" controls the animation.
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_feed:
                            Log.i("Feed", "The feed opens at home.");
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.navigation_map:
                            Log.i("Map", "Opens the map.");
                            startActivity(new Intent(MainActivity.this, MapActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                    }
                    return false;
                }
            };

    public void onFilterClicked(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.filter, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.RIGHT, 60, -239);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
    public void changeToAddPostView(View view){
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivity(intent);
    }
}
