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

class HistoryListAdapter : ListAdapter<Task, HistoryListAdapter.HistoryViewHolder>(HistoryComparator()){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        return HistoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bindText(current.name, holder.textViewHistoryName)
    }

    class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.layout_history)
        val textViewHistoryName :TextView = itemView.findViewById(R.id.textView_history)

        fun bindText (text: String?, textView: TextView) {
            textView.text = text
        }

        companion object{
            fun create (parent: ViewGroup) : HistoryViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
                return HistoryViewHolder(view)
            }
        }

    }

    class HistoryComparator : DiffUtil.ItemCallback<Task>(){
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem === newItem
        }
    }

}