package com.excal.higherlower.presentation.gamedata

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.core.snap
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DatabaseClient {
    private var database:FirebaseDatabase=FirebaseDatabase.getInstance("https://higherlowerapp-default-rtdb.asia-southeast1.firebasedatabase.app/")

    private lateinit var databaseRef:DatabaseReference
    private val currentUser = Firebase.auth.currentUser


    fun updateScore(score:Int,gameMode:String) {
        val updates=mapOf<String,Any>(
            "uid" to currentUser?.displayName!!,
            "${gameMode}" to score,
        )

        databaseRef = database.getReference("data/")

        databaseRef.child(currentUser.uid).updateChildren(updates)



    }

    fun GetGamedata(onComplete:(GameData?)->Unit){

        databaseRef=database.getReference("data/${currentUser?.uid}")
        databaseRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val snapshot=snapshot
                val gameData=snapshot.getValue(GameData::class.java)
                onComplete(gameData)
                return
            }

            override fun onCancelled(error: DatabaseError) {

                TODO("Not yet implemented")
                onComplete(null)
            }
        })

    }
}