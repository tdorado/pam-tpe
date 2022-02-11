package com.td.wallendar.groupbalance

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.Debt
import java.util.*

class GroupBalanceAdapter(loggedUserId: Long) : RecyclerView.Adapter<GroupBalanceViewHolder?>() {
    private val dataset: MutableList<Debt>
    private val loggedUserId: Long
    private var onGroupSettleUpClickedListener: OnGroupSettleUpClickedListener? = null
    private var onGroupRemindClickedListener: OnGroupRemindClickedListener? = null
    fun setOnGroupSettleUpClickedListener(listener: OnGroupSettleUpClickedListener?) {
        onGroupSettleUpClickedListener = listener
    }

    fun setOnGroupRemindClickedListener(listener: OnGroupRemindClickedListener?) {
        onGroupRemindClickedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupBalanceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_balances, parent, false)
        return GroupBalanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupBalanceViewHolder, position: Int) {
        holder.bind(dataset[position], loggedUserId)
        holder.setOnGroupSettleUpClickedListener(onGroupSettleUpClickedListener)
        holder.setOnGroupRemindClickedListener(onGroupRemindClickedListener)
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