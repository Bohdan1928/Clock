package com.runner.clock.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.runner.clock.AccountInfo
import com.runner.clock.R
import com.runner.clock.objects.UtilityAccountInfo

class AccountsInfoAdapter(
    private val context: Context,
) :
    RecyclerView.Adapter<AccountsInfoAdapter.ViewHolder>() {

    private var accountInfoList = emptyList<UtilityAccountInfo>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvAddress: TextView = itemView.findViewById(R.id.tv_address)
        val tvCity: TextView = itemView.findViewById(R.id.tv_city)
    }

    fun update(utilityAccountInfo: UtilityAccountInfo): ArrayList<UtilityAccountInfo> {
        accountInfoList = accountInfoList + utilityAccountInfo
        notifyDataSetChanged()
        return accountInfoList as ArrayList<UtilityAccountInfo>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val accountView = inflater.inflate(R.layout.utility_code_item, parent, false)
        return ViewHolder(accountView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val accountInfo: UtilityAccountInfo = accountInfoList[position]

        holder.tvName.text = accountInfo.name
        holder.tvCity.text = accountInfo.city + ", "
        holder.tvAddress.text = accountInfo.address + ", " + accountInfo.numberApartment

        holder.itemView.setOnClickListener {
            val intent = Intent(context, AccountInfo::class.java)
            intent.putExtra("tv_name", holder.tvName.text)
            intent.putExtra("tv_address", holder.tvAddress.text)
            intent.putExtra("tv_city", holder.tvCity.text)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return accountInfoList.size
    }
}