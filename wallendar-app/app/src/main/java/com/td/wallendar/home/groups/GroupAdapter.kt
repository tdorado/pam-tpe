package com.td.wallendar.home.groups

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.Group

class GroupAdapter(private val loggedUserId: Long) : RecyclerView.Adapter<GroupViewHolder?>() {
    private val dataset: MutableList<Group> = emptyArray<Group>().toMutableList()
    private var listener: OnGroupClickedListener? = null
    fun setOnGroupClickedListener(listener: OnGroupClickedListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_groups, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(dataset[position], loggedUserId)
        holder.setOnGroupClickedListener(listener)
    }

    override fun getItemCount(): Int {
        return dataset.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(newDataset: MutableList<Group>) {
        dataset.clear()
        dataset.addAll(newDataset)
        notifyDataSetChanged()
    }

    fun addToDataset(group: Group) {
        dataset.add(group)
        notifyItemInserted(dataset.size - 1)
    }

    fun updateInDataset(group: Group) {
        val position = dataset.indexOf(group)
        dataset[position] = group
        notifyItemChanged(position)
    }

}