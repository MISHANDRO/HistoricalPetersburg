package com.example.historicalpetersburg.tools

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.yandex.runtime.image.ImageProvider
import java.io.File

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

    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }


    fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(activity, message, length).show()
    }

    fun getString(id: Int): String {
        return activity.getString(id)
    }

    fun getStringArray(id: Int): Array<String> {
        return resources.getStringArray(id)
    }

    fun getInt(id: Int): Int {
        return resources.getInteger(id)
    }

    fun getIntArray(id: Int): Array<Int> {
        return resources.getIntArray(id).toTypedArray()
    }

    fun getColor(id: Int): Int {
        return activity.getColor(id)
    }

    fun getImage(id: Int): ImageProvider {
        return ImageProvider.fromResource(activity, id)
    }

    fun getIdentifier(str: String, defType: String = "drawable"): Int {
        val fileNameWithExtension = File(str).name
        val fileName = fileNameWithExtension.substringBeforeLast('.')

        return resources.getIdentifier(fileName, defType, activity.packageName)
    }

    fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }
}