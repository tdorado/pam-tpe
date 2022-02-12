package com.td.wallendar.group

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.GroupHistory
import com.td.wallendar.models.GroupHistoryType
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class GroupHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("SimpleDateFormat")
    fun bind(history: GroupHistory) {
        val month = itemView.findViewById<TextView?>(R.id.group_activity_date_month)
        val day = itemView.findViewById<TextView?>(R.id.group_activity_date_number)
        val title = itemView.findViewById<TextView?>(R.id.group_activity_title)
        val amount = itemView.findViewById<TextView?>(R.id.group_activity_amount)
        if (history.getGroupHistoryType() == GroupHistoryType.CHARGE) {
            title.visibility = View.VISIBLE
            title.text = history.getTitle()
            val userText = history.getFromUser().firstName + " " + history.getFromUser().lastName
            val amountText = df.format(history.getAmount())
            amount.text = itemView.context.getString(R.string.user_paid_amount, userText, amountText)
        } else {
            title.visibility = View.GONE
            val userFromText = history.getFromUser().firstName + " " + history.getFromUser().lastName
            val amountText = df.format(history.getAmount())
            val userToText = history.getToUser().firstName + " " + history.getToUser().lastName
            amount.text = itemView.context.getString(R.string.user_paid_amount_to, userFromText, amountText, userToText)
        }
        month.text = SimpleDateFormat("MMM").format(history.getDate())
        day.text = SimpleDateFormat("dd").format(history.getDate())
    }

    companion object {
        val df: DecimalFormat = DecimalFormat("0.00")
    }
}