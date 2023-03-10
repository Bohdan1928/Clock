package com.runner.clock

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.runner.clock.adapters.UtilityAddressAdapter
import com.runner.clock.data.api.ApiService
import com.runner.clock.databinding.ActivityMainBinding
import com.runner.clock.objects.UtilityAddressAndInfo
import org.koin.android.ext.android.inject
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var rootElement: ActivityMainBinding
    private lateinit var accountList: ArrayList<UtilityAddressAndInfo>
    private val database: DatabaseReference by inject()
    private val auth: FirebaseAuth by inject()
    private val pathAddressInfo = "AddressInfo"
    private val ref = database.child(pathAddressInfo)
    private val userId = auth.currentUser!!.uid
    private var addressAndInfo = UtilityAddressAndInfo()
    private val addressList = ArrayList<UtilityAddressAndInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityMainBinding.inflate(layoutInflater)
        val view = rootElement.root
        val context = view.context
        setContentView(view)

        val accountsRecyclerView = rootElement.accountsRecyclerView
        val nameUtilityBillEdt = rootElement.nameUtilityBillEdt
        val cityEdt = rootElement.cityEdt
        val addressEdt = rootElement.addressEdt
        val buildingNum = rootElement.buildingNumEdt
        val apartmentNumberEdt = rootElement.apartmentNumberEdt
        val addUtilityCodeBtn = rootElement.addUtilityCodeBtn
        val chbPrivateHouse = rootElement.chbPrivateHouse
        val adapter = UtilityAddressAdapter()
        accountsRecyclerView.adapter = adapter
        accountsRecyclerView.layoutManager = LinearLayoutManager(this)

        checkDataOnStart(adapter)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://opendata.city-adm.lviv.ua")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getStreets("64a169c2-ab09-4fcf-96c5-f89ffc409315")


        addUtilityCodeBtn.setOnClickListener {
            when {
                (addressEdt.text.isNotEmpty()
                        && nameUtilityBillEdt.text.isNotEmpty()
                        && buildingNum.text.isNotEmpty()) -> {

                    if (chbPrivateHouse.isChecked) {
                        addressAndInfo = UtilityAddressAndInfo(
                            nameUtilityBillEdt.text.toString(),
                            cityEdt.text.toString(),
                            addressEdt.text.toString(),
                            buildingNum.text.toString(),
                            null,
                            true,
                            userId
                        )
                        if (!addressList.contains(addressAndInfo)) {
                            val setValue = ref.push().setValue(addressAndInfo)
                            addCheck(setValue, adapter)
                        }
                    } else if (!chbPrivateHouse.isChecked) {
                        addressAndInfo = UtilityAddressAndInfo(
                            nameUtilityBillEdt.text.toString(),
                            cityEdt.text.toString(),
                            addressEdt.text.toString(),
                            buildingNum.text.toString(),
                            apartmentNumberEdt.text.toString(),
                            false,
                            userId
                        )
                        val setValue = ref.push().setValue(addressAndInfo)
                        addCheck(setValue, adapter)
                    }
                    nameUtilityBillEdt.text = null
                    addressEdt.text = null
                    buildingNum.text = null
                    apartmentNumberEdt.text = null
                    cityEdt.text = null
                }

                nameUtilityBillEdt.text.isEmpty() -> Toast.makeText(
                    context, "Введіть назву об'єкту", Toast.LENGTH_SHORT
                ).show()

                addressEdt.text.isEmpty() -> Toast.makeText(
                    context, "Введіть адресу", Toast.LENGTH_SHORT
                ).show()
                buildingNum.text.isEmpty() -> Toast.makeText(
                    context, "Введіть номер будинку", Toast.LENGTH_SHORT
                ).show()
            }
        }

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition // отримання позиції адаптера
                val addressToDelete = getAddress(adapter, position)
                removeElement(addressToDelete, adapter)
                println("From Adapter: $addressToDelete")
            }
        }).attachToRecyclerView(accountsRecyclerView)
    }

    private fun addCheck(setValue: Task<Void>, adapter: UtilityAddressAdapter) {
        setValue.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Main", "Data on DB")
                getAllAddressAndInfo(adapter)
            } else {
                Log.d("Main", "Task fall")
            }
        }
    }

    private fun getAllAddressAndInfo(adapter: UtilityAddressAdapter): ArrayList<UtilityAddressAndInfo> {
        ref.orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (info in snapshot.children) {
                        val utilityAddressAndInfo =
                            info.getValue(UtilityAddressAndInfo::class.java)!!
                        if (!addressList.contains(utilityAddressAndInfo)) {
                            addressList.add(utilityAddressAndInfo)
                            adapter.update(addressList)
                        }
                        Log.d("Main", "Data on RV")

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("Main", "Data fall")
                }
            })
        return addressList
    }

    private fun checkDataOnStart(adapter: UtilityAddressAdapter) {
        getAllAddressAndInfo(adapter)
        if (addressList.isNotEmpty()) {
            adapter.update(addressList)
        }
    }

    private fun getAddress(adapter: UtilityAddressAdapter, position: Int): String {
        var address: String? = String()
        adapter.getList().getOrNull(position)?.let { it ->
            address = it.address.toString()
        }
        return address!!
    }

    private fun removeElement(addressToDelete: String, adapter: UtilityAddressAdapter) {
        var obj: UtilityAddressAndInfo?
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    obj = item.getValue(UtilityAddressAndInfo::class.java)
                    if (addressToDelete == obj!!.address) {
                        println(obj.toString())
                        item.ref.removeValue()
                        addressList.remove(obj)
                        adapter.update(addressList)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
