package com.example.tema2_v3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.tema2_v3.MainActivity
import com.example.tema2_v3.R
import com.example.tema2_v3.fragments.Fragment3
import com.example.tema2_v3.interfaces.IActivityFragmentCommunication
import com.example.tema2_v3.models.Album
import java.util.*

class AlbumsAdapter(
    private val albums: ArrayList<Album>,
    private val activity: IActivityFragmentCommunication?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.album_name)
        var id: Int = -1
        fun bind(album: Album) {
            titleView.text = album.name
            id = album.id
        }
    }

    override fun getItemCount() = albums.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val albumRvItemView = inflater.inflate(R.layout.album_cell, parent, false)
        return AlbumViewHolder(albumRvItemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = albums[position]
        (holder as AlbumViewHolder).itemView.setOnClickListener {
            Toast.makeText(it.context, "Images", Toast.LENGTH_SHORT).show()

            val myFragment: Fragment = Fragment3(holder.id)
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, myFragment).addToBackStack(null).commit()
        }

        holder.bind(currentItem)
    }

}