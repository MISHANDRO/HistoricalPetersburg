package com.example.historicalpetersburg.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.databinding.ActivityToolsBinding
import com.example.historicalpetersburg.tools.settings.Settings


class ToolsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityToolsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
//        enableEdgeToEdge(SystemBarStyle.dark(Color.BLUE))
        super.onCreate(savedInstanceState)

        binding = ActivityToolsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_settings)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.toolbarTitle.text = binding.toolbar.title

        binding.closeBtnToolbar.setOnClickListener {
            finish()
        }

        val fragmentId = intent.getIntExtra("id", R.id.settings_fragment)
        navController.navigate(fragmentId)
        supportActionBar?.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_settings)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(Settings.instance.localeHelper.onAttach(newBase))
    }

    override fun onBackPressed() {
        finish()
    }
}