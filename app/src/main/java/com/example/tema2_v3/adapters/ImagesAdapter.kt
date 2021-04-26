package com.example.tema2_v3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.ImageLoader.ImageContainer
import com.android.volley.toolbox.ImageLoader.ImageListener
import com.example.tema2_v3.R
import com.example.tema2_v3.models.Image
import com.example.tema2_v3.utils.VolleySingleton
import java.util.*

class ImagesAdapter(private val albums: ArrayList<Image>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.image)

        fun bind(image: Image) {
            val imageViewUrl: String = image.imageUrl.toString() + ".png"
            val imageLoader: ImageLoader =
                VolleySingleton.getInstance(imageView.context.applicationContext).imageLoader

            imageLoader[imageViewUrl, object : ImageListener {
                override fun onResponse(response: ImageContainer, isImmediate: Boolean) {
                    imageView.setImageBitmap(response.bitmap)
                }
                override fun onErrorResponse(error: VolleyError) {}
            }]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val imageRvItemView = inflater.inflate(R.layout.album_cell, parent, false)
        return ImageViewHolder(imageRvItemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = albums[position]
        (holder as ImageViewHolder).itemView.setOnClickListener {
            Toast.makeText(it.context, "Clicked", Toast.LENGTH_SHORT).show()
        }
        holder.bind(currentItem)
    }

    override fun getItemCount() = albums.size

}