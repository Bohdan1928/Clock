package com.runner.clock.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.runner.clock.R
import com.runner.clock.objects.UtilityAccountInfo

class AccountsInfoAdapter() : RecyclerView.Adapter<AccountsInfoAdapter.ViewHolder>() {

    private var accountInfoList = emptyList<UtilityAccountInfo>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvAddress: TextView = itemView.findViewById(R.id.tv_address)
    }

    fun update(utilityAccountInfo: UtilityAccountInfo) {
        accountInfoList = accountInfoList + utilityAccountInfo
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val addressView = inflater.inflate(R.layout.utility_code_item, parent, false)
        return ViewHolder(addressView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val accountInfo: UtilityAccountInfo = accountInfoList[position]
        Log.d("MyLog", "$accountInfoList")
        Log.d("MyLog", "${accountInfo.name}, ${accountInfo.address}")
        holder.tvName.text = accountInfo.name
        holder.tvAddress.text = accountInfo.address
    }

    override fun getItemCount(): Int {
        return accountInfoList.size
    }
}