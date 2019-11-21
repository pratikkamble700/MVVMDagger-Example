package com.pratik.shweta.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.pratik.shweta.model.userAPI.UserResponseParser;
import com.pratik.shweta.network.auth.AuthAPI;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    private AuthAPI authAPI;

    private MediatorLiveData<UserResponseParser> authUser = new MediatorLiveData();

    @Inject
    public AuthViewModel(AuthAPI authAPI) {
        this.authAPI = authAPI;
        Log.d(TAG, "AuthViewModel: View model working");

        //API CALL USING RX
       /* authAPI.getUsers(1)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<UserResponseParser>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserResponseParser userResponseParser) {
                        Log.d(TAG, "onNext: " + userResponseParser.getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }

    public void authenticateWithId(int id) {
        final LiveData<UserResponseParser> source = LiveDataReactiveStreams.fromPublisher(
                authAPI.getUsers(id)
                        .subscribeOn(Schedulers.io())
        );

        authUser.addSource(source, new Observer<UserResponseParser>() {
            @Override
            public void onChanged(UserResponseParser userResponseParser) {
                authUser.setValue(userResponseParser);
                authUser.removeSource(source);
            }
        });
    }

    public LiveData<UserResponseParser> obserUser() {
        return authUser;
    }
}
