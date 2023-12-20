package com.example.historicalpetersburg.tools.image

import android.animation.Animator
import android.animation.AnimatorInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.tools.GlobalTools
import com.github.chrisbanes.photoview.PhotoView


class ImageAdapter(private val images: ImageArray) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null
    var scaleTypeOnItem = ImageView.ScaleType.CENTER_INSIDE
    var isZoomableOnItem = true

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: PhotoView = view.findViewById(R.id.image)

        init {
            imageView.apply {
                scaleType = scaleTypeOnItem
                isZoomable = isZoomableOnItem
            }

            view.findViewById<FrameLayout>(R.id.image_item_view_block).setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        images[position]?.into(holder.imageView)
    }

    override fun getItemCount(): Int = images.size
}
