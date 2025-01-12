package com.excal.higherlower.ui


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.excal.higherlower.R
import com.excal.higherlower.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fragmentManager: FragmentManager

    private lateinit var playBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        firebaseAuth=Firebase.auth
        fragmentManager=supportFragmentManager


        val currentUser=firebaseAuth.currentUser
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        replaceFragment(HomeFragment(),true)


        if(currentUser!=null){


        }else{
            Log.i(TAG,"Auth Not Found")
        }
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//        binding.btnPlay.setOnClickListener{
//            val fragment=fragmentManager.findFragmentByTag(PlayFragment::class.java.simpleName)
//            fragmentManager.commit{
//                replace<PlayFragment>(R.id.fragment_play)
//                setReorderingAllowed(true)
//                addToBackStack("Play")
//            }
//
////            playBtn.isEnabled=false
////            playBtn.alpha=0f
//
//        }





    }

    fun replaceFragment(fragment: Fragment, addToBackStack:Boolean){
        val existingFragment=fragmentManager.findFragmentByTag(fragment::class.java.simpleName)
        fragmentManager.commit {
            if(existingFragment!=null){
                replace(R.id.fragment_play,existingFragment,fragment::class.java.simpleName)
            }else{
                replace(R.id.fragment_play,fragment,fragment::class.java.simpleName)

            }
            setReorderingAllowed(true)
            if(addToBackStack){
                addToBackStack(fragment::class.java.simpleName)
            }
        }
    }

    private fun signOut(){
        firebaseAuth.signOut()
    }
}