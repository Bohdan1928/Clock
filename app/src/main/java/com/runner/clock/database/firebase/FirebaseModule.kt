package com.runner.clock.database.firebase

import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module
import kotlin.math.sin

val firebaseModule = module {
    single { FirebaseDatabase.getInstance() }
    single { FirebaseDatabaseService(get()) }
}