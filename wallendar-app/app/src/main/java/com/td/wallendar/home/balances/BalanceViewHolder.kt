package com.td.wallendar.home.balances

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.Debt
import java.text.DecimalFormat

class BalanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var onBalanceSettleUpClickedListener: OnBalanceSettleUpClickedListener? = null
    private var onRemindButtonClickedListener: OnRemindButtonClickedListener? = null
    private var onDetailDebtClickedListener: OnDetailDebtClickedListener? = null
    fun bind(debt: Debt, loggedUserId: Long) {
        val textView = itemView.findViewById<TextView?>(R.id.row_balance_text)
        val remindButton = itemView.findViewById<Button?>(R.id.remind_debt_button)
        val settleUpButton = itemView.findViewById<Button?>(R.id.settle_up_debt_button)
        val debtDetail = itemView.findViewById<ImageView?>(R.id.debt_detail)

        if (!debt.containsDetails) {
            debtDetail.visibility = GONE
        } else {
            debtDetail.visibility = VISIBLE
            debtDetail.setOnClickListener { onDetailDebtClickedListener?.onDetailDebtClickedListener(debt.id) }
        }

        val amount = df.format(debt.amount)
        if (debt.from.id == loggedUserId) {
            remindButton.visibility = GONE
            val userToText = debt.to.firstName + " " + debt.to.lastName
            textView.text = itemView.context.getString(R.string.you_owe_to_user_amount, userToText, amount)
        } else {
            val userFromText = debt.from.firstName + " " + debt.from.lastName
            textView.text = itemView.context.getString(R.string.user_owes_you_amount, userFromText, amount)
        }
        remindButton.setOnClickListener { view: View? -> onRemindButtonClickedListener?.onRemindButtonClick(debt) }
        settleUpButton.setOnClickListener { view: View? -> onBalanceSettleUpClickedListener?.onBalanceSettleUpClick(debt) }
    }

    fun setOnBalanceSettleUpClickedListener(listener: OnBalanceSettleUpClickedListener?) {
        onBalanceSettleUpClickedListener = listener
    }

    fun setOnRemindButtonClickedListener(listener: OnRemindButtonClickedListener?) {
        onRemindButtonClickedListener = listener
    }

    fun setOnDetailDebtClickedListener(listener: OnDetailDebtClickedListener?) {
        onDetailDebtClickedListener = listener
    }

    companion object {
        private val df: DecimalFormat = DecimalFormat("0.00")
    }
}