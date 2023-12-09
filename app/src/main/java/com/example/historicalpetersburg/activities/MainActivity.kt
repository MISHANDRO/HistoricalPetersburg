package com.example.historicalpetersburg.activities

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.historicalpetersburg.App
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.databinding.ActivityMainBinding
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.ui.map.MapFragment
import com.google.android.material.navigation.NavigationView
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(SystemBarStyle.dark(getColor(R.color.transparent_black)))
        super.onCreate(savedInstanceState)
        (application as App).mainActivity = this

        setLocale()
        GlobalTools.setup(this, supportFragmentManager)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            (view.layoutParams as ViewGroup.MarginLayoutParams).apply {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }
            view.requestLayout()
            WindowInsetsCompat.CONSUMED
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        drawerLayout.setStatusBarBackgroundColor(Color.TRANSPARENT)

        supportFragmentManager
            .beginTransaction().replace(R.id.fragment_content_main, MapFragment())
            .commit()

        binding.contentMain.openMenuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navView.setNavigationItemSelectedListener { item ->
            val intent = Intent(this, ToolsActivity::class.java)

            when (item.itemId) {
                R.id.nav_settings -> {
                    intent.putExtra("id", R.id.settings_fragment)
                }
            }

            startActivity(intent)
            drawerLayout.closeDrawer(GravityCompat.START)
            false
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        MapManager.instance.locationManager.onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        MapManager.instance.locationManager.onLocationEnabledResult(requestCode, resultCode)
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