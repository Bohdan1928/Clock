package com.runner.clock.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.runner.clock.R
import com.runner.clock.constants.NameOfUtilitiesConst
import com.runner.clock.objects.Utility

class UtilitiesAdapter(private val context: Context) :
    RecyclerView.Adapter<UtilitiesAdapter.ViewHolder>() {
    private val utilitiesList = listOf<Utility>()

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var utilitiesIcon = item.findViewById(R.id.utilities_icon) as ImageView
        var tvNameUtilities: TextView = item.findViewById(R.id.tv_name_utilities)
        var tvProviderUtilities: TextView = item.findViewById(R.id.tv_provider_utilities)
        var tvPriceUtilities: TextView = item.findViewById(R.id.tv_price_utilities)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val utilityView = inflater.inflate(R.layout.utilities_item, parent, false)
        return ViewHolder(utilityView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var utility = utilitiesList[position]

        holder.tvNameUtilities.text = utility.utilityName
        when (holder.tvNameUtilities.text) {
            NameOfUtilitiesConst.ELECTRICITY -> {
                holder.utilitiesIcon.setBackgroundResource(R.drawable.combining_electricity_supply)
                holder.tvNameUtilities.text = NameOfUtilitiesConst.ELECTRICITY
                /*holder.tvProviderUtilities.text = */
            }
            NameOfUtilitiesConst.GAS_SUPPLY -> {
                holder.utilitiesIcon.setBackgroundResource(R.drawable.combining_gas)
            }
            NameOfUtilitiesConst.HEATING -> {
                holder.utilitiesIcon.setBackgroundResource(R.drawable.combining_radiator)
            }
            NameOfUtilitiesConst.WATER_SUPPLY -> {
                holder.utilitiesIcon.setBackgroundResource(R.drawable.combining_faucet)
            }
            NameOfUtilitiesConst.RENT -> {
                holder.utilitiesIcon.setBackgroundResource(R.drawable.combining_rent)
            }
            NameOfUtilitiesConst.GARBAGE -> {
                holder.utilitiesIcon.setBackgroundResource(R.drawable.combining_garbage)
            }
            NameOfUtilitiesConst.INTERCOM -> {
                holder.utilitiesIcon.setBackgroundResource(R.drawable.combining_intercom)
            }
            NameOfUtilitiesConst.ELEVATOR -> {
                holder.utilitiesIcon.setBackgroundResource(R.drawable.combining_elevator_maintenance)
            }
            NameOfUtilitiesConst.OTHER -> {
                holder.utilitiesIcon.setBackgroundResource(R.drawable.combining_other_services)
            }
        }
    }

    override fun getItemCount(): Int {
        return utilitiesList.size
    }
}
