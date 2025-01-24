package com.excal.higherlower.presentation.database

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class DatabaseClient {

    private lateinit var database: DatabaseReference
    private val currentUser = Firebase.auth.currentUser


    fun getData(input:String) {
        database =
            FirebaseDatabase.getInstance("https://higherlowerapp-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("data/")
                .child("${currentUser?.uid.toString()}")
                .child("/niggaShit")
                .
        database.setValue(input)
    }
}