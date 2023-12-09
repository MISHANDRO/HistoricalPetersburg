package com.example.historicalpetersburg.tools.anim

import android.animation.ObjectAnimator
import android.view.View

class FadeAnimation {
    private fun fadeOut(view: View, duration: Long) {
        val fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f)
        fadeOut.duration = duration

        fadeOut.addListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                // Здесь можно выполнить дополнительные действия по завершении анимации, если нужно
            }
        })

        fadeOut.start()
    }

    private fun fadeIn(view: View, duration: Long) {
        val fadeOut = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f)
        fadeOut.duration = duration

        fadeOut.addListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                // Здесь можно выполнить дополнительные действия по завершении анимации, если нужно
            }
        })

        fadeOut.start()
    }
}