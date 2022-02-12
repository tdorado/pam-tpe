package com.td.wallendar.home.balances

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.Debt
import java.util.*

class BalanceAdapter(loggedUserId: Long) : RecyclerView.Adapter<BalanceViewHolder?>() {
    private val dataset: MutableList<Debt>
    private val loggedUserId: Long
    private var onBalanceSettleUpClickedListener: OnBalanceSettleUpClickedListener? = null
    private var onRemindButtonClickedListener: OnRemindButtonClickedListener? = null
    private var onDetailDebtClickedListener: OnDetailDebtClickedListener? = null

    fun setOnBalanceSettleUpClickedListener(
            listener: OnBalanceSettleUpClickedListener?,
            onRemindButtonClickedListener: OnRemindButtonClickedListener?,
            onDetailDebtClickedListener: OnDetailDebtClickedListener?,
    ) {
        this.onBalanceSettleUpClickedListener = listener
        this.onRemindButtonClickedListener = onRemindButtonClickedListener
        this.onDetailDebtClickedListener = onDetailDebtClickedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_balances, parent, false)
        return BalanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        holder.bind(dataset[position], loggedUserId)
        holder.setOnBalanceSettleUpClickedListener(onBalanceSettleUpClickedListener)
        holder.setOnRemindButtonClickedListener(onRemindButtonClickedListener)
        holder.setOnDetailDebtClickedListener(onDetailDebtClickedListener)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(newDataset: MutableList<Debt>) {
        dataset.clear()
        dataset.addAll(newDataset)
        notifyDataSetChanged()
    }

    fun removeFromDataset(debt: Debt?) {
        val position = dataset.indexOf(debt)
        dataset.removeAt(position)
        notifyItemRemoved(position)
    }

    init {
        dataset = ArrayList()
        this.loggedUserId = loggedUserId
    }
}