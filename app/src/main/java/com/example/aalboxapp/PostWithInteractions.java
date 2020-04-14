package com.example.aalboxapp;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PostWithInteractions {

    @Embedded
    public Post post;
    @Relation(
            parentColumn = "id",
            entityColumn = "post_id"
    )

    public List<PostInteraction> postInteractions;

}
