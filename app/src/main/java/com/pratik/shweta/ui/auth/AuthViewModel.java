package com.pratik.shweta.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.pratik.shweta.SessionManager;
import com.pratik.shweta.model.userAPI.UserResponseParser;
import com.pratik.shweta.network.auth.AuthAPI;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    private AuthAPI authAPI;
    private SessionManager sessionManager;

//    private MediatorLiveData<AuthResource<UserResponseParser>> authUser = new MediatorLiveData();

    @Inject
    public AuthViewModel(AuthAPI authAPI, SessionManager sessionManager) {
        this.authAPI = authAPI;
        this.sessionManager = sessionManager;
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
       /* authUser.setValue(AuthResource.loading((UserResponseParser) null));
        final LiveData<AuthResource<UserResponseParser>> source;
        source = LiveDataReactiveStreams.fromPublisher(
                authAPI.getUsers(id)
                        .onErrorReturn(new Function<Throwable, UserResponseParser>() {
                            @Override
                            public UserResponseParser apply(Throwable throwable) throws Exception {
                                UserResponseParser errorUser = new UserResponseParser();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })
                        .map(new Function<UserResponseParser, AuthResource<UserResponseParser>>() {
                            @Override
                            public AuthResource<UserResponseParser> apply(UserResponseParser userResponseParser) throws Exception {
                                if(userResponseParser.getId() == -1){
                                    return AuthResource.error("Could not Authenticated",null);
                                }
                                return AuthResource.authenticated(userResponseParser);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );

        authUser.addSource(source, new Observer<AuthResource<UserResponseParser>>() {
            @Override
            public void onChanged(AuthResource<UserResponseParser> userResponseParser) {
                authUser.setValue(userResponseParser);
                authUser.removeSource(source);
            }
        });*/

       sessionManager.authenticateWithId(queryUserId(id));
    }

    private LiveData<AuthResource<UserResponseParser>> queryUserId(int id){
        return LiveDataReactiveStreams.fromPublisher(
                authAPI.getUsers(id)
                        .onErrorReturn(new Function<Throwable, UserResponseParser>() {
                            @Override
                            public UserResponseParser apply(Throwable throwable) throws Exception {
                                UserResponseParser errorUser = new UserResponseParser();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })
                        .map(new Function<UserResponseParser, AuthResource<UserResponseParser>>() {
                            @Override
                            public AuthResource<UserResponseParser> apply(UserResponseParser userResponseParser) throws Exception {
                                if(userResponseParser.getId() == -1){
                                    return AuthResource.error("Could not Authenticated",null);
                                }
                                return AuthResource.authenticated(userResponseParser);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );
    }

    public LiveData<AuthResource<UserResponseParser>> obserSessionState() {
        return sessionManager.getAuthUser();
    }
}
