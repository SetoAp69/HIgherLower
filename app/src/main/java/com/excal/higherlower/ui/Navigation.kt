package com.excal.higherlower.ui

import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.excal.higherlower.component.GameOverScreen
import com.excal.higherlower.component.MainMenuScreen
import com.excal.higherlower.component.NormalModeScreen
import com.excal.higherlower.component.SignInScreen
import com.excal.higherlower.presentation.sign_in.GoogleAuthClient
import com.excal.higherlower.presentation.sign_in.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun Navigation(googleAuthClient: GoogleAuthClient,lifecycleOwner: LifecycleOwner){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SignInScreen.route) {
        composable(Screen.SignInScreen.route) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                if(googleAuthClient.getSignedInUser()!=null){
                    navController.navigate("main"){
                        popUpTo(navController.graph.findStartDestination().id){
                            inclusive=true
                            saveState=false
                        }
                    }
                    viewModel.resetState()
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        lifecycleOwner.lifecycleScope.launch {
                            val signInResult = googleAuthClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                })
            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    navController.navigate("main"){
                        popUpTo(navController.graph.findStartDestination().id){
                            inclusive=true
                            saveState=false
                        }
                    }
                    viewModel.resetState()
                }
            }
            SignInScreen(state = state, onSignInClick = {
                Log.d(TAG, "Login tapped")
                lifecycleOwner.lifecycleScope.launch {
                    val signInIntentSender = googleAuthClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            })


        }
        composable(Screen.MainScreen.route) {
            MainMenuScreen(
                userData = googleAuthClient.getSignedInUser(),
                navController=navController,
                onSignOut = {
                    lifecycleOwner.lifecycleScope.launch{
                        googleAuthClient.signOut()

                    }
                    navController.navigate(Screen.SignInScreen.route){
                        popUpTo(0)
                    }
                })
        }
        composable(Screen.NormalMode.route){
            NormalModeScreen(navController=navController)
        }
        composable(
            route=Screen.GameOverScreen.route +"/{score}" ,
            arguments = listOf(
                navArgument("score"){
                    type= NavType.IntType
                    defaultValue=0
                    nullable=false
                }
            )
            ){entry->
                GameOverScreen(score=entry.arguments?.getInt("score"), navController = navController)

        }


    }
}