package com.pratik.shweta.di;

import com.pratik.shweta.di.auth.AuthModule;
import com.pratik.shweta.di.auth.AuthViewModelModule;
import com.pratik.shweta.ui.auth.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(
            modules ={ AuthViewModelModule.class, AuthModule.class}

    )
    abstract AuthActivity contributeAuthActivity();

}
