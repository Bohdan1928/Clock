package com.runner.clock

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.runner.clock.adapters.AccountsInfoAdapter
import com.runner.clock.databinding.ActivityMainBinding
import com.runner.clock.objects.User
import com.runner.clock.objects.UtilityAddressAndInfo

class MainActivity : AppCompatActivity() {
    private lateinit var rootElement: ActivityMainBinding
    private lateinit var accountList: ArrayList<UtilityAddressAndInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityMainBinding.inflate(layoutInflater)
        val view = rootElement.root
        val context = view.context
        setContentView(view)

        //Робота бази даних в автономному режимі
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        val accountsRecyclerView = rootElement.accountsRecyclerView
        val nameUtilityBillEdt = rootElement.nameUtilityBillEdt
        val cityEdt = rootElement.cityEdt
        val addressEdt = rootElement.addressEdt
        val apartmentNumberEdt = rootElement.apartmentNumberEdt
        val addUtilityCodeBtn = rootElement.addUtilityCodeBtn
        val chbPrivateHouse = rootElement.chbPrivateHouse

        val adapter = AccountsInfoAdapter(rootElement.root.context)
        accountsRecyclerView.adapter = adapter

        accountsRecyclerView.layoutManager = LinearLayoutManager(this)

        addUtilityCodeBtn.setOnClickListener {
            when {
                (addressEdt.text.isNotEmpty() && nameUtilityBillEdt.text.isNotEmpty()) -> {
                    if (chbPrivateHouse.isChecked) {
                        apartmentNumberEdt.text = null
                        accountList = adapter.update(
                            UtilityAddressAndInfo(
                                nameUtilityBillEdt.text.toString(),
                                cityEdt.text.toString(),
                                addressEdt.text.toString(),
                                "ПБ"
                            )
                        )
                    } else if (!chbPrivateHouse.isChecked) {
                        accountList = adapter.update(
                            UtilityAddressAndInfo(
                                nameUtilityBillEdt.text.toString(),
                                cityEdt.text.toString(),
                                addressEdt.text.toString(),
                                apartmentNumberEdt.text.toString()
                            )
                        )
                    }
                    nameUtilityBillEdt.text = null
                    addressEdt.text = null
                    apartmentNumberEdt.text = null
                    cityEdt.text = null
                }

                nameUtilityBillEdt.text.isEmpty() -> Toast.makeText(
                    context, "Введіть назву об'єкту", Toast.LENGTH_SHORT
                ).show()

                addressEdt.text.isEmpty() -> Toast.makeText(
                    context, "Введіть адресу", Toast.LENGTH_SHORT
                ).show()
            }

        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // цей метод викликається, коли елемент переміщується.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteAccount: UtilityAddressAndInfo = accountList[viewHolder.adapterPosition]
                val position = viewHolder.adapterPosition
                accountList.removeAt(position)
                adapter.notifyItemRemoved(position)

                Snackbar.make(
                    accountsRecyclerView, "Видалено " + deleteAccount.name, Snackbar.LENGTH_LONG
                ).setAction("Скасувати") {
                    accountList.add(position, deleteAccount)
                    adapter.notifyItemInserted(position)
                }.show()
            }
        }).attachToRecyclerView(accountsRecyclerView)

    }
}