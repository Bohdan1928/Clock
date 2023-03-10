package com.runner.clock.data.database.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module
import kotlin.math.sin

val firebaseModule = module {
    single { Firebase.database.reference }
    single { FirebaseAuth.getInstance() }
}