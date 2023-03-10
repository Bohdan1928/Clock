package com.runner.clock.data.database.objectDB

import android.provider.BaseColumns

object UtilitiesInfoContract {
    object UtilitiesTableInfoEntry: BaseColumns {
        const val TABLE_NAME = "UtilitiesCityInfo"
        const val COLUMN_NAME_PROVIDER = "Provider"
        const val COLUMN_NAME_CITY = "City"
        const val COLUMN_NAME_UTILITY = "Utility"
    }
}