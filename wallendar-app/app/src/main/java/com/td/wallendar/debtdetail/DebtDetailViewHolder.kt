package com.td.wallendar.debtdetail

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.DebtDetail
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class DebtDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(debtDetail: DebtDetail) {
        val month = itemView.findViewById<TextView?>(R.id.group_activity_date_month)
        val day = itemView.findViewById<TextView?>(R.id.group_activity_date_number)
        val title = itemView.findViewById<TextView?>(R.id.group_activity_title)
        val amount = itemView.findViewById<TextView?>(R.id.group_activity_amount)

        amount.text = df.format(debtDetail.amount)
        title.text = debtDetail.description

        month.text = SimpleDateFormat("MMM").format(debtDetail.date)
        day.text = SimpleDateFormat("dd").format(debtDetail.date)
    }

    companion object {
        private val df: DecimalFormat = DecimalFormat("0.00")
    }
}