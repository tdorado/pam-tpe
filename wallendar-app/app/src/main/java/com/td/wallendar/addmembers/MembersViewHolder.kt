package com.td.wallendar.addmembers

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R

class MembersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // TODO agregar para remover email y setear listener etc etc
    fun bind(memberEmail: String) {
        val textView = itemView.findViewById<TextView?>(R.id.member_text_mail)
        textView.text = memberEmail
    }
}