package com.pratik.shweta.network.auth;

import com.pratik.shweta.model.userAPI.UserResponseParser;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthAPI {

    @GET("users/{id}")
    Flowable<UserResponseParser> getUsers(@Path("id") int id);
}
