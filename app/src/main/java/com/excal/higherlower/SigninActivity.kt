package com.excal.higherlower

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.excal.higherlower.databinding.ActivitySigninBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var oneTapClient: SignInClient

    //Firebase auth

    private val REQ_ONE_TAP = 1
    private var clientId="874497066463-svjon23sk78l9a25k5o3q7c21vj6lmaq.apps.googleusercontent.com"
    private lateinit var auth:FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivitySigninBinding.inflate(layoutInflater)
        auth= Firebase.auth


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnSignIn.setOnClickListener{
            signIn()
        }

    }

    public override fun onStart() {
        super.onStart()
        val currentUser=auth.currentUser
        if(currentUser!=null){

        }
    }

    fun signIn(){
        oneTapClient = Identity.getSignInClient(this)
        val signInRequest=BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()


        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result->
                try{

                    startIntentSenderForResult(
                        result.pendingIntent.intentSender,
                        REQ_ONE_TAP,
                        null,
                        0,
                        0,
                        0,
                        null
                    )
                }catch(e: IntentSender.SendIntentException){
                    Log.e(TAG,"Error Launching One Tap Sign-in: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener{e->
                Log.e(TAG,"Error Launching One Tap Sign-in: ${e.localizedMessage}")

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQ_ONE_TAP){
            try{
                val credential=oneTapClient.getSignInCredentialFromIntent(data)
                val idToken=credential.googleIdToken
                val username=credential.displayName
                val email=credential.id
                if (idToken != null) {
                    Log.d("OneTap", "Got ID token: $idToken")
                    // Send the token to your backend
                } else {
                    Log.d("OneTap", "No ID token or credentials.")
                }
            } catch (e: ApiException) {
                Log.e("OneTap", "Sign-in failed: ${e.localizedMessage}")
            }
        }
    }
}