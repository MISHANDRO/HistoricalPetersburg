package com.example.historicalpetersburg.map.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.historicalpetersburg.R

class ImageAdapter(private val images: List<Int>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null
    var scaleTypeOnItem = ImageView.ScaleType.CENTER_INSIDE

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image)

        init {
            imageView.apply {
                scaleType = scaleTypeOnItem

                setOnClickListener {
                    onItemClick?.invoke(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])
    }

    override fun getItemCount(): Int = images.size
}
