package com.tecx.todo

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_todolist.*


class TodoListActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_item_backup -> {
                Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show()
            }

            R.id.menu_item_export -> {
                Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show()
            }

            R.id.menu_item_about -> {
                startActivity(Intent(this, AboutMe::class.java))
                finish()
                finishActivity(0)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishActivity(1)
    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    fun updateToDo(toDo: ToDo) {
        val dialog = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)
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

    private fun refreshList() {
        rv_dashboard.adapter = DashboardAdapter(this, dbHandler.getToDos())
    }


    class DashboardAdapter(
        private val activity: TodoListActivity,
        private val list: MutableList<ToDo>
    ) :
        RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(activity).inflate(R.layout.rv_child_todolist, p0, false)
            )
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
            holder.toDoName.text = list[p1].name

            holder.menu.setOnClickListener {
                val popup = PopupMenu(activity, holder.menu)
                popup.inflate(R.menu.todo_item_menu)

                popup.setOnMenuItemClickListener {
                    when (it.itemId) {

                        R.id.menu_edit -> {
                            activity.updateToDo(list[p1])
                        }

                        R.id.menu_delete -> {
                            activity.dbHandler.deleteToDo(list[p1].id)
                            activity.refreshList()
                        }
                    }

                    true
                }
                popup.show()
            }
        }

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val toDoName: TextView = v.findViewById(R.id.tv_todo_name)
            val menu: ImageView = v.findViewById(R.id.iv_menu)
        }
    }
}
