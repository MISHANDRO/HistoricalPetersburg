package com.example.historicalpetersburg.tools.value

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.tools.anim.FadeAnimation

class ImageListener : RequestListener<Bitmap> {

    var actionResourceReady: ((Bitmap) -> Unit)? = null
    var bitmap: Bitmap? = null

    override fun onResourceReady(
        resource: Bitmap,
        model: Any,
        target: Target<Bitmap>,
        dataSource: DataSource,
        isFirstResource: Boolean
    ): Boolean {
        bitmap = resource

//        if (dataSource != DataSource.MEMORY_CACHE) {
            actionResourceReady?.invoke(resource)
//        }
        return false
    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Bitmap>?,
        isFirstResource: Boolean
    ): Boolean {
        GlobalTools.instance.toast(GlobalTools.instance.getString(R.string.unsuccess_load))
        return false
    }
}