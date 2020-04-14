package com.example.aalboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddPostActivity extends AppCompatActivity {

    private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
    }

    public void addPostToFeed(View v){
        EditText location = (EditText)findViewById(R.id.text_view_insert_location);
        EditText category = (EditText)findViewById(R.id.text_view_insert_category);
        EditText description = (EditText)findViewById(R.id.text_view_insert_description);

        Post post = new Post(String.valueOf(location.getText()), String.valueOf(category.getText()), String.valueOf(description.getText()),0,0,"123");
        postViewModel.insert(post);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
