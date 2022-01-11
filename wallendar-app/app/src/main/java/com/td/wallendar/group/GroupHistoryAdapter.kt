package com.td.wallendar.group

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.GroupHistory
import java.util.*

class GroupHistoryAdapter : RecyclerView.Adapter<GroupHistoryViewHolder?>() {
    private val dataset: MutableList<GroupHistory>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_group_activity, parent, false)
        return GroupHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupHistoryViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(newDataset: MutableList<GroupHistory>) {
        dataset.clear()
        dataset.addAll(newDataset)
        notifyDataSetChanged()
    }

    fun addToDataset(history: GroupHistory) {
        dataset.add(0, history)
        notifyItemInserted(0)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    init {
        dataset = ArrayList()
    }
}