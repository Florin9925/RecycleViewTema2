package com.example.tema2_v3.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tema2_v3.R;
import com.example.tema2_v3.adapters.MyAdapter;
import com.example.tema2_v3.fragments.Fragment1;
import com.example.tema2_v3.models.User;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class UserViewHolder extends GroupViewHolder {
    private TextView name;
    private ImageView imageView;
    private View view;
    private boolean isExpanded = false;

    public UserViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.title);
        imageView = (ImageView) view.findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExpanded){
                    collapse();

                }else{
                    isExpanded=true;
                    expand();


                }
            }
        });

        this.view = view;
    }

    @Override
    public void expand() {
        super.expand();


    }

    @Override
    public void collapse() {
        super.collapse();


    }

    public void bind(User user) {

        name.setText(user.getTitle());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment1.userNameClicked = user.getTitle();
                if (MyAdapter.onUserItemClick != null)
                    MyAdapter.onUserItemClick.onClick(user);
            }
        });
    }


}