package com.pratik.shweta.di.main;

import com.pratik.shweta.network.main.MainAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static MainAPI provideMainAPI(Retrofit retrofit) {
        return retrofit.create(MainAPI.class);
    }
}
