package com.example.aalboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NFCAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_f_c_acitivity);
    }

    public void goBackToFeed(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
