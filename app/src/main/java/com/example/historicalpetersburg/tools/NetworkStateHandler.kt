package com.example.historicalpetersburg.tools

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.widget.Toast

class NetworkStateHandler(val context: Context) : ConnectivityManager.NetworkCallback() {


    override fun onAvailable(network: Network) {
        // Сеть доступна
        Toast.makeText(context, "Инет ЕСТЬ", Toast.LENGTH_SHORT).show()
    }

    override fun onLost(network: Network) {
        // Сеть потеряна
        Toast.makeText(context, "Инета НЕТ", Toast.LENGTH_SHORT).show()
    }
}
