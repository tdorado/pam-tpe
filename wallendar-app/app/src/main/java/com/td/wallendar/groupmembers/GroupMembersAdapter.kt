package com.td.wallendar.groupmembers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.ApplicationUser
import java.util.*

class GroupMembersAdapter : RecyclerView.Adapter<GroupMembersViewHolder?>() {
    private val dataset: MutableList<ApplicationUser>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMembersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_group_member, parent, false)
        return GroupMembersViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupMembersViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(newDataset: MutableList<ApplicationUser>) {
        dataset.clear()
        dataset.addAll(newDataset)
        notifyDataSetChanged()
    }

    init {
        dataset = ArrayList()
    }
}