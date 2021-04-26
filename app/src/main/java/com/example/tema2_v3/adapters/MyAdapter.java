package com.example.tema2_v3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tema2_v3.R;

import com.example.tema2_v3.interfaces.OnUserItemClick;
import com.example.tema2_v3.models.Post;
import com.example.tema2_v3.models.User;
import com.example.tema2_v3.viewholders.PostViewHolder;
import com.example.tema2_v3.viewholders.UserViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class MyAdapter extends ExpandableRecyclerViewAdapter<UserViewHolder, PostViewHolder> {
    public Context context;
    public static OnUserItemClick onUserItemClick;
    public MyAdapter(List<? extends ExpandableGroup> groups, OnUserItemClick onUserItemClick){

        super(groups);
        this.onUserItemClick=onUserItemClick;
    }



    @Override
    public List<? extends ExpandableGroup> getGroups() {
        return super.getGroups();
    }

    @Override
    public UserViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recycleview_user, parent, false);
        return new UserViewHolder(v);

    }

    @Override
    public PostViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recycleview_post, parent, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(PostViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Post post = (Post) group.getItems().get(childIndex);
        holder.bind(post);
        holder.mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Selected : " + post.getTitle(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBindGroupViewHolder(UserViewHolder holder, int flatPosition, ExpandableGroup group) {
        final User user = (User) group;
        holder.bind(user);
    }




}