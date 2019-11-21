package com.pratik.shweta.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.pratik.shweta.R;
import com.pratik.shweta.model.userAPI.UserResponseParser;
import com.pratik.shweta.viewmodels.ViewModelsProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthActivity";

    private AuthViewModel viewModel;

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
        findViewById(R.id.login_button).setOnClickListener(this);

        viewModel = ViewModelProviders.of(this,providerFactory).get(AuthViewModel.class);
        setLogo();
        subScribeObserver();
    }

    private void subScribeObserver(){
        viewModel.obserUser().observe(this, new Observer<UserResponseParser>() {
            @Override
            public void onChanged(UserResponseParser userResponseParser) {
                if(userResponseParser != null){
                    Log.d(TAG, "onChanged: "+userResponseParser.getName());
                    Toast.makeText(AuthActivity.this,userResponseParser.getName(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setLogo() {
        requestManager
                .load(logo)
                .into((ImageView) findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                attemptLogin();
                break;
        }
    }

    private void attemptLogin() {
        if(!TextUtils.isEmpty(userID.getText().toString().trim())){
            viewModel.authenticateWithId(Integer.parseInt(userID.getText().toString().trim()));
        }
    }
}
