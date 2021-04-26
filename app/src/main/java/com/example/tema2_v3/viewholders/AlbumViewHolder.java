package com.example.tema2_v3.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tema2_v3.R;
import com.example.tema2_v3.adapters.AlbumsAdapter;
import com.example.tema2_v3.fragments.Fragment2;
import com.example.tema2_v3.models.Album;

public class AlbumViewHolder extends RecyclerView.ViewHolder {
    private TextView title;

    public AlbumViewHolder(@NonNull View view) {
        super(view);
        title = view.findViewById(R.id.album_name);

    }

    public void bind(Album album) {
        title.setText(album.getName());

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment2.albumId = album.getId();
                if (AlbumsAdapter.onAlbumItemClick != null)
                    AlbumsAdapter.onAlbumItemClick.onClick(album);
            }
        });
    }

}
