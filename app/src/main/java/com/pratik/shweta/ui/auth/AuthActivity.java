package com.pratik.shweta.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.pratik.shweta.R;
import com.pratik.shweta.model.userAPI.UserResponseParser;
import com.pratik.shweta.ui.main.MainActivity;
import com.pratik.shweta.viewmodels.ViewModelsProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthActivity";

    private AuthViewModel viewModel;

    private ProgressBar progressBar;

    @Inject
    ViewModelsProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    EditText userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        userID = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.login_button).setOnClickListener(this);
        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);
        setLogo();
        subScribeObserver();
    }

    private void subScribeObserver() {
       /* viewModel.obserUser().observe(this, new Observer<UserResponseParser>() {
            @Override
            public void onChanged(UserResponseParser userResponseParser) {
                if(userResponseParser != null){
                    Log.d(TAG, "onChanged: "+userResponseParser.getName());
                    Toast.makeText(AuthActivity.this,userResponseParser.getName(),Toast.LENGTH_LONG).show();
                }
            }
        });*/
        viewModel.obserSessionState().observe(this, new Observer<AuthResource<UserResponseParser>>() {
            @Override
            public void onChanged(AuthResource<UserResponseParser> userResponseParserAuthResource) {
                if (userResponseParserAuthResource != null) {
                    switch (userResponseParserAuthResource.status) {
                        case AUTHENTICATED:
                            showProgressBar(false);
                            Log.d(TAG, "onChanged: LOGIN SUCCESS " + userResponseParserAuthResource.data.getName());
//                            Toast.makeText(AuthActivity.this,userResponseParserAuthResource.data.getName(),Toast.LENGTH_LONG).show();
                            onLoginSuccess();
                            break;
                        case LOADING:
                            showProgressBar(true);
                            break;
                        case NOT_AUTHENTICATED:
                            showProgressBar(false);
                            Log.d(TAG, "onChanged: LOGIN FAILED : " + userResponseParserAuthResource.message);
                            Toast.makeText(AuthActivity.this, userResponseParserAuthResource.message, Toast.LENGTH_LONG).show();
                            break;
                        case ERROR:
                            showProgressBar(false);
                            break;
                    }
                }
            }
        });
    }

    private void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

    public void setLogo() {
        requestManager
                .load(logo)
                .into((ImageView) findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                attemptLogin();
                break;
        }
    }

    private void attemptLogin() {
        if (!TextUtils.isEmpty(userID.getText().toString().trim())) {
            viewModel.authenticateWithId(Integer.parseInt(userID.getText().toString().trim()));
        }
    }
}
