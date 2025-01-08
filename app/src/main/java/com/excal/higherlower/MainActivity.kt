package com.excal.higherlower

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.excal.higherlower.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        firebaseAuth=Firebase.auth
        val currentUser=firebaseAuth.currentUser
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        if(currentUser!=null){

            binding.tvEmail.text=currentUser.email.toString()
        }else{
            Log.i(TAG,"Auth Not Found")
        }
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSignOut.setOnClickListener{
            signOut()
            val intent= Intent(this@MainActivity,SigninActivity::class.java)
            startActivity(intent)
            finish()
        }





    }

    private fun signOut(){
        firebaseAuth.signOut()
    }
}