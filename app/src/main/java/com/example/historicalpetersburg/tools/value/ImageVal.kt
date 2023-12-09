package com.example.historicalpetersburg.tools.value

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.example.historicalpetersburg.tools.GlobalTools
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
class ImageVal(
    override var id: Int = -1,
    override var value: String = ""
) : IValue<String>, Parcelable {

    @IgnoredOnParcel
    private val target = ImageTarget()

    private val load: RequestBuilder<Bitmap>?
        get() {
            try {
                if (id == -1) {
                    return Glide.with(GlobalTools.instance.activity).asBitmap().load(value)
                }

                return Glide.with(GlobalTools.instance.activity).asBitmap().load(id).centerInside()
            } catch (_: Exception) {
                return null
            }
        }

    constructor(url: String) : this() {
        value = url
    }

    constructor(id: Int) : this() {
        this.id = id
    }

    fun into(view: ImageView) {
        target.actionResourceReady = { drawable ->
            view.setImageBitmap(drawable)
        }
        load?.into(target)
    }

    fun getBitmap(context: Context, drawableId: Int): Bitmap? {
        return target.bitmap
    }

    fun saveToGallery(context: Context, directoryName: String): Boolean {
        try {
            val filename = "image_${System.currentTimeMillis()}.jpg"
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES + File.separator + directoryName
                    )
                }
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let {
                resolver.openOutputStream(it).use { outputStream ->
                    if (outputStream != null) {
                        target.bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }
                }

                contentValues.clear()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    resolver.update(uri, contentValues, null, null)
                }
            }
        } catch (e: Exception) {
            println(e.message)
            return false
        }

        return true
    }
}