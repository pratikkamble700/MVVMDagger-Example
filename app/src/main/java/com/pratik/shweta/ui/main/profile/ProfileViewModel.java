package com.pratik.shweta.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.pratik.shweta.SessionManager;
import com.pratik.shweta.model.userAPI.UserResponseParser;
import com.pratik.shweta.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";

    // we get Session manager here If we get Session manager from MainActivity then it will break viewModel Artitechture rule
    // for lifecycle awareness & observe Data.
    private final SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        Log.d(TAG, "ProfileViewModel: Is ready to use...");
    }

    public LiveData<AuthResource<UserResponseParser>> getAuthenticatedUser() {
        return sessionManager.getAuthUser();
    }
}
