package com.excal.higherlower

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.excal.higherlower.databinding.ActivitySigninBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class SigninActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySigninBinding
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest:BeginSignInRequest

    private val REQ_ONE_TAP=1
    private var showOneTapUI=true

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivitySigninBinding.inflate(layoutInflater)
        firebaseAuth= Firebase.auth

        val signInButton=binding.btnSignIn



        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        signInButton.setOnClickListener{
            signIn()
        }


    }

    private fun signIn(){

        oneTapClient=Identity.getSignInClient(this@SigninActivity)
        signInRequest=BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .build()
            )
            .build()


        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, REQ_ONE_TAP,
                        null,0,0,0,null
                    )
                }catch(e: IntentSender.SendIntentException){
                    Log.e(TAG,"Couldn't start One Tap UI : ${e.localizedMessage}")
                }

            }
            .addOnFailureListener(this){e->
                Log.d(TAG,e.localizedMessage)
            }


    }

    override fun onStart() {
        super.onStart()
        val currentUser=firebaseAuth.currentUser
        if(currentUser!=null){
            val intent=Intent(this@SigninActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQ_ONE_TAP->{
                try{
                    val credential=oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken=credential.googleIdToken
                    when{
                        idToken!=null->{
                            val firebaseCredential=GoogleAuthProvider.getCredential(idToken,null)
                            firebaseAuth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener(this){
                                    task->
                                    if(task.isSuccessful){
                                        val user=firebaseAuth.currentUser
                                        val intent=Intent(this@SigninActivity,MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }else{
                                        Toast.makeText(this@SigninActivity,"Failed to signin using credential",Toast.LENGTH_LONG)

                                    }
                                }

                        }
                        else->{
                            Toast.makeText(this@SigninActivity,"Id Token Not Found",Toast.LENGTH_LONG)
                        }
                    }

                }catch (e:ApiException){
                    Log.w(TAG,"Error Sign in with One Tap Client : ${e.localizedMessage}")
                }
            }
        }
    }


}