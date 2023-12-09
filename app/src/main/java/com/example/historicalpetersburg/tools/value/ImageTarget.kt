package com.example.historicalpetersburg.tools.value

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.historicalpetersburg.tools.GlobalTools

class ImageTarget : CustomTarget<Bitmap>() {

    var actionResourceReady: ((Bitmap) -> Unit)? = null
    var bitmap: Bitmap? = null

    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
        actionResourceReady?.invoke(resource)
        bitmap = resource
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        GlobalTools.instance.toast("Не удалось загрузить изображение")
    }

    override fun onLoadCleared(placeholder: Drawable?) {
        // TODO("Очистка ресурсов, если View, к которому привязан Glide, удаляется")
    }


}