package com.example.fornow

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.sql.Date

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskComparator()){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskListAdapter.TaskViewHolder {
        return TaskViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)
        holder.bindText(current.name, holder.taskNameTextView)

        holder.bindText(Date(current.deadline).toString(), holder.daysLeftTextView)

        holder.constraintLayout.setOnClickListener {
            val intent = Intent(it.context, TaskActivity::class.java)
            intent.putExtra("id", current.id.toString())
            it.context.startActivity(intent)
        }
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskNameTextView : TextView = itemView.findViewById(R.id.textView_taskName)
        val daysLeftTextView : TextView = itemView.findViewById(R.id.textView_daysLeft)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.layout_task)

        fun bindText (text: String?, textView: TextView) {
            textView.text = text
        }

        companion object{
            fun create (parent: ViewGroup) : TaskViewHolder{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tasks, parent, false)
                return TaskViewHolder(view)
            }
        }
    }

    class TaskComparator : DiffUtil.ItemCallback<Task>(){
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem === newItem
        }
    }
}