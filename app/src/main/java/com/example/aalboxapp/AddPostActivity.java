package com.example.aalboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class AddPostActivity extends AppCompatActivity {

    private PostViewModel postViewModel;
    private static final int CAMERA_REQUEST = 1888;

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

    public void onLinkClicked(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void startCamera(View view){
        Intent myCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(myCameraIntent, CAMERA_REQUEST);
    }
}
