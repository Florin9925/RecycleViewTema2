package com.example.tema2_v3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tema2_v3.R;
import com.example.tema2_v3.interfaces.OnAlbumItemClick;
import com.example.tema2_v3.models.Album;
import com.example.tema2_v3.viewholders.AlbumViewHolder;

import java.util.ArrayList;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumViewHolder> {
    private Context context;
    ArrayList<Album> albums;
    public static OnAlbumItemClick onAlbumItemClick;

    public AlbumsAdapter(ArrayList<Album> albums, OnAlbumItemClick onAlbumItemClick) {
        this.albums = albums;
        this.onAlbumItemClick = onAlbumItemClick;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.album_cell, parent, false);
        AlbumViewHolder albumViewHolder = new AlbumViewHolder(view);

        return albumViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albums.get(position);

        holder.bind(album);
    }

    @Override
    public int getItemCount() {
        return this.albums.size();
    }
}
