package com.tecx.todo.ui.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tecx.todo.R
import com.tecx.todo.db.DBHandler
import com.tecx.todo.model.ToDo
import com.tecx.todo.ui.adapter.TodoAdapter
import kotlinx.android.synthetic.main.activity_todolist.*


class TodoListActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        darkMode()
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist)
        setSupportActionBar(dashboard_toolbar)
        dbHandler = DBHandler(this)
        rv_dashboard.layoutManager = LinearLayoutManager(this)

        fab_dashboard.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
            dialog.setTitle(getString(R.string.add_todo))
            val view = layoutInflater.inflate(R.layout.dialog_todolist, null)
            val toDoName = view.findViewById<EditText>(R.id.ev_todo)

            dialog.setView(view)

            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                if (toDoName.text.isNotEmpty()) {
                    val toDo = ToDo()
                    toDo.name = toDoName.text.toString()
                    dbHandler.addToDo(toDo)
                    refreshList()
                }
            }

            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int -> }
            dialog.create().show()
        }

    }

    private fun darkMode() {
        // Shared preferences for toggling light and dark modes
        val isDarkOn: Boolean =
            getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getBoolean("isDarkOn", false)

        if (isDarkOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_item_light_mode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                    .putBoolean("isDarkOn", false).apply()
            }

            R.id.menu_item_dark_mode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                    .putBoolean("isDarkOn", true).apply()
            }

            R.id.menu_item_about -> {
                startActivity(Intent(this, AboutMeActivity::class.java))
                finish()
                finishActivity(0)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    fun updateToDo(toDo: ToDo) {
        val dialog = MaterialAlertDialogBuilder(
            this,
            R.style.MaterialAlertDialog_Rounded
        )
        dialog.setTitle(getString(R.string.update_todo))
        val view = layoutInflater.inflate(R.layout.dialog_todolist, null)
        val toDoName = view.findViewById<EditText>(R.id.ev_todo)
        toDoName.setText(toDo.name)
        dialog.setView(view)

        dialog.setPositiveButton("Update") { _: DialogInterface, _: Int ->
            if (toDoName.text.isNotEmpty()) {
                toDo.name = toDoName.text.toString()
                dbHandler.updateToDo(toDo)
                refreshList()
            }
        }

        dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int -> }
        dialog.create().show()
    }

    fun refreshList() {
        rv_dashboard.adapter =
            TodoAdapter(
                this,
                dbHandler.getToDos()
            )
    }

}