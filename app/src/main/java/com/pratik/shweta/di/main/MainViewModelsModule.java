package com.pratik.shweta.di.main;


import androidx.lifecycle.ViewModel;

import com.pratik.shweta.di.ViewModelKey;
import com.pratik.shweta.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);
}
