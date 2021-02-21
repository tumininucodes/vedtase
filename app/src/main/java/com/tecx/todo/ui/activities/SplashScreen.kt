package com.tecx.todo.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.tecx.todo.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Shared preferences for toggling light and dark modes
        val isDarkOn: Boolean =
            getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getBoolean("isDarkOn", false)

        if (isDarkOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        Handler().postDelayed(Runnable() {
            run() {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 1500)
    }
}