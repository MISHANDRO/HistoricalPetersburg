package com.example.historicalpetersburg.activities

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.databinding.ActivityToolsBinding
import java.util.Locale

class ToolsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityToolsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
//        enableEdgeToEdge(SystemBarStyle.dark(Color.BLUE))
        super.onCreate(savedInstanceState)

        binding = ActivityToolsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locale = Locale(application.resources.configuration.locale.language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_settings)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragmentId = intent.getIntExtra("id", R.id.settings_fragment)
        navController.navigate(fragmentId)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_settings)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        finish()
    }
}