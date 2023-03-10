package com.runner.clock.data.database.firebase

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthService {
    private val auth = Firebase.auth

    fun getCurrentUser(): FirebaseUser?{
        return auth.currentUser
    }

    fun singIn(email: String, password: String, context: Context){
    }

    fun singUp(email: String, password: String): Task<AuthResult>{
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun signOut(){
        auth.signOut()
    }
}