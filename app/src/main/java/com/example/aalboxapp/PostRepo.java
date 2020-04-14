package com.example.aalboxapp;


import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PostRepo {

    private PostDAO postDao;

    private LiveData<List<PostWithInteractions>> allPostWithInteractions;

    public PostRepo(Application application){
        PostDatabase database = PostDatabase.getInstance(application);
        postDao = database.postDAO();
        allPostWithInteractions = postDao.getAllPostsWithInteractions();
    }


    //Post
    public void update(Post post){new UpdatePostAsync(postDao).execute(post);}
    private static class UpdatePostAsync extends AsyncTask<Post, Void, Void> {
        private PostDAO postDAO;
        private UpdatePostAsync(PostDAO personDao){
            this.postDAO = personDao;
        }

        @Override
        protected Void doInBackground(Post... post) {
            postDAO.updatePost(post[0]);
            return null;
        }
    }
    public void insert(Post post){
        new InsertPostAsync(postDao).execute(post);
    }
    private static class InsertPostAsync extends AsyncTask<Post, Void, Void> {
        private PostDAO postDAO;
        private InsertPostAsync(PostDAO personDao){
            this.postDAO = personDao;
        }

        @Override
        protected Void doInBackground(Post... post) {
            postDAO.addPost(post[0]);
            return null;
        }
    }

    //Post with interactions
    public LiveData<List<PostWithInteractions>> getAllPostWithInteractions(){
        return allPostWithInteractions;
    }

    public void delete(PostInteraction postInteraction){new DeletePostInteractionAsync(postDao).execute(postInteraction);}
    private static class DeletePostInteractionAsync extends AsyncTask<PostInteraction,Void,Void> {
        private PostDAO postDAO;
        private DeletePostInteractionAsync(PostDAO postDAO){ this.postDAO = postDAO; }

        @Override
        protected Void doInBackground(PostInteraction... postInteractions) {
            postDAO.deletePostInteraction(postInteractions[0]);
            return null;
        }
    }

    public void insert(PostInteraction postInteraction){new InsertLikedPostAsync(postDao).execute(postInteraction);}
    private static class InsertLikedPostAsync extends AsyncTask<PostInteraction, Void, Void> {
        private PostDAO postDAO;
        private InsertLikedPostAsync(PostDAO postDAO){
            this.postDAO = postDAO;
        }

        @Override
        protected Void doInBackground(PostInteraction... postInteractions) {
            postDAO.addPostInteraction(postInteractions[0]);
            return null;
        }
    }
    public void update(PostInteraction postInteraction){new UpdateLikedPostAsync(postDao).execute(postInteraction);}
    private static class UpdateLikedPostAsync extends AsyncTask<PostInteraction, Void, Void> {
        private PostDAO postDAO;
        private UpdateLikedPostAsync(PostDAO personDao){
            this.postDAO = personDao;
        }

        @Override
        protected Void doInBackground(PostInteraction... postInteractions) {
            postDAO.updatePostInteraction(postInteractions[0]);
            return null;
        }
    }







}
