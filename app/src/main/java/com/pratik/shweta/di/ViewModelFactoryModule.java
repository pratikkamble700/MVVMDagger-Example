package com.pratik.shweta.di;

import androidx.lifecycle.ViewModelProvider;

import com.pratik.shweta.viewmodels.ViewModelsProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelProviderFactory(ViewModelsProviderFactory viewModelsProviderFactory);

    //Same As Above method with bind
   /* @Provides
    static ViewModelProvider.Factory provideViewModelProviderFactory(ViewModelsProviderFactory viewModelsProviderFactory){
        return viewModelsProviderFactory;
    }*/
}
