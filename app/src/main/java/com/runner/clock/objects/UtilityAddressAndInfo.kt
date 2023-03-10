package com.runner.clock.objects

import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

data class UtilityAddressAndInfo(
    val name: String? = null,
    val city: String? = null,
    val address: String? = null,
    val buildingNumber: String? = null,
    val numberApartment: String? = null,
    val privateHouse: Boolean = false,
    val userId: String? = null
)