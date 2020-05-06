package com.example.aalboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import static com.example.aalboxapp.ApplicationClass.categories;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Information";
    BottomNavigationView bottomNavigation;
    private PostViewModel postViewModel;
    private String currentTab = "ALL";
    private final List<PostWithInteractions> postWithInteractions = null;

    private Hashtable<String, Boolean> categoryStates = new Hashtable<String, Boolean>() {{put("History", true); put("Food", true); put("Language", true); put("Culture", true); put("Norms", true); put("Activities", true);}};

    // Creates NFC-button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Handles NFC-button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nfcBtn) {
            Log.i(TAG, "NFC Button Clicked!");

            Intent intent = new Intent(this, NFCActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
/*
    Ved ikke, hvad det her er?
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

 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences myPrefsFile = getApplicationContext().getSharedPreferences("MyPrefsFile", Activity.MODE_PRIVATE);
        categoryStates.replace("History", myPrefsFile.getBoolean("History", true));
        categoryStates.replace("Culture", myPrefsFile.getBoolean("Culture", true));
        categoryStates.replace("Norms", myPrefsFile.getBoolean("Norms", true));
        categoryStates.replace("Activities", myPrefsFile.getBoolean("Activities", true));
        categoryStates.replace("Language", myPrefsFile.getBoolean("Language", true));
        categoryStates.replace("Food", myPrefsFile.getBoolean("Food", true));

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        RecyclerView recyclerView = findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final PostAdapter postAdapter = new PostAdapter();
        recyclerView.setAdapter(postAdapter);

        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);


        List<String> categoryStrings = new ArrayList<>();
        for (String key:categoryStates.keySet()) {
            if(categoryStates.get(key)){
                categoryStrings.add(key);
            }
        }
        postViewModel.setFilterlivedata(new PostFilter(categoryStrings,currentTab));

        View topNavigation =  findViewById(R.id.top_navigation);
        final BottomNavigationItemView allBtn = (BottomNavigationItemView)topNavigation.findViewById(R.id.navigation_all);
        final BottomNavigationItemView likedBtn = (BottomNavigationItemView)topNavigation.findViewById(R.id.navigation_liked);
        final BottomNavigationItemView yourpostsBtn = (BottomNavigationItemView)topNavigation.findViewById(R.id.navigation_yourposts);
        final TextView yourLikeTxtView = (TextView)findViewById(R.id.yourliketxtview);

        // TO MAKE THE ITEM SEEM ACTIVE
        allBtn.setBackgroundColor(Color.parseColor("#02083C"));
        allBtn.setPadding(0,0,0,5);
        likedBtn.setBackgroundColor(Color.parseColor("#030C5B"));
        likedBtn.setPadding(0,0,0,0);
        yourpostsBtn.setBackgroundColor(Color.parseColor("#030C5B"));
        yourpostsBtn.setPadding(0,0,0,0);

        allBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                Log.i("TopNavigation", "The 'All'-Button clicked!");
                allBtn.setBackgroundColor(Color.parseColor("#02083C"));
                allBtn.setPadding(0,0,0,5);
                likedBtn.setBackgroundColor(Color.parseColor("#030C5B"));
                likedBtn.setPadding(0,0,0,0);
                yourpostsBtn.setBackgroundColor(Color.parseColor("#030C5B"));
                yourpostsBtn.setPadding(0,0,0,0);
                overridePendingTransition(0,0);

                List<String> categoryStrings = new ArrayList<>();
                for (String key:categoryStates.keySet()) {
                    if(categoryStates.get(key)){
                        categoryStrings.add(key);
                    }
                }
                currentTab = "ALL";
                postViewModel.setFilterlivedata(new PostFilter(categoryStrings,currentTab));
                yourLikeTxtView.setVisibility(View.INVISIBLE);

            }
        });

        likedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TopNavigation", "The 'Liked'-Button clicked!");
                allBtn.setBackgroundColor(Color.parseColor("#030C5B"));
                allBtn.setPadding(0,0,0,0);
                likedBtn.setBackgroundColor(Color.parseColor("#02083C"));
                likedBtn.setPadding(0,0,0,5);
                yourpostsBtn.setBackgroundColor(Color.parseColor("#030C5B"));
                yourpostsBtn.setPadding(0,0,0,0);
                overridePendingTransition(0,0);

                List<String> categoryStrings = new ArrayList<>();
                for (String key:categoryStates.keySet()) {
                    if(categoryStates.get(key)){
                        categoryStrings.add(key);
                    }
                }
                currentTab = "MY_LIKED_POSTS";
                postViewModel.setFilterlivedata(new PostFilter(categoryStrings,currentTab));
                yourLikeTxtView.setVisibility(View.INVISIBLE);
            }
        });

        yourpostsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TopNavigation", "The 'Your Posts'-Button clicked!");
                allBtn.setBackgroundColor(Color.parseColor("#030C5B"));
                allBtn.setPadding(0,0,0,0);
                likedBtn.setBackgroundColor(Color.parseColor("#030C5B"));
                likedBtn.setPadding(0,0,0,0);
                yourpostsBtn.setBackgroundColor(Color.parseColor("#02083C"));
                yourpostsBtn.setPadding(0,0,0,5);
                overridePendingTransition(0,0);

                List<String> categoryStrings = new ArrayList<>();
                for (String key:categoryStates.keySet()) {
                    if(categoryStates.get(key)){
                        categoryStrings.add(key);
                    }
                }
                currentTab = "MY_POSTS";
                postViewModel.setFilterlivedata(new PostFilter(categoryStrings,currentTab));
                yourLikeTxtView.setVisibility(View.VISIBLE);

            }
        });

        postViewModel.getPostWithInteractionLiveData().observe(this, new Observer<List<PostWithInteractions>>() {
            @Override
            public void onChanged(List<PostWithInteractions> postWithInteractions) {
                postAdapter.setPosts(postWithInteractions);

                int count = 0;
                for (PostWithInteractions post:postWithInteractions) {
                    count += post.post.getLike();
                }
                yourLikeTxtView.setText("Your likes: " + String.valueOf(count));
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
                            overridePendingTransition(0,0);
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            return true;
                        case R.id.navigation_map:
                            Log.i("Map", "Opens the map.");
                            overridePendingTransition(0,0);
                            startActivity(new Intent(MainActivity.this, MapFragment.class));
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

        popupWindow.showAtLocation(view, Gravity.RIGHT, 22, -118);

        final ImageButton historyBtn = (ImageButton)popupView.findViewById(R.id.historyBtn);
        final ImageButton normBtn = (ImageButton)popupView.findViewById(R.id.normBtn);
        final ImageButton cultureBtn = (ImageButton)popupView.findViewById(R.id.cultureBtn);
        final ImageButton activityBtn = (ImageButton)popupView.findViewById(R.id.activityBtn);
        final ImageButton foodBtn = (ImageButton)popupView.findViewById(R.id.foodBtn);
        final ImageButton langBtn = (ImageButton)popupView.findViewById(R.id.langBtn);
        Button saveBtn = (Button)popupView.findViewById(R.id.saveFilterBtn);

        handleCategoryColor("History", historyBtn);
        handleCategoryColor("Culture", cultureBtn);
        handleCategoryColor("Norms", normBtn);
        handleCategoryColor("Activities", activityBtn);
        handleCategoryColor("Food", foodBtn);
        handleCategoryColor("Language", langBtn);

        historyBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                handleCategoryClicked("History", historyBtn);

            }
        });

        normBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                handleCategoryClicked("Norms", normBtn);
            }
        });

        cultureBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                handleCategoryClicked("Culture", cultureBtn);
            }
        });

        activityBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                handleCategoryClicked("Activities", activityBtn);
            }
        });

        foodBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                handleCategoryClicked("Food", foodBtn);
            }
        });

        langBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                handleCategoryClicked("Language", langBtn);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SharedPreferences myPrefsFile = getApplicationContext().getSharedPreferences("MyPrefsFile", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefsFile.edit();
                editor.putBoolean("History", categoryStates.get("History"));
                editor.putBoolean("Norms", categoryStates.get("Norms"));
                editor.putBoolean("Activities", categoryStates.get("Activities"));
                editor.putBoolean("Food", categoryStates.get("Food"));
                editor.putBoolean("Language", categoryStates.get("Language"));
                editor.putBoolean("Culture", categoryStates.get("Culture"));
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
