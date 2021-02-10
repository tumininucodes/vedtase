package com.tecx.todo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tecx.todo.R
import com.tecx.todo.model.ToDo
import com.tecx.todo.ui.activities.TodoListActivity

class TodoAdapter(
    private val activity: TodoListActivity,
    private val list: MutableList<ToDo>
) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(activity)
                .inflate(R.layout.rv_child_todolist, p0, false)
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

