package com.pratik.shweta.ui.main.posts;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.pratik.shweta.SessionManager;
import com.pratik.shweta.model.postAPI.PostResponceParser;
import com.pratik.shweta.network.main.MainAPI;
import com.pratik.shweta.ui.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostsViewModel extends ViewModel {
    private static final String TAG = "PostsViewModel";

    private final SessionManager sessionManager;
    private final MainAPI mainAPI;

//    private MediatorLiveData posts;
    private MediatorLiveData<Resource<List<PostResponceParser>>> posts;

    @Inject
    public PostsViewModel(SessionManager sessionManager,MainAPI mainAPI) {
        this.sessionManager = sessionManager;
        this.mainAPI = mainAPI;
    }

    public LiveData<Resource<List<PostResponceParser>>> observePosts() {
        if(posts == null){
            posts = new MediatorLiveData<>();
            posts.setValue(Resource.loading((List<PostResponceParser>)null));

            final LiveData<Resource<List<PostResponceParser>>> source = LiveDataReactiveStreams.fromPublisher(
                    mainAPI.getPostsFromUser(sessionManager.getAuthUser().getValue().data.getId())

                    .onErrorReturn(new Function<Throwable, List<PostResponceParser>>() {
                        @Override
                        public List<PostResponceParser> apply(Throwable throwable) throws Exception {
                            Log.e(TAG, "apply: ",throwable );

                            PostResponceParser errorPost = new PostResponceParser();
                            errorPost.setId(-1);
                            ArrayList<PostResponceParser> errorList = new ArrayList<>();

                            errorList.add(errorPost);
                            return errorList;
                        }
                    })

                            .map(new Function<List<PostResponceParser>, Resource<List<PostResponceParser>>>() {
                        @Override
                        public Resource<List<PostResponceParser>> apply(List<PostResponceParser> postResponceParsers) throws Exception {
                            if(postResponceParsers != null && postResponceParsers.size() > 0){
                                if(postResponceParsers.get(0).getId() == -1){
                                    return Resource.error("Something Went wrong",null);
                                }
                            }
                            return Resource.success(postResponceParsers);
                        }
                    })

                    .subscribeOn(Schedulers.io())
            );

            posts.addSource(source, new Observer<Resource<List<PostResponceParser>>>() {
                @Override
                public void onChanged(Resource<List<PostResponceParser>> listResource) {
                    posts.setValue(listResource);
                    posts.removeSource(source);
                }
            });
        }

        return posts;
    }
}
