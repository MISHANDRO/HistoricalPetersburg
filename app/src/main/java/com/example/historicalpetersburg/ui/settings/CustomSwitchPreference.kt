package com.example.historicalpetersburg.ui.settings

import android.content.Context
import android.util.AttributeSet
import android.widget.Switch
import android.widget.TextView
import androidx.preference.PreferenceViewHolder
import androidx.preference.SwitchPreferenceCompat
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.MapManager

class CustomSwitchPreference(context: Context, attrs: AttributeSet) : SwitchPreferenceCompat(context, attrs) {

    init {
        widgetLayoutResource = R.layout.custom_switch_preference // Указываем кастомный макет
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)

        // Находим элементы макета
        val titleView = holder.findViewById(R.id.title) as TextView
        val switchView = holder.findViewById(R.id.switch_c) as Switch

        // Устанавливаем заголовок
        titleView.text = title

        // Восстанавливаем состояние переключателя из SharedPreferences
        val sharedPreferences = preferenceManager.sharedPreferences ?: return

        switchView.isChecked = sharedPreferences.getBoolean(key, false)

        // Устанавливаем слушатель на переключатель
        switchView.setOnCheckedChangeListener { _, isChecked ->
            // Сохраняем новое значение
            with(sharedPreferences.edit()) {
                putBoolean(key, isChecked)
                apply()
            }

            // Оповещаем об изменении
            callChangeListener(isChecked)
        }
    }
}
