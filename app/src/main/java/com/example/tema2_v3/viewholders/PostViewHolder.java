package com.example.tema2_v3.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tema2_v3.R;
import com.example.tema2_v3.models.Post;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class PostViewHolder extends ChildViewHolder {
    public TextView mTextView;
    private ImageView imageView;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.textView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

    public void bind(Post post) {
        mTextView.setText(post.getTitle());
    }
}
