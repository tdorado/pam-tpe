package com.td.wallendar.debtdetail.ui

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.AbstractActivity
import com.td.wallendar.R
import com.td.wallendar.debtdetail.DebtDetailAdapter
import com.td.wallendar.di.DependenciesContainerLocator
import com.td.wallendar.models.DebtDetail

class DebtDetailActivity : AbstractActivity(), DebtDetailView {
    private val DEBT_ID: String = "DEBT_ID"

    private var debtId: Long? = null
    private var debtDetailAdapter: DebtDetailAdapter? = null
    private var debtDetailPresenter: DebtDetailPresenter? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debtId = intent.extras?.getLong(DEBT_ID)
        setContentView(R.layout.activity_debt_detail)
        setupAdapter()
        createPresenter()
    }

    private fun setupAdapter() {
        debtDetailAdapter = DebtDetailAdapter()

        val detailsRecycler = findViewById<RecyclerView>(R.id.debt_detail_recycler)
        detailsRecycler.setHasFixedSize(true)
        detailsRecycler.layoutManager = LinearLayoutManager(this.applicationContext, LinearLayoutManager.VERTICAL, false)
        detailsRecycler.adapter = debtDetailAdapter
    }

    private fun createPresenter() {
        debtDetailPresenter = lastNonConfigurationInstance as DebtDetailPresenter?
        if (debtDetailPresenter == null) {
            val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
            val debtRepository = dependenciesContainer.getDebtsRepository()
            val schedulerProvider = dependenciesContainer.getSchedulerProvider()
            debtDetailPresenter = DebtDetailPresenter(this, debtRepository, schedulerProvider)
        }
    }

    public override fun onStart() {
        super.onStart()
        debtDetailPresenter?.onViewAttached(debtId!!)
    }

    public override fun onStop() {
        super.onStop()
        debtDetailPresenter?.onViewDetached()
    }

    public override fun onDestroy() {
        super.onDestroy()
        debtDetailPresenter?.onViewDestroyed()
    }

    override fun bind(details: List<DebtDetail>) {
        debtDetailAdapter?.setDataset(details)
    }

    override fun failedToLoadDetails() {
        Toast.makeText(applicationContext,
                getString(R.string.failed_to_load_details), Toast.LENGTH_SHORT).show()
    }
}