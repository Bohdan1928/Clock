package com.runner.clock.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.runner.clock.AccountInfo
import com.runner.clock.R
import com.runner.clock.objects.UtilityAddressAndInfo

class UtilityAddressAdapter :
    RecyclerView.Adapter<UtilityAddressAdapter.ViewHolder>() {
    private var accountInfoList = ArrayList<UtilityAddressAndInfo>()

    fun getList(): ArrayList<UtilityAddressAndInfo> {
        return accountInfoList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvAddress: TextView = itemView.findViewById(R.id.tv_address)
        val tvCity: TextView = itemView.findViewById(R.id.tv_city)
        val tvBuildingNumber: TextView = itemView.findViewById(R.id.tv_building_number)
    }

    fun update(utilityAddressAndInfo: ArrayList<UtilityAddressAndInfo>): ArrayList<UtilityAddressAndInfo> {
        accountInfoList.clear()
        accountInfoList =
            (accountInfoList + utilityAddressAndInfo) as ArrayList<UtilityAddressAndInfo>
        notifyDataSetChanged()
        return accountInfoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val accountView = inflater.inflate(R.layout.utility_code_item, parent, false)
        return ViewHolder(accountView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val accountInfo: UtilityAddressAndInfo = accountInfoList[position]

        holder.tvName.text = accountInfo.name
        holder.tvCity.text = accountInfo.city + ", "
        if (accountInfo.numberApartment != null) {
            holder.tvAddress.text =
                accountInfo.address + " " + accountInfo.buildingNumber + ", " + accountInfo.numberApartment
        } else{
            holder.tvAddress.text =
                accountInfo.address + " " + accountInfo.buildingNumber + ", ПБ"
        }

    }

    override fun getItemCount(): Int {
        return accountInfoList.size
    }
}