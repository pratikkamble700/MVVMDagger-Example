package com.pratik.shweta;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.pratik.shweta.model.userAPI.UserResponseParser;
import com.pratik.shweta.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private static final String TAG = "DaggerExample";

    // data
    private MediatorLiveData<AuthResource<UserResponseParser>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {
    }

    public void authenticateWithId(final LiveData<AuthResource<UserResponseParser>> source) {
        if(cachedUser != null){
            // to showing progressBar
            cachedUser.setValue(AuthResource.loading((UserResponseParser)null));
            cachedUser.addSource(source, new Observer<AuthResource<UserResponseParser>>() {
                @Override
                public void onChanged(AuthResource<UserResponseParser> userAuthResource) {
                    cachedUser.setValue(userAuthResource);
                    cachedUser.removeSource(source);
                    if(userAuthResource.status.equals(AuthResource.AuthStatus.ERROR)){
                        cachedUser.setValue(AuthResource.<UserResponseParser>logout());
                    }
                }
            });
        }
    }

    public void logOut() {
        Log.d(TAG, "logOut: logging out...");
        cachedUser.setValue(AuthResource.<UserResponseParser>logout());
    }


    public LiveData<AuthResource<UserResponseParser>> getAuthUser(){
        return cachedUser;
    }
}
