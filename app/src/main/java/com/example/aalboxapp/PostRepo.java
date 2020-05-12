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

    public LiveData<List<PostWithInteractions>> getPostWithInteractions(PostFilter filter){
        if(filter.getSelectedTab().equals("ALL")){
            return postDao.getPostsWithInteractions(filter.getSelectedCategories());
        }
        else if(filter.getSelectedTab().equals("MY_POSTS")){
            return postDao.getMyPostsWithInteractions(filter.getSelectedCategories(), "123");
        }
        else if(filter.getSelectedTab().equals("MY_LIKED_POSTS")) {
            return postDao.getMyLikedPostsWithInteractions(filter.getSelectedCategories(), "123");
        }
        else throw new IllegalArgumentException("SelectedTab is not support " + filter.getSelectedTab().toString());
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


    public void delete(Post post){new DeletePostAsync(postDao).execute(post);}
    private static class DeletePostAsync extends AsyncTask<Post,Void,Void> {
        private PostDAO postDAO;
        private DeletePostAsync(PostDAO postDAO){ this.postDAO = postDAO; }

        @Override
        protected Void doInBackground(Post... post) {
            postDAO.deletePost(post[0]);
            postDAO.deletePostInteractions(post[0].getId());
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
