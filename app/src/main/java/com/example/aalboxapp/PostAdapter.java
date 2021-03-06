package com.example.aalboxapp;

import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.aalboxapp.ApplicationClass.categories;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private List<PostWithInteractions> postWithInteractions = new ArrayList<>();
    public OnItemClickListener clickListener;
    public boolean showBtn = false;

    public void setPosts(List<PostWithInteractions> postWithInteractions){
        this.postWithInteractions = postWithInteractions;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener{
        void onItemClicked(PostWithInteractions post, int actionBtnId);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public void showDeleteBtn(boolean show){
        this.showBtn = show;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        PostWithInteractions currentPost = postWithInteractions.get(position);
        holder.textViewLocation.setText(currentPost.post.getLocation());
        holder.textViewCategory.setText(currentPost.post.getCategory());
        holder.textViewDescription.setText(currentPost.post.getDescription());
        holder.textViewLike.setText(String.valueOf(currentPost.post.getLike()));
        holder.textViewDislike.setText(String.valueOf(currentPost.post.getDislike()));

        LinearLayout linearLayout = holder.itemView.findViewById(R.id.linearLayoutColor);
        PostWithInteractions post = postWithInteractions.get(position);
        ImageView image = linearLayout.findViewById(R.id.category_icon_postview);
        ImageButton deleteBtn = holder.itemView.findViewById(R.id.deletePostBtn);

        if (this.showBtn){
            deleteBtn.setVisibility(View.VISIBLE);
        } else{
            deleteBtn.setVisibility(View.INVISIBLE);
        }

        switch (post.post.getCategory()){
            case "History":
                linearLayout.setBackgroundColor(Color.parseColor(categories.get("History")));
                image.setImageDrawable(linearLayout.getContext().getDrawable(R.drawable.ic_history_icon));
                break;
            case "Food":
                linearLayout.setBackgroundColor(Color.parseColor(categories.get("Food")));
                image.setImageDrawable(linearLayout.getContext().getDrawable(R.drawable.ic_food_icon));
                break;
            case "Language":
                linearLayout.setBackgroundColor(Color.parseColor(categories.get("Language")));
                image.setImageDrawable(linearLayout.getContext().getDrawable(R.drawable.ic_languages_icon));
                break;
            case "Activities":
                linearLayout.setBackgroundColor(Color.parseColor(categories.get("Activities")));
                image.setImageDrawable(linearLayout.getContext().getDrawable(R.drawable.ic_activites_icon));
                break;
            case "Norms":
                linearLayout.setBackgroundColor(Color.parseColor(categories.get("Norms")));
                image.setImageDrawable(linearLayout.getContext().getDrawable(R.drawable.ic_norms_icon));
                break;
            case "Culture":
                linearLayout.setBackgroundColor(Color.parseColor(categories.get("Culture")));
                image.setImageDrawable(linearLayout.getContext().getDrawable(R.drawable.ic_culture_icon));
                break;
            case "None":
                linearLayout.setBackgroundColor(Color.parseColor(categories.get("None")));
                image.setImageDrawable(linearLayout.getContext().getDrawable(R.drawable.ic_category));
                break;
            default:break;
        }


    }

    @Override
    public int getItemCount() {
        return postWithInteractions.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayoutColor;
        private TextView textViewLocation;
        private TextView textViewCategory;
        private TextView textViewDescription;
        private TextView textViewLike;
        private TextView textViewDislike;
        private ImageButton btnLike;
        private ImageButton btnDislike;
        private ImageButton btnDelete;


        public PostHolder(@NonNull View itemView) {
            super(itemView);
            textViewLocation = itemView.findViewById(R.id.text_view_location);
            textViewCategory = itemView.findViewById(R.id.text_view_category);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewLike = itemView.findViewById(R.id.text_view_like);
            textViewDislike = itemView.findViewById(R.id.text_view_dislike);
            btnLike = itemView.findViewById(R.id.btn_like);
            btnDislike = itemView.findViewById(R.id.btn_dislike);
            btnDelete = itemView.findViewById(R.id.deletePostBtn);


            btnDislike.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (clickListener != null && position!=RecyclerView.NO_POSITION){
                        PostWithInteractions post = postWithInteractions.get(position);
                        clickListener.onItemClicked(post, 2);

                    }

                }
            });

            btnLike.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (clickListener != null && position!=RecyclerView.NO_POSITION){
                        PostWithInteractions post = postWithInteractions.get(position);
                        clickListener.onItemClicked(post, 1);
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (clickListener != null && position!=RecyclerView.NO_POSITION){
                        PostWithInteractions post = postWithInteractions.get(position);
                        clickListener.onItemClicked(post, 3);
                    }

                }
            });


        }
    }
}
