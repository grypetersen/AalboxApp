package com.example.aalboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.*;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddPostActivity extends AppCompatActivity {
    private PostViewModel postViewModel;
    private static final int CAMERA_REQUEST = 1888;
    private static String randomString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        randomString = getRandomString();

        // Change the location randomly
        TextView locationText = findViewById(R.id.locationText);
        locationText.setText(randomString);
    }

    static String getRandomString() {
        int r = (int) (Math.random() * 8);

        return new String[]{
                "Nytorv",
                "Budolfi Plads",
                "Eternitten",
                "AAU Campus",
                "Cassiopeia",
                "Østre Anlæg",
                "Musikkens Hus",
                "Jomfru Ane Gade"}[r];
    }


    public void onCategoryClicked(View view) {
        final ImageButton cultureBtn = (ImageButton) findViewById(R.id.cultureBtn);
        final ImageButton historyBtn = (ImageButton) findViewById(R.id.historyBtn);
        final ImageButton activityBtn = (ImageButton) findViewById(R.id.activitiesBtn);
        final ImageButton languageBtn = (ImageButton) findViewById(R.id.languagesBtn);
        final ImageButton normsBtn = (ImageButton) findViewById(R.id.normsBtn);
        final ImageButton foodBtn = (ImageButton) findViewById(R.id.foodBtn);
        final TextView category = (TextView) findViewById(R.id.CategoryText);
        final ImageView categoryImg = (ImageView) findViewById(R.id.category_icon);


        cultureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Culture", "The Culture button was clicked");
                LinearLayout ln = (LinearLayout) findViewById(R.id.topbarCategory);
                ln.setBackgroundColor(getResources().getColor(R.color.colorRed));
                category.setText(R.string.culture);
                categoryImg.setImageResource(R.drawable.ic_culture_icon);
                cultureBtn.setBackground(getDrawable(R.drawable.ic_culture_btn));
                historyBtn.setBackground(getDrawable(R.drawable.ic_history_btn_inactive));
                activityBtn.setBackground(getDrawable(R.drawable.ic_activities_btn_inactive));
                languageBtn.setBackground(getDrawable(R.drawable.ic_language_btn_inactive));
                normsBtn.setBackground(getDrawable(R.drawable.ic_norms_btn_inactive));
                foodBtn.setBackground(getDrawable(R.drawable.ic_food_btn_inactive));
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("History", "The History button was clicked");
                LinearLayout ln = (LinearLayout) findViewById(R.id.topbarCategory);
                ln.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                category.setText(R.string.history);
                categoryImg.setImageResource(R.drawable.ic_history_icon);
                cultureBtn.setBackground(getDrawable(R.drawable.ic_culture_btn_inactive));
                historyBtn.setBackground(getDrawable(R.drawable.ic_history_btn));
                activityBtn.setBackground(getDrawable(R.drawable.ic_activities_btn_inactive));
                languageBtn.setBackground(getDrawable(R.drawable.ic_language_btn_inactive));
                normsBtn.setBackground(getDrawable(R.drawable.ic_norms_btn_inactive));
                foodBtn.setBackground(getDrawable(R.drawable.ic_food_btn_inactive));
            }
        });

        activityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Activities", "The Activities button was clicked");
                LinearLayout ln = findViewById(R.id.topbarCategory);
                ln.setBackgroundColor(getResources().getColor(R.color.colorPink));
                category.setText(R.string.activities);
                categoryImg.setImageResource(R.drawable.ic_activites_icon);
                cultureBtn.setBackground(getDrawable(R.drawable.ic_culture_btn_inactive));
                historyBtn.setBackground(getDrawable(R.drawable.ic_history_btn_inactive));
                activityBtn.setBackground(getDrawable(R.drawable.ic_activities_btn));
                languageBtn.setBackground(getDrawable(R.drawable.ic_language_btn_inactive));
                normsBtn.setBackground(getDrawable(R.drawable.ic_norms_btn_inactive));
                foodBtn.setBackground(getDrawable(R.drawable.ic_food_btn_inactive));
            }
        });

        languageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Languages", "The Languages button was clicked");
                LinearLayout ln = (LinearLayout) findViewById(R.id.topbarCategory);
                ln.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                category.setText(R.string.language);
                categoryImg.setImageResource(R.drawable.ic_languages_icon);
                cultureBtn.setBackground(getDrawable(R.drawable.ic_culture_btn_inactive));
                historyBtn.setBackground(getDrawable(R.drawable.ic_history_btn_inactive));
                activityBtn.setBackground(getDrawable(R.drawable.ic_activities_btn_inactive));
                languageBtn.setBackground(getDrawable(R.drawable.ic_language_btn));
                normsBtn.setBackground(getDrawable(R.drawable.ic_norms_btn_inactive));
                foodBtn.setBackground(getDrawable(R.drawable.ic_food_btn_inactive));
            }
        });

        normsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Norms", "The Norms button was clicked");
                LinearLayout ln = (LinearLayout) findViewById(R.id.topbarCategory);
                ln.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                category.setText(R.string.norms);
                categoryImg.setImageResource(R.drawable.ic_norms_icon);
                cultureBtn.setBackground(getDrawable(R.drawable.ic_culture_btn_inactive));
                historyBtn.setBackground(getDrawable(R.drawable.ic_history_btn_inactive));
                activityBtn.setBackground(getDrawable(R.drawable.ic_activities_btn_inactive));
                languageBtn.setBackground(getDrawable(R.drawable.ic_language_btn_inactive));
                normsBtn.setBackground(getDrawable(R.drawable.ic_norms_btn));
                foodBtn.setBackground(getDrawable(R.drawable.ic_food_btn_inactive));
            }
        });

        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Food", "The Food button was clicked");
                LinearLayout ln = (LinearLayout) findViewById(R.id.topbarCategory);
                ln.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                category.setText(R.string.food);
                categoryImg.setImageResource(R.drawable.ic_food_icon);
                cultureBtn.setBackground(getDrawable(R.drawable.ic_culture_btn_inactive));
                historyBtn.setBackground(getDrawable(R.drawable.ic_history_btn_inactive));
                activityBtn.setBackground(getDrawable(R.drawable.ic_activities_btn_inactive));
                languageBtn.setBackground(getDrawable(R.drawable.ic_language_btn_inactive));
                normsBtn.setBackground(getDrawable(R.drawable.ic_norms_btn_inactive));
                foodBtn.setBackground(getDrawable(R.drawable.ic_food_btn));
            }
        });
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

    public void startCamera(View view) {
        Intent myCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(myCameraIntent, CAMERA_REQUEST);
    }


    public void addPostToFeed(View v) {
        final TextView category = findViewById(R.id.CategoryText);
        String categoryString;

        if (category.getText().equals("Culture")) {
            Log.i("Category", "The category was set to 'Culture'");
            categoryString = "Culture";
        } else if (category.getText().equals("History")) {
            Log.i("History", "The category was set to 'History'");
            categoryString = "History";
        } else if (category.getText().equals("Activities")) {
            Log.i("Activities", "The category was set to 'Activities'");
            categoryString = "Activities";
        } else if (category.getText().equals("Language")) {
            Log.i("Language", "The category was set to 'Language'");
            categoryString = "Language";
        } else if (category.getText().equals("Norms")) {
            Log.i("Norms", "The category was set to 'Norms'");
            categoryString = "Norms";
        } else if (category.getText().equals("Food")) {
            Log.i("Food", "The category was set to 'Food'");
            categoryString = "Food";
        } else categoryString = "None";

        //final String categoryString = String.valueOf(getCategoryString());
        EditText description = findViewById(R.id.text_view_insert_description);

        Post post = new Post(randomString, categoryString, description.getText().toString(), 0, 0, "123");
        postViewModel.insert(post);

        Toast toast = Toast.makeText(AddPostActivity.this,"Your post is now added to feed", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 520);
        View view = toast.getView();
        view.setBackgroundColor(Color.parseColor("#030C5B"));
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        toast.show();

        finish();
    }

}
