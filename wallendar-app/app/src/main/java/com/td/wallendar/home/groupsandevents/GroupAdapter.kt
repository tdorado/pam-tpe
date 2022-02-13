package com.td.wallendar.home.groupsandevents

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.Group

class GroupAdapter(private val loggedUserId: Long) : RecyclerView.Adapter<GroupViewHolder?>() {
    private val groupDataset: MutableList<Group> = emptyArray<Group>().toMutableList()
    private var listener: OnGroupClickedListener? = null
    fun setOnGroupClickedListener(listener: OnGroupClickedListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_groups, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groupDataset[position], loggedUserId)
        holder.setOnGroupClickedListener(listener)
    }

    override fun getItemCount(): Int {
        return groupDataset.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(newDataset: MutableList<Group>) {
        groupDataset.clear()
        groupDataset.addAll(newDataset)
        notifyDataSetChanged()
    }

    fun addToDataset(group: Group) {
        groupDataset.add(group)
        notifyItemInserted(groupDataset.size - 1)
    }

    fun updateInDataset(group: Group) {
        val position = groupDataset.indexOf(group)
        groupDataset[position] = group
        notifyItemChanged(position)
    }

}