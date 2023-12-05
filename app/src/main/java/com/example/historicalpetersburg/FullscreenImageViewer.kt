package com.example.historicalpetersburg

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.historicalpetersburg.map.views.adapters.ImageAdapter

class FullscreenImageViewer : AppCompatActivity() {

    // TODO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fullcreen_image_viewer)

        val viewPager = findViewById<ViewPager2>(R.id.fullscreen_image_viewer)
        val currentPosition = intent.getIntExtra("current_position", 0)

        val images = intent.getIntArrayExtra("current_array")?.toList()

        viewPager.adapter = images?.let { ImageAdapter(it) }
        viewPager.setCurrentItem(currentPosition, false)

        val button = findViewById<ImageButton>(R.id.close_btn_viewer)
        button.setOnClickListener {
            finish()
        }
    }
}