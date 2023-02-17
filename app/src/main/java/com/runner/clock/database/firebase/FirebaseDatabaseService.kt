package com.runner.clock.database.firebase

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.runner.clock.objects.User

class FirebaseDatabaseService(private val authService: FirebaseAuthService) {
    private val db = Firebase.database
    private val userRef = db.getReference("users")

    fun addUser(newUser: User): Task<Void> {
        newUser.id = authService.getCurrentUser()!!.uid
        return userRef.setValue(newUser)
    }

    fun getUser(email: String): LiveData<DataSnapshot>{
        val livedata = MutableLiveData<DataSnapshot>()
        userRef.child(email).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                livedata.value = snapshot
                //Обробка отриманого об'єкту
            }

            override fun onCancelled(error: DatabaseError) {
                //Обробка помилки
                Log.d("MyLog","Помилка : $error")
            }

        })
        return livedata
    }

    fun deleteUser(email: String){
        userRef.child(email).removeValue()
    }
}