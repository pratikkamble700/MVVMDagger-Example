package com.pratik.shweta.di.auth;


import androidx.lifecycle.ViewModel;

import com.pratik.shweta.di.ViewModelKey;
import com.pratik.shweta.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
}
