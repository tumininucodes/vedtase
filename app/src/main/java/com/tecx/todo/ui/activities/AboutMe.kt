package com.tecx.todo.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tecx.todo.R
import kotlinx.android.synthetic.main.activity_about_me.*

class AboutMe : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(
            R.anim.right_to_left2,
            R.anim.stay_put
        )
        setContentView(R.layout.activity_about_me)
        setSupportActionBar(about_toolbar)

        about_toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, TodoListActivity::class.java))
            overridePendingTransition(
                R.anim.left_to_right,
                R.anim.right_to_left
            )
            finish()
            finishActivity(0)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.left_to_right,
            R.anim.right_to_left
        )
        startActivity(Intent(this, TodoListActivity::class.java))
        finish()
        finishActivity(0)
    }
}
