package com.pratik.shweta.ui.main.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pratik.shweta.R;
import com.pratik.shweta.model.postAPI.PostResponceParser;

public class PostsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PostResponceParser> posts = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_list_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PostViewHolder) holder).bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<PostResponceParser> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }

        public void bind(PostResponceParser post) {
            title.setText(post.getTitle());
        }
    }
}
