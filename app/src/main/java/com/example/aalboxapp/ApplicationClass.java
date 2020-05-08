package com.example.aalboxapp;

import android.app.Application;
import java.util.Hashtable;

public class ApplicationClass extends Application {

    //Hashtable including all categories as keys, with color as values
    public static Hashtable<String, String> categories;

    @Override
    public void onCreate() {
        super.onCreate();

        //Change to real colors later
        categories = new Hashtable<String, String>();
        categories.put("History", "#FCD445");
        categories.put("Food", "#FC9D54");
        categories.put("Language", "#5298FA");
        categories.put("Culture", "#F94949");
        categories.put("Norms", "#6EE07F");
        categories.put("Activities", "#FD94FE");
        categories.put("default", "#000000");
        categories.put("None", "#C6C6C6");



    }
}
