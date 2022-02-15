package com.td.wallendar.home.groups

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.Group

class GroupAdapter<T : Group>(private val loggedUserId: Long) : RecyclerView.Adapter<GroupViewHolder<T>>() {
    private val groupDataset: MutableList<T> = mutableListOf()
    private var listener: OnGroupClickedListener? = null

    fun setOnGroupClickedListener(listener: OnGroupClickedListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_groups, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder<T>, position: Int) {
        holder.bind(groupDataset[position], loggedUserId)
        holder.setOnGroupClickedListener(listener)
    }

    override fun getItemCount(): Int {
        return groupDataset.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(newDataset: MutableList<T>) {
        groupDataset.clear()
        groupDataset.addAll(newDataset)
        notifyDataSetChanged()
    }

    fun addToDataset(group: T) {
        groupDataset.add(group)
        notifyItemInserted(groupDataset.size - 1)
    }

    fun updateInDataset(group: T) {
        val position = groupDataset.indexOf(group)
        groupDataset[position] = group
        notifyItemChanged(position)
    }

}