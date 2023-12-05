package com.example.historicalpetersburg

import android.app.Activity
import android.content.res.Resources
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.yandex.runtime.image.ImageProvider

class GlobalTools {
    private var _activity: Activity? = null

    private var _fragmentManager: FragmentManager? = null

    val activity: Activity
        get() = _activity!!

    val fragmentManager: FragmentManager
        get() = _fragmentManager!!

    val displayMetrics: DisplayMetrics
        get() = activity.resources.displayMetrics

    val resources: Resources
        get() = activity.resources

    companion object {
        private val instanceObj: GlobalTools by lazy { GlobalTools() }

        val instance: GlobalTools
            get() { return instanceObj }

        fun setup(
            activity: Activity,
            fragmentManager: FragmentManager
        ) {
            instance._activity = activity
            instance._fragmentManager = fragmentManager
        }
    }

    fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(activity, message, length).show()
    }

    fun getString(id: Int): String {
        return activity.getString(id)
    }

    fun getStringArray(id: Int): List<String> {
        return resources.getStringArray(id).toList()
    }

    fun getInt(id: Int): Int {
        return resources.getInteger(id)
    }

    fun getIntArray(id: Int): List<Int> {
        return resources.getIntArray(id).toList()
    }

    fun getColor(id: Int): Int {
        return activity.getColor(id)
    }

    fun getImage(id: Int): ImageProvider {
        return ImageProvider.fromResource(activity, id)
    }
}