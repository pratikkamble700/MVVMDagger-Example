package com.pratik.shweta.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pratik.shweta.R;
import com.pratik.shweta.model.postAPI.PostResponceParser;
import com.pratik.shweta.ui.main.Resource;
import com.pratik.shweta.utils.VerticalSpacingItemDecoration;
import com.pratik.shweta.viewmodels.ViewModelsProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {
    private static final String TAG = "PostsFragment";

    private PostsViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    PostsRecyclerAdapter adapter;

    @Inject
    ViewModelsProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);

        viewModel = ViewModelProviders.of(this, providerFactory).get(PostsViewModel.class);

        initRecyclerView();
        subScribeObserver();
    }

    private void subScribeObserver() {
        viewModel.observePosts().removeObservers(getViewLifecycleOwner());
        viewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<PostResponceParser>>>() {
            @Override
            public void onChanged(Resource<List<PostResponceParser>> listResource) {
                if (listResource != null) {
                    switch (listResource.status) {
                        case LOADING: {
                            Log.d(TAG, "onChanged: Loading...");
                            break;
                        }
                        case SUCCESS: {
                            adapter.setPosts(listResource.data);
                            break;
                        }
                        case ERROR: {
                            Log.e(TAG, "onChanged: Error in API "+ listResource.message);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }
}
