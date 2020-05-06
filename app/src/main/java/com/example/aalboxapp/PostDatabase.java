package com.example.aalboxapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Post.class , PostInteraction.class}, version = 1, exportSchema = false)
public abstract class PostDatabase extends RoomDatabase {

    private static PostDatabase instance;
    public abstract PostDAO postDAO();

    public  static synchronized PostDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), PostDatabase.class, "Database").fallbackToDestructiveMigration().addCallback(roomcallback).build();
        }
        return instance;
    }

    private  static  RoomDatabase.Callback roomcallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDB(instance).execute();
        }
    };

    private static class PopulateDB extends AsyncTask<Void, Void, Void> {
        private PostDAO postDAO;

        public PopulateDB(PostDatabase database) {
            this.postDAO = database.postDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            postDAO.addPost(new Post("Nytorv", "Food", "Burgerking er nice", 0,0, "123"));
            postDAO.addPost(new Post("Vejgaard", "Culture", "Vejgaard dollar er 50kr",0,0,"456"));
            postDAO.addPost(new Post("Øgaden", "Culture", "Se den flotte natur",0,0,"123"));
            postDAO.addPost(new Post("Nytorv", "History", "Nytorv var for 100 år siden...",0,0,"123"));
            postDAO.addPost(new Post("Nytorv", "Norms", "Danskere flager altid for at fejre eller mindes om noget. Se bare omkring",0,0,"123"));
            postDAO.addPost(new Post("Vestbyen", "Food", "Prøv ulla terkelsen, det er bare farverigt og god mod",0,0,"123"));
            postDAO.addPost(new Post("Øst", "Norms", "Pas på de stjæler din cykel",0,0,"123"));
            postDAO.addPost(new Post("City syd", "Culture", "Kø kultur er super vigtigt. Vent på det bliver din tur",0,0,"123"));
            postDAO.addPost(new Post("City syd", "Food", "Prøv at spise i bilkas morgenmadsafdeling",0,0,"123"));
            postDAO.addPost(new Post("Øst", "Norms", "Der cykler mange hver dag, pas på du ikke bliver kørt ned",0,0,"123"));

            return null;

        }
    }



}
