package com.excal.higherlower


import android.app.ComponentCaller
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.excal.higherlower.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch





class LoginActivity : AppCompatActivity() {
    private var responseJson: String? = null
//    private val clientId="359966699878-th7v1m5qr51diq5i5fgm5fvnt5se0gri.apps.googleusercontent.com"
    private val clientId="874497066463-litk5v3pfbt09f0t460upv69s8sv6b0q.apps.googleusercontent.com"
    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth:FirebaseAuth

    private val REQ_ONE_TAP=2
    private var showOneTapUI=true



    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityLoginBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val credentialManager = CredentialManager.create(this@LoginActivity)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSignIn.setOnClickListener{
            signIn()

            val googleIdOption:GetGoogleIdOption=GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId(clientId)
                .setAutoSelectEnabled(true)
                .setNonce("")
                .build()

            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

//            lifecycleScope.launch {
//                try{
//                    val result = credentialManager.getCredential(
//                        request=request,
//                        context=this@LoginActivity,
//                    )
//                    if(result!=null){
//                        Log.i(TAG,"Result Get")
//                    }
//                    handleSignIn(result)
//
//                }catch (e: GetCredentialException){
////                    handleFailure(e)
//
//                }
//            }
        }
    }

    fun handleSignIn(result: GetCredentialResponse){
        val credential=result.credential

        when(credential){
            is PublicKeyCredential -> {
                responseJson=credential.authenticationResponseJson
            }

            is PasswordCredential ->{
                val username=credential.id
                val password = credential.password
            }

            is CustomCredential->{
               if(credential.type== GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                   try {

                       val googleIdTokenCredential = GoogleIdTokenCredential
                           .createFrom(credential.data)

                       val verifier = GoogleIdTokenVerifier.Builder(
                           NetHttpTransport(),
                           JacksonFactory()

                       )
                           .setAudience(listOf(clientId))
                           .build()
                       val idToken = verifier.verify(googleIdTokenCredential.idToken)
                       if(idToken!=null){
                           val userId=idToken.payload.subject
                       }


                   } catch (e: GoogleIdTokenParsingException) {
                       Log.e(TAG, "Received an invalid google id token response", e)
                   }
               }
                else{
                    Log.e(TAG, "Unexpected type of credential")
                }
            }

            else->{
                Log.e(TAG,"unexpected type credential")
            }
        }
    }

    fun signIn(){
        val signInRequest=BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(clientId)
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()

    }

    override fun onStart(){
        super.onStart()
        auth= Firebase.auth

        val currentUser=auth.currentUser

//        updateUI(currentUser)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,

    ) {
        super.onActivityResult(requestCode, resultCode, data)
            val oneTapClient =  Identity.getSignInClient(this@LoginActivity)

            when(requestCode){
                REQ_ONE_TAP ->{
                    try{
                        val credential=oneTapClient.getSignInCredentialFromIntent(data)
                        val idToken=credential.googleIdToken

                        when{
                            idToken!=null->{
                                Log.d(TAG, "Got ID Token")
                                val firebaseCredential= GoogleAuthProvider.getCredential(idToken,null)
                                auth.signInWithCredential(firebaseCredential)
                                    .addOnCompleteListener(this@LoginActivity) {task->
                                        if(task.isSuccessful){
                                            Log.d(TAG,"Login with credential Success")
                                            val user=auth.currentUser

                                        }else{
                                            Log.w(TAG,"Sign in With Credential :failure",task.exception)
                                        }
                                    }
                            }
                            else->{
                                Log.d(TAG,"ID Token not found")
                            }
                        }
                    }
                    catch(e: ApiException){

                    }
                }
            }

    }
}