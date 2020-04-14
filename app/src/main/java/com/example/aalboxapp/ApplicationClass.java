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
        categories.put("Historie", "#gul");
        categories.put("Mad", "#orange");
        categories.put("Sprog", "#blå");
        categories.put("Kultur", "#rød");
        categories.put("Normer", "#grøn");
        categories.put("Aktiviteter", "#pink");

        //How to get the values... for later work
        categories.get("Historie");


    }
}
