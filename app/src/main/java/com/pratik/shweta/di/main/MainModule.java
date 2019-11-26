package com.pratik.shweta.di.main;

import com.pratik.shweta.network.main.MainAPI;
import com.pratik.shweta.ui.main.posts.PostsRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static PostsRecyclerAdapter providePostsRecyclerViewAdapter() {
        return new PostsRecyclerAdapter();
    }

    @Provides
    static MainAPI provideMainAPI(Retrofit retrofit) {
        return retrofit.create(MainAPI.class);
    }
}
