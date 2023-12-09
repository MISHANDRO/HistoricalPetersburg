package com.example.historicalpetersburg.activities

import android.animation.ObjectAnimator
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.databinding.FullscreenImageViewerBinding
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.tools.ImageAdapter
import com.example.historicalpetersburg.tools.value.ImageVal
import java.util.Locale


class FullscreenImageViewerActivity : AppCompatActivity() {

    private lateinit var binding: FullscreenImageViewerBinding
    private var isFullscreen: Boolean = false
    private lateinit var images: Array<ImageVal>
    private lateinit var separatorOf: String

    private lateinit var windowInsetsController: WindowInsetsControllerCompat
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale()
        super.onCreate(savedInstanceState)

        // TODO
        WindowCompat.setDecorFitsSystemWindows(window, false)
        windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        binding = FullscreenImageViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        separatorOf = binding.numberOfPhoto.text.toString()

        val currentPosition = intent.getIntExtra("current_position", 0)

        images = intent.getParcelableArrayExtra("current_array",  ImageVal::class.java) as Array<ImageVal>

        binding.fullscreenImageViewer.adapter = images.let { ImageAdapter(it).apply {
            scaleTypeOnItem = ImageView.ScaleType.FIT_CENTER
            onItemClick = { getStatusBarHeight(); toggleSystemUI() }
        } }

//        binding.fullscreenImageViewer.apply {
//            clipToPadding = false
//            clipChildren = false
//            offscreenPageLimit = 3
//            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
//        }

        binding.fullscreenImageViewer.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val text = "${position + 1} $separatorOf ${images.size}"
                binding.numberOfPhoto.text = text
            }
        })

        binding.fullscreenImageViewer.setCurrentItem(currentPosition, false)

        binding.toolbar.apply {
            setPadding(this.paddingLeft, this.paddingTop + (getStatusBarHeight() * 0.8).toInt(),
                this.paddingRight, this.paddingBottom)
        }

        binding.closeBtnViewer.setOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.image_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_download -> {
                val success = images[binding.fullscreenImageViewer.currentItem].saveToGallery(
                    this,
                    GlobalTools.instance.getString(R.string.app_name)
                )

                if (!success) GlobalTools.instance.toast(GlobalTools.instance.getString(R.string.unsuccess_save))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            for (i in 0 until menu.size()) {
                val menuItem = menu.getItem(i)
                val spannableString = SpannableString(menuItem.title)
                spannableString.setSpan(ForegroundColorSpan(Color.WHITE), 0, spannableString.length, 0)
                menuItem.title = spannableString
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun toggleSystemUI() {
        var start = 0f
        var end = 0f

        if (!isFullscreen) { // to show fullscreen
            end = -binding.toolbar.height.toFloat()
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        } else { // to hide
            start = -binding.toolbar.height.toFloat()
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        }

        binding.toolbar.let { actionBar ->
            val animator = ObjectAnimator.ofFloat(actionBar, "translationY", start, end)
            animator.duration = 300
            animator.start()
        }
        isFullscreen = !isFullscreen
    }


    private fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    private fun setLocale() {
        println(application.resources.configuration.locale.language)
        val locale = Locale(application.resources.configuration.locale.language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}