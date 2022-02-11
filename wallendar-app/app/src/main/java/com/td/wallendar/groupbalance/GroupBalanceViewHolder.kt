package com.td.wallendar.groupbalance

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.Debt
import java.text.DecimalFormat

class GroupBalanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var onGroupSettleUpClickedListener: OnGroupSettleUpClickedListener? = null
    private var onGroupRemindClickedListener: OnGroupRemindClickedListener? = null
    fun bind(debt: Debt, loggedUserId: Long) {
        val textView = itemView.findViewById<TextView?>(R.id.row_balance_text)
        val remindButton = itemView.findViewById<Button?>(R.id.remind_debt_button)
        val settleUpButton = itemView.findViewById<Button?>(R.id.settle_up_debt_button)
        val amount = df.format(debt.amount)
        if (debt.from.id == loggedUserId) {
            remindButton.visibility = View.GONE
            val userToText = debt.to.firstName + " " + debt.to.lastName
            textView.text = itemView.context.getString(R.string.you_owe_to_user_amount, userToText, amount)
        } else if (debt.to.id == loggedUserId) {
            val userFromText = debt.from.firstName + " " + debt.from.lastName
            textView.text = itemView.context.getString(R.string.user_owes_you_amount, userFromText, amount)
        } else {
            remindButton.visibility = View.GONE
            val userFromText = debt.from.firstName + " " + debt.from.lastName
            val userToText = debt.to.firstName + " " + debt.to.lastName
            textView.text = itemView.context.getString(R.string.user_owes_to_user_amount, userFromText, userToText, amount)
        }
        remindButton.setOnClickListener { onGroupRemindClickedListener?.onGroupRemindClick(debt) }
        settleUpButton.setOnClickListener { onGroupSettleUpClickedListener?.onGroupSettleUpClick(debt) }
    }

    fun setOnGroupSettleUpClickedListener(listener: OnGroupSettleUpClickedListener?) {
        onGroupSettleUpClickedListener = listener
    }

    fun setOnGroupRemindClickedListener(listener: OnGroupRemindClickedListener?) {
        onGroupRemindClickedListener = listener
    }

    companion object {
        private val df: DecimalFormat = DecimalFormat("0.00")
    }
}