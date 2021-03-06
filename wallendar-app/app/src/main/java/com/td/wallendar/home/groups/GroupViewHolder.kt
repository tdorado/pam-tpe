package com.td.wallendar.home.groups

import android.view.View
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.Event
import com.td.wallendar.models.Group
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class GroupViewHolder<T : Group>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var listener: OnGroupClickedListener? = null
    fun bind(group: T, loggedUserId: Long) {
        val groupTitleTextView = itemView.findViewById<TextView?>(R.id.row_group_name)
        val amountOwedTextView = itemView.findViewById<TextView?>(R.id.row_you_are_owed)
        val amountYouOweTextView = itemView.findViewById<TextView?>(R.id.row_you_owe)
        var amountOwed = 0.0
        var amountYouOwe = 0.0
        for (debt in group.debts) {
            if (debt.from.id == loggedUserId) {
                amountYouOwe += debt.amount
            }
            if (debt.to.id == loggedUserId) {
                amountOwed += debt.amount
            }
        }
        groupTitleTextView.text = group.title
        val amountOwedString = itemView.context.getString(R.string.you_are_owed, df.format(amountOwed))
        amountOwedTextView.text = amountOwedString
        val amountYouOweString = itemView.context.getString(R.string.you_owe, df.format(amountYouOwe))
        amountYouOweTextView.text = amountYouOweString
        itemView.setOnClickListener {
            if (listener != null) {
                listener!!.onGroupClicked(group.id, group is Event)
            }
        }
        if (group is Event) {
            val eventDate = itemView.findViewById<TextView?>(R.id.row_event_date)
            eventDate.visibility = VISIBLE
            eventDate?.text = "FECHA: " + SimpleDateFormat("dd-MM").format(group.date)
        }
    }

    fun setOnGroupClickedListener(listener: OnGroupClickedListener?) {
        this.listener = listener
    }

    companion object {
        private val df: DecimalFormat = DecimalFormat("0.00")
    }
}