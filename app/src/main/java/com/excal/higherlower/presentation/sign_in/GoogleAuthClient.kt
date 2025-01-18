package com.excal.higherlower.presentation.sign_in

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.excal.higherlower.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleAuthClient (
    private val context: Context,
    private val oneTapClient:SignInClient
    ){

    private val auth= Firebase.auth


    suspend fun signIn(): IntentSender?{
        val result = try{
            oneTapClient.beginSignIn(buildSignInRequest())
                .await()

        }catch (e:Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }

        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent):SignInResult{
        val credential= oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken=credential.googleIdToken
        val googleCredential=GoogleAuthProvider.getCredential(googleIdToken,null)

        return try{
            val user = auth.signInWithCredential(googleCredential).await().user
            SignInResult(
                data=user?.run{
                    UserData(
                        userId =uid,
                        userName = displayName,
                        pictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        }catch (e:Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data=null,
                errorMessage = e.localizedMessage
            )
        }
    }

    suspend fun signOut(){
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        }catch (e:Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    fun getSignedInUser():UserData?{
        return auth.currentUser?.run{
            UserData(
                userId = uid,
                userName = displayName,
                pictureUrl = photoUrl?.toString()
            )
        }
    }

    private fun buildSignInRequest():BeginSignInRequest{
        val request=BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        if(request!=null){
            Log.d(TAG,"Success Creating Sign In Request")
        }else{
            Log.d(TAG,"Success Creating Sign In Request")

        }

        return request
    }
}