package com.tecx.todo.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tecx.todo.R
import kotlinx.android.synthetic.main.activity_about_me.*

class AboutMeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)
        setSupportActionBar(about_toolbar)

        about_toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, TodoListActivity::class.java))
            finish()
            finishActivity(0)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
