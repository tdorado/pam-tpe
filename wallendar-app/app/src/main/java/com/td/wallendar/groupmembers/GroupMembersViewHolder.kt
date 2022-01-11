package com.td.wallendar.groupmembers

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.ApplicationUser

class GroupMembersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(applicationUser: ApplicationUser) {
        val textView = itemView.findViewById<TextView?>(R.id.member_text_name)
        val userNameText = applicationUser.firstName + " " + applicationUser.lastName
        textView.text = userNameText
    }
}