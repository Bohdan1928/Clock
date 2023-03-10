package com.runner.clock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.runner.clock.adapters.UtilitiesAdapter
import com.runner.clock.constants.NameOfUtilitiesConst
import com.runner.clock.data.database.dbHelper.UtilitiesDbHelper
import com.runner.clock.databinding.ActivityAccountInfoBinding
import com.runner.clock.objects.Utility

class AccountInfo : AppCompatActivity() {
    private lateinit var rootElement: ActivityAccountInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        rootElement = ActivityAccountInfoBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)

        val context = this

        val tvAccountName: TextView = rootElement.accountNameTv
        val tvAccountCity: TextView = rootElement.accountCityTv
        val tvAccountAddress: TextView = rootElement.accountAddressTv

        val accountInfoRecyclerView = rootElement.accountInfoRecyclerView
        val adapter = UtilitiesAdapter()
        accountInfoRecyclerView.adapter = adapter
        accountInfoRecyclerView.layoutManager = LinearLayoutManager(this)

        val bundle: Bundle? = intent.extras
        val name = bundle?.get("tv_name")
        val city = bundle?.get("tv_city")
        val address = bundle?.get("tv_address")

        tvAccountName.text = name.toString()
        tvAccountAddress.text = address.toString()
        tvAccountCity.text = city.toString()

        val dbHelper = UtilitiesDbHelper(context)
        dbHelper.insertData(
            Utility(
                getString(R.string.lviv_watter),
                getString(R.string.lviv),
                NameOfUtilitiesConst.WATER_SUPPLY
            )
        )
        dbHelper.insertData(
            Utility(
                getString(R.string.lviv_heat),
                getString(R.string.lviv),
                NameOfUtilitiesConst.HEATING
            )
        )
        dbHelper.insertData(
            Utility(
                getString(R.string.lviv_electricity),
                getString(R.string.lviv),
                NameOfUtilitiesConst.ELECTRICITY
            )
        )
        dbHelper.insertData(
            Utility(
                getString(R.string.green_era),
                getString(R.string.lviv),
                NameOfUtilitiesConst.GARBAGE
            )
        )
        dbHelper.insertData(
            Utility(
                getString(R.string.lviv_elevator),
                getString(R.string.lviv),
                NameOfUtilitiesConst.ELEVATOR
            )
        )
        dbHelper.insertData(
            Utility(
                getString(R.string.lviv_gas),
                getString(R.string.lviv),
                NameOfUtilitiesConst.GAS_SUPPLY
            )
        )
        dbHelper.insertData(
            Utility(
                getString(R.string.lviv_intercom),
                getString(R.string.lviv),
                NameOfUtilitiesConst.INTERCOM
            )
        )
        dbHelper.insertData(
            Utility(
                getString(R.string.lviv_osbb),
                getString(R.string.lviv),
                NameOfUtilitiesConst.RENT
            )
        )
        dbHelper.insertData(
            Utility(
                getString(R.string.lviv),
                getString(R.string.lviv),
                NameOfUtilitiesConst.OTHER
            )
        )
    }
}