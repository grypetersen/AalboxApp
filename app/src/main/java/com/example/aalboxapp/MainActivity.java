package com.example.aalboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import static com.example.aalboxapp.ApplicationClass.categories;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    private PostViewModel postViewModel;
    private String currentTab = "ALL";
    private final List<PostWithInteractions> postWithInteractions = null;

    private Hashtable<String, Boolean> categoryStates = new Hashtable<String, Boolean>() {{put("Historie", true); put("Mad", true); put("Sprog", true); put("Kultur", true); put("Normer", true); put("Aktiviteter", true);}};

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

        SharedPreferences myPrefsFile = getApplicationContext().getSharedPreferences("MyPrefsFile", Activity.MODE_PRIVATE);
        categoryStates.replace("Historie", myPrefsFile.getBoolean("Historie", true));
        categoryStates.replace("Kultur", myPrefsFile.getBoolean("Kultur", true));
        categoryStates.replace("Normer", myPrefsFile.getBoolean("Normer", true));
        categoryStates.replace("Aktiviteter", myPrefsFile.getBoolean("Aktiviteter", true));
        categoryStates.replace("Sprog", myPrefsFile.getBoolean("Sprog", true));
        categoryStates.replace("Mad", myPrefsFile.getBoolean("Mad", true));

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);



        RecyclerView recyclerView = findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final PostAdapter postAdapter = new PostAdapter();
        recyclerView.setAdapter(postAdapter);

        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);

        //DETTE HER SKAL BRUGE TIL AT FILTERE I TABS
        List<String> categoryStrings = new ArrayList<>();
        for (String key:categoryStates.keySet()) {
            if(categoryStates.get(key)){
                categoryStrings.add(key);
            }
        }
        postViewModel.setFilterlivedata(new PostFilter(categoryStrings,currentTab));

        //HERNED TIL ER DET BARE LÆKKERT

        View topNavigation =  findViewById(R.id.top_navigation);
        BottomNavigationItemView allBtn = (BottomNavigationItemView)topNavigation.findViewById(R.id.navigation_all);
        BottomNavigationItemView likedBtn = (BottomNavigationItemView)topNavigation.findViewById(R.id.navigation_liked);
        BottomNavigationItemView yourpostsBtn = (BottomNavigationItemView)topNavigation.findViewById(R.id.navigation_yourposts);

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> categoryStrings = new ArrayList<>();
                for (String key:categoryStates.keySet()) {
                    if(categoryStates.get(key)){
                        categoryStrings.add(key);
                    }
                }
                currentTab = "ALL";
                postViewModel.setFilterlivedata(new PostFilter(categoryStrings,currentTab));
            }
        });

        likedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> categoryStrings = new ArrayList<>();
                for (String key:categoryStates.keySet()) {
                    if(categoryStates.get(key)){
                        categoryStrings.add(key);
                    }
                }
                currentTab = "MY_LIKED_POSTS";
                postViewModel.setFilterlivedata(new PostFilter(categoryStrings,currentTab));
            }
        });

        yourpostsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> categoryStrings = new ArrayList<>();
                for (String key:categoryStates.keySet()) {
                    if(categoryStates.get(key)){
                        categoryStrings.add(key);
                    }
                }
                currentTab = "MY_POSTS";
                postViewModel.setFilterlivedata(new PostFilter(categoryStrings,currentTab));
            }
        });

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
        boolean focusable = false; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.RIGHT, 60, -80);

        final ImageButton historyBtn = (ImageButton)popupView.findViewById(R.id.historyBtn);
        final ImageButton normBtn = (ImageButton)popupView.findViewById(R.id.normBtn);
        final ImageButton cultureBtn = (ImageButton)popupView.findViewById(R.id.cultureBtn);
        final ImageButton activityBtn = (ImageButton)popupView.findViewById(R.id.activityBtn);
        final ImageButton foodBtn = (ImageButton)popupView.findViewById(R.id.foodBtn);
        final ImageButton langBtn = (ImageButton)popupView.findViewById(R.id.langBtn);
        Button saveBtn = (Button)popupView.findViewById(R.id.saveFilterBtn);

        handleCategoryColor("Historie", historyBtn);
        handleCategoryColor("Kultur", cultureBtn);
        handleCategoryColor("Normer", normBtn);
        handleCategoryColor("Aktiviteter", activityBtn);
        handleCategoryColor("Mad", foodBtn);
        handleCategoryColor("Sprog", langBtn);

        historyBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                handleCategoryClicked("Historie", historyBtn);

            }
        });

        normBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                handleCategoryClicked("Normer", normBtn);
            }
        });

        cultureBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                handleCategoryClicked("Kultur", cultureBtn);
            }
        });

        activityBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                handleCategoryClicked("Aktiviteter", activityBtn);
            }
        });

        foodBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                handleCategoryClicked("Mad", foodBtn);
            }
        });

        langBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                handleCategoryClicked("Sprog", langBtn);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SharedPreferences myPrefsFile = getApplicationContext().getSharedPreferences("MyPrefsFile", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefsFile.edit();
                editor.putBoolean("Historie", categoryStates.get("Historie"));
                editor.putBoolean("Normer", categoryStates.get("Normer"));
                editor.putBoolean("Aktiviteter", categoryStates.get("Aktiviteter"));
                editor.putBoolean("Mad", categoryStates.get("Mad"));
                editor.putBoolean("Sprog", categoryStates.get("Sprog"));
                editor.putBoolean("Kultur", categoryStates.get("Kultur"));
                editor.apply();

                List<String> categoryStrings = new ArrayList<>();
                for (String key:categoryStates.keySet()) {
                    if(categoryStates.get(key)){
                        categoryStrings.add(key);
                    }
                }
                postViewModel.setFilterlivedata(new PostFilter(categoryStrings,currentTab));
                popupWindow.dismiss();
            }
        });


    }
    public void changeToAddPostView(View view){
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivity(intent);
    }


    public void handleCategoryClicked(String category, ImageButton button){
        categoryStates.replace(category, !categoryStates.get(category));
        handleCategoryColor(category, button);

    }

    public void handleCategoryColor(String category, ImageButton button){
        if (categoryStates.get(category) == true){
            button.setBackgroundColor(Color.parseColor(categories.get(category)));
        } else {
            String tempColor = categories.get(category);
            tempColor = tempColor.substring(0,1) + "60" + tempColor.substring(1,tempColor.length());
            button.setBackgroundColor(Color.parseColor(tempColor));
        }

    }


}
