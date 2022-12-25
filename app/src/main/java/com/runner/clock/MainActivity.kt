package com.runner.clock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.runner.clock.adapters.AccountsInfoAdapter
import com.runner.clock.databinding.ActivityMainBinding
import com.runner.clock.objects.UtilityAccountInfo

class MainActivity : AppCompatActivity() {
    private lateinit var rootElement: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityMainBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)

        val accountsRecyclerView = rootElement.accountsRecyclerView
        val nameUtilityBillEdt = rootElement.nameUtilityBillEdt
        val addressEdt = rootElement.addressEdt
        val addUtilityCodeBtn = rootElement.addUtilityCodeBtn


        Log.d("MyLog", "In List: ")
        Log.d("MyLog", nameUtilityBillEdt.text.toString())
        val adapter = AccountsInfoAdapter()
        accountsRecyclerView.adapter = adapter

        accountsRecyclerView.layoutManager = LinearLayoutManager(this)

        addUtilityCodeBtn.setOnClickListener {
            adapter.update(
                UtilityAccountInfo(
                    nameUtilityBillEdt.text.toString(),
                    addressEdt.text.toString()
                )
            )

            nameUtilityBillEdt.text = null
            addressEdt.text = null
        }

    }
}