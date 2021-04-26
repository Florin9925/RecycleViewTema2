package com.example.tema2_v3.models;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import android.os.Parcel;

import java.util.List;

public class User extends ExpandableGroup<Post>  {

    private List<Post> usersPosts;

    public User(String title, List<Post> items) {
        super(title, items);
    }

    protected User(Parcel in) {
        super(in);
    }


    public List<Post> getUsersPosts() {
        return usersPosts;
    }

    public void setUsersPosts(List<Post> usersPosts) {
        this.usersPosts = usersPosts;
    }


}
