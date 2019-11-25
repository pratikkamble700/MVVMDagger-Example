package com.pratik.shweta.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.pratik.shweta.R;
import com.pratik.shweta.model.userAPI.UserResponseParser;
import com.pratik.shweta.ui.auth.AuthResource;
import com.pratik.shweta.viewmodels.ViewModelsProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";

    @Inject
    ViewModelsProviderFactory providerFactory;

    ProfileViewModel viewModel;

    TextView email, username, website;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "Profile Fragment...", Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ProfileFragment Created");

        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        website = view.findViewById(R.id.website);

        viewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel.class);
    }

    private void subscribeObserver() {
        //fragment has it's own lifecycle
        viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<UserResponseParser>>() {
            @Override
            public void onChanged(AuthResource<UserResponseParser> userResponseParserAuthResource) {
                if (userResponseParserAuthResource != null) {
                    switch (userResponseParserAuthResource.status) {
                        case AUTHENTICATED: {
                            setUserDetails(userResponseParserAuthResource.data);
                            break;
                        }
                        case ERROR: {
                            setErrorDetails(userResponseParserAuthResource.message);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void setErrorDetails(String message) {
        email.setText(message);
        username.setText("Error");
        website.setText("Error");
    }

    private void setUserDetails(UserResponseParser data) {
        email.setText(data.getEmail());
        username.setText(data.getName());
        website.setText(data.getWebsite());
    }
}
