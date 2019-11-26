package com.pratik.shweta.network.main;

import com.pratik.shweta.model.postAPI.PostResponceParser;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainAPI {

    //TODO : path is denoted by '/' where query denoted by '?' in rest API

    //posts?userId=1
    @GET("posts")
    Flowable<List<PostResponceParser>> getPostsFromUser(
            @Query("userId") int id
    );
}
