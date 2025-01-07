package com.excal.higherlower

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.excal.higherlower.databinding.ActivityLogin2Binding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity2 : AppCompatActivity() {

    private lateinit var binding:ActivityLogin2Binding
    private lateinit var auth:FirebaseAuth
    private var clientId="874497066463-t4u8ingfou9mq901odh67kgsbhdpf08d.apps.googleusercontent.com"

    companion object{
        private const val RC_SIGN_IN=9001
    }


    override fun onCreate(savedInstanceState: Bundle?) {


        binding=ActivityLogin2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth=FirebaseAuth.getInstance()

        val currentUser=auth.currentUser

        if(currentUser!=null){
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val signInButton=binding.btnSignIn
        signInButton.setOnClickListener{
            signIn()
        }


    }

    private fun signIn(){
        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient= GoogleSignIn.getClient(this,gso)
        val signInIntent=googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== RC_SIGN_IN){
            val task=GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account=task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            }catch (e:ApiException){
                Log.i(TAG,"Login Failed :${e.message}")
            }
        }

    }

    private fun firebaseAuthWithGoogle(idToken:String){
        val credential= GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){task->
                if(task.isSuccessful){
                    val user=auth.currentUser
                    startActivity(Intent(this@LoginActivity2,MainActivity::class.java))
                    finish()
                }else{
                    Log.i(TAG,"Authentication failed")
                }
            }
    }

}