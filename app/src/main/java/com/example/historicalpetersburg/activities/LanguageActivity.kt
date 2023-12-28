package com.example.historicalpetersburg.activities

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.historicalpetersburg.App
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.databinding.ActivityLanguageBinding
import com.example.historicalpetersburg.databinding.ItemLanguageBinding
import com.example.historicalpetersburg.tools.settings.Settings


class LanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLanguageBinding

    private lateinit var checkNow: RadioButton
    private lateinit var animation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.closeBtnToolbar.setOnClickListener {
            for (activity in (application as App).activeActivities) {
                if (activity != this)
                    activity.recreate()
            }

            finish()
        }

        val langEntries = resources.getStringArray(R.array.lang_entries).toMutableList()
        val langEntriesEn = resources.getStringArray(R.array.lang_entries_english).toMutableList()
        val langValues = resources.getStringArray(R.array.lang_values).toMutableList()

        for (i in langEntries.indices) {
            if (langValues[i] == Settings.instance.localeHelper.languageCode) {
                langEntries[0] = langEntries[i].also { langEntries[i] = langEntries[0] }
                langEntriesEn[0] = langEntriesEn[i].also { langEntriesEn[i] = langEntriesEn[0] }
                langValues[0] = langValues[i].also { langValues[i] = langValues[0] }
            }
        }

        for (i in langEntries.indices) {
            val newRadioButton = layoutInflater.inflate(
                R.layout.item_language,
                binding.radioGroupLanguage,
                false
            )

            val newRadioButtonBinding = ItemLanguageBinding.bind(newRadioButton)

            newRadioButtonBinding.radioButtonLanguageTitle.text = langEntries[i]
            newRadioButtonBinding.radioButtonLanguageTitleEnglish.text = langEntriesEn[i]

            binding.radioGroupLanguage.addView(newRadioButton)

            newRadioButtonBinding.root.setOnClickListener {
                checkButton(newRadioButtonBinding.radioButtonLanguage, langValues[i])
            }

            if (langValues[i] == Settings.instance.localeHelper.languageCode) {
                newRadioButtonBinding.radioButtonLanguage.isChecked = true
                checkNow = newRadioButtonBinding.radioButtonLanguage
            }
        }

        setAnimations()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(Settings.instance.localeHelper.onAttach(newBase))
    }

    private fun checkButton(radioButton: RadioButton, language: String) {
        if (radioButton == checkNow) return

        checkNow.isChecked = false
        radioButton.isChecked = true
        checkNow = radioButton

        Settings.instance.setDefaultValueByKey(
            this,
            Settings.instance.localeHelper.key,
            language)

        Settings.instance.localeHelper.onAttach(this)

        binding.toolbarTitle.startAnimation(animation)
        binding.languageSetTitle.startAnimation(animation)
    }

    private fun setAnimations() {
        animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        animation.duration = 200
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                val fadeIn = AnimationUtils.loadAnimation(this@LanguageActivity, R.anim.fade_in)
                fadeIn.duration = animation.duration

                val config = Configuration()
                config.setLocale(resources.configuration.locale)
                val res = createConfigurationContext(config)

                binding.toolbarTitle.text = res.getString(R.string.language_title)
                binding.languageSetTitle.text = res.getString(R.string.language_set_title)

                binding.toolbarTitle.startAnimation(fadeIn)
                binding.languageSetTitle.startAnimation(fadeIn)
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.closeBtnToolbar.callOnClick()
    }
}