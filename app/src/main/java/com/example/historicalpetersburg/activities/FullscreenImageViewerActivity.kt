package com.example.historicalpetersburg.activities

import android.animation.ObjectAnimator
import android.content.Context
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
import androidx.viewpager2.widget.ViewPager2
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.databinding.FullscreenImageViewerBinding
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.tools.settings.Settings
import com.example.historicalpetersburg.tools.image.ImageAdapter
import com.example.historicalpetersburg.tools.image.ImageArray


class FullscreenImageViewerActivity : AppCompatActivity() {

    private lateinit var binding: FullscreenImageViewerBinding
    private var isFullscreen: Boolean = false
    private lateinit var images: ImageArray
    private lateinit var separatorOf: String

    private lateinit var windowInsetsController: WindowInsetsControllerCompat
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        if (!intent.hasExtra("current_array")) {
            finish()
            return
        }

        super.onCreate(savedInstanceState)

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

        images = intent.getStringArrayExtra("current_array")?.let { ImageArray(it) }!!
        binding.fullscreenImageViewer.adapter = images.let { ImageAdapter(it).apply {
            scaleTypeOnItem = ImageView.ScaleType.FIT_CENTER
            onItemClick = { toggleSystemUI() }
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

        binding.fullscreenImageViewer.setCurrentItem(intent.getIntExtra("current_position", 0), false)

        binding.toolbar.apply {
            setPadding(this.paddingLeft, this.paddingTop + (GlobalTools.instance.getStatusBarHeight() * 0.8).toInt(),
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
                val success = images[binding.fullscreenImageViewer.currentItem]!!.saveToGallery(
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

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(Settings.instance.localeHelper.onAttach(newBase))
    }
}