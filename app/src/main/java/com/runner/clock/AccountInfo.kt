package com.runner.clock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.sax.RootElement
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.runner.clock.adapters.UtilitiesAdapter
import com.runner.clock.databinding.ActivityAccountInfoBinding
import com.runner.clock.databinding.ActivityMainBinding

class AccountInfo : AppCompatActivity() {
    private lateinit var rootElement: ActivityAccountInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        rootElement = ActivityAccountInfoBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)

        val tvAccountName: TextView = rootElement.accountNameTv
        val tvAccountCity: TextView = rootElement.accountCityTv
        val tvAccountAddress: TextView = rootElement.accountAddressTv

        val accountInfoRecyclerView = rootElement.accountInfoRecyclerView
        val adapter = UtilitiesAdapter(rootElement.root.context)
        accountInfoRecyclerView.adapter = adapter
        accountInfoRecyclerView.layoutManager = LinearLayoutManager(this)

        val bundle: Bundle? = intent.extras
        val name = bundle?.get("tv_name")
        val city = bundle?.get("tv_city")
        val address = bundle?.get("tv_address")

        tvAccountName.text = name.toString()
        tvAccountAddress.text = address.toString()
        tvAccountCity.text = city.toString()
    }
}