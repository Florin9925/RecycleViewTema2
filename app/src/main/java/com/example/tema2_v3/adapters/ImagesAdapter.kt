package com.example.tema2_v3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tema2_v3.R
import com.example.tema2_v3.models.Image
import java.util.*
import coil.load
import coil.transform.RoundedCornersTransformation

class ImagesAdapter(private val images: ArrayList<Image>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.image_album)

        fun bind(image: Image) {
            val imageViewUrl: String = image.imageUrl + ".png"
            imageView.load(imageViewUrl)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val imageRvItemView = inflater.inflate(R.layout.photo_cell, parent, false)
        return ImageViewHolder(imageRvItemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = images[position]
        (holder as ImageViewHolder).itemView.setOnClickListener {
            Toast.makeText(it.context, "Clicked", Toast.LENGTH_SHORT).show()
        }
        holder.bind(currentItem)
    }

    override fun getItemCount() = images.size

}