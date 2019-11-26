package com.pratik.shweta.di.main;

import com.pratik.shweta.ui.main.posts.PostsFragment;
import com.pratik.shweta.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragment contributePostsFragment();
}
