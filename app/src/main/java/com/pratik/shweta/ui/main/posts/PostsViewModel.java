package com.pratik.shweta.ui.main.posts;

import androidx.lifecycle.ViewModel;

import com.pratik.shweta.SessionManager;
import com.pratik.shweta.network.main.MainAPI;

import javax.inject.Inject;

public class PostsViewModel extends ViewModel {
    private static final String TAG = "PostsViewModel";

    private final SessionManager sessionManager;
    private final MainAPI mainAPI;

    @Inject
    public PostsViewModel(SessionManager sessionManager,MainAPI mainAPI) {
        this.sessionManager = sessionManager;
        this.mainAPI = mainAPI;
    }
}
