package com.td.wallendar.addmembers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R

class MembersAdapter(private val members: MutableList<String>) : RecyclerView.Adapter<MembersViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_member, parent, false)
        return MembersViewHolder(view)
    }

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        holder.bind(members[position])
    }

    override fun getItemCount(): Int {
        return members.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addToDataset(member: String) {
        members.add(member)
        notifyItemInserted(members.size - 1)
    }
}