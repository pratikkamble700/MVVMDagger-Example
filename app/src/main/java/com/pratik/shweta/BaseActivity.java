package com.pratik.shweta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.pratik.shweta.model.userAPI.UserResponseParser;
import com.pratik.shweta.ui.auth.AuthActivity;
import com.pratik.shweta.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subScribeObserver();
    }

    private void subScribeObserver() {
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<UserResponseParser>>() {
            @Override
            public void onChanged(AuthResource<UserResponseParser> userResponseParserAuthResource) {
                if (userResponseParserAuthResource != null) {
                    switch (userResponseParserAuthResource.status) {
                        case AUTHENTICATED:
                            Log.d(TAG, "onChanged: LOGIN SUCCESS " + userResponseParserAuthResource.data.getName());
                            Toast.makeText(BaseActivity.this, userResponseParserAuthResource.data.getName(), Toast.LENGTH_LONG).show();
                            break;
                        case LOADING:
                            break;
                        case NOT_AUTHENTICATED:
                            navToAuhActivity();
                            break;
                        case ERROR:
                            break;
                    }
                }
            }
        });
    }

    private void navToAuhActivity() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
