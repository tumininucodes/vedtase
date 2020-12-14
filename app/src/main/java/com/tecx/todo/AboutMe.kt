package com.tecx.todo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about_me.*

class AboutMe : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.right_to_left2, R.anim.stay_put)
        setContentView(R.layout.activity_about_me)
        setSupportActionBar(about_toolbar)

        about_toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "Thanks!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, TodoListActivity::class.java))
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
            finish()
            finishActivity(0)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        startActivity(Intent(this, TodoListActivity::class.java))
        finish()
        finishActivity(0)
    }
}
