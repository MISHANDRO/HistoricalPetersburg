package com.example.historicalpetersburg.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.tools.settings.Settings

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        Handler().postDelayed({
            if (!GlobalTools.instance.isConnectedToInternet(this)) {
                Toast.makeText(this, "Нет инета, сука!", Toast.LENGTH_SHORT).show()

                val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkCallback = object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        // Сеть доступна
                        connectivityManager.unregisterNetworkCallback(this)
                        startMainActivity()
                    }
                }
                connectivityManager.registerDefaultNetworkCallback(networkCallback)

                return@postDelayed
            }

            startMainActivity()

        }, 1000)
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(Settings.instance.localeHelper.onAttach(newBase))
    }
}