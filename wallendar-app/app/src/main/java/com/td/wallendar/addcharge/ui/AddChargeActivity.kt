package com.td.wallendar.addcharge.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.td.wallendar.AbstractActivity
import com.td.wallendar.R
import com.td.wallendar.di.DependenciesContainerLocator
import com.td.wallendar.dtos.request.AddChargeRequest
import com.td.wallendar.models.*
import java.util.*

class AddChargeActivity : AbstractActivity(), AddChargeView {
    private var adapter: ArrayAdapter<String>? = null
    private val stringGroupMap: MutableMap<String, Group> = HashMap()
    private var chargeTitleInput: TextInputLayout? = null
    private var chargeAmountInput: TextInputLayout? = null
    private var groupSelected: String? = null
    private var addChargePresenter: AddChargePresenter? = null
    private var editTextFilledExposedDropdown: AutoCompleteTextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_charge)
        setupActionBar()
        setupInputs()
        createPresenter()
        setupGroupId()
    }

    private fun setupGroupId() {
        val extras = intent.extras
        if (extras != null) {
            val groupId = extras.getLong("GROUP_ID")
            addChargePresenter?.setGroupId(groupId)
        }
    }

    private fun setupInputs() {
        chargeTitleInput = findViewById(R.id.charge_title)
        chargeAmountInput = findViewById(R.id.charge_amount)
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white)
        actionBar.setTitle(R.string.add_charge)
    }

    private fun createPresenter() {
        addChargePresenter = lastNonConfigurationInstance as AddChargePresenter
        if (addChargePresenter == null) {
            val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
            val chargesRepository = dependenciesContainer.getChargesRepository()
            val groupsRepository = dependenciesContainer.getGroupsRepository()
            val schedulerProvider = dependenciesContainer.getSchedulerProvider()
            addChargePresenter = AddChargePresenter(this, chargesRepository,
                    groupsRepository, schedulerProvider)
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return addChargePresenter
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_charge_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == R.id.add_charge_done) {
            val editableChargeTitle = chargeTitleInput?.getEditText().getText()
            val editableChargeAmount = chargeAmountInput?.getEditText().getText()
            if (editableChargeTitle != null && editableChargeAmount != null && groupSelected != null) {
                val chargeTitle = editableChargeTitle.toString()
                val chargeAmount = editableChargeAmount.toString()
                val chargeAmountValue: Double
                chargeAmountValue = try {
                    chargeAmount.toDouble()
                } catch (e: Exception) {
                    //TODO
                    Toast.makeText(applicationContext, "TIENE QUE SER DOUBLE", Toast.LENGTH_LONG).show()
                    return false
                }
                val groupId = stringGroupMap.get(groupSelected).getId()
                addChargePresenter.addCharge(groupId, AddChargeRequest(chargeTitle,
                        loggedUserId, chargeAmountValue))
                return true
            } else {
                //TODO
                Toast.makeText(applicationContext, "PONELE TITULO", Toast.LENGTH_LONG).show()
            }
        }
        return false
    }

    override fun chargeError() {
        Toast.makeText(applicationContext, "Error adding charge", Toast.LENGTH_LONG).show()
    }

    override fun chargeAddedOk(charge: Charge?) {
        Toast.makeText(applicationContext, "Charge added ok", Toast.LENGTH_LONG).show()
        val resultIntent = Intent()
        resultIntent.putExtra("NEW_CHARGE", charge)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onGroupsLoadOk(groups: MutableList<Group?>?) {
        val stringGroups: MutableList<String?> = ArrayList()
        for (group in groups) {
            stringGroups.add(group.getTitle())
            stringGroupMap[group.getTitle()] = group
        }
        adapter = ArrayAdapter(this, R.layout.dropdown_menu_popup_item, stringGroups)
        editTextFilledExposedDropdown = findViewById(R.id.group_charge_dropdown)
        editTextFilledExposedDropdown.setAdapter(adapter)
        editTextFilledExposedDropdown.setOnItemClickListener(OnItemClickListener { adapterView: AdapterView<*>?, view: View?, i: Int, l: Long -> groupSelected = adapter.getItem(i) })
    }

    override fun onGroupsLoadError() {
        Toast.makeText(applicationContext, "Groups load error", Toast.LENGTH_LONG).show()
    }

    override fun setSelectedGroup(groupId: Long?) {
        for (group in stringGroupMap.values) {
            if (group.getId() == groupId) {
                groupSelected = group.getTitle()
            }
        }
        editTextFilledExposedDropdown.setText(groupSelected, false)
    }

    public override fun onStart() {
        super.onStart()
        addChargePresenter.onViewAttached(loggedUserId)
    }

    public override fun onStop() {
        super.onStop()
        addChargePresenter.onViewDetached()
    }
}