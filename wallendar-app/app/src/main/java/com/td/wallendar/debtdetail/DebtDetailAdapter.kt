package com.td.wallendar.debtdetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.R
import com.td.wallendar.models.DebtDetail

class DebtDetailAdapter() : RecyclerView.Adapter<DebtDetailViewHolder?>() {

    private var details: List<DebtDetail> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_group_activity, parent, false)
        return DebtDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DebtDetailViewHolder, position: Int) {
        holder.bind(details[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataset(details: List<DebtDetail>) {
        this.details = details
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = details.size

}