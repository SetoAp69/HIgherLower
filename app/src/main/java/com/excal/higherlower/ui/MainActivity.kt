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

        playBtn=binding.btnPlay

        val currentUser=firebaseAuth.currentUser
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


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

        binding.btnPlay.setOnClickListener{
//            signOut()
            val playFragment=PlayFragment()
            val fragment=fragmentManager.findFragmentByTag(PlayFragment::class.java.simpleName)
            fragmentManager.commit{
//                if (fragment != null) {
//                    replace(R.id.fragment_play,fragment)
//                    setReorderingAllowed(true)
//                    addToBackStack("name")
//                }
//                else{
//                    Log.i(TAG,"Fragment is Missing!!")
//                }
                replace<PlayFragment>(R.id.fragment_play)
                setReorderingAllowed(true)
                addToBackStack("name")
            }

            playBtn.isEnabled=false
            playBtn.alpha=0f



//            val intent= Intent(this@MainActivity, SigninActivity::class.java)
//            startActivity(intent)
//            finish()
        }





    }

    private fun signOut(){
        firebaseAuth.signOut()
    }
}