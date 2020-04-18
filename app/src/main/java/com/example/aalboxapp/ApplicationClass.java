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
        categories.put("Historie", "#FCD445");
        categories.put("Mad", "#FC9D54");
        categories.put("Sprog", "#5298FA");
        categories.put("Kultur", "#F94949");
        categories.put("Normer", "#6EE07F");
        categories.put("Aktiviteter", "#FD94FE");
        categories.put("default", "#000000");



    }
}
