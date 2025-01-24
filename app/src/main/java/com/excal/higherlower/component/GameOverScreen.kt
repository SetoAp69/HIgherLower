package com.excal.higherlower.component

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.excal.higherlower.ui.GameOverViewModel
import com.excal.higherlower.ui.Screen
import com.excal.higherlower.ui.theme.HIgherLowerTheme
import com.excal.higherlower.ui.theme.SoftRed

@Composable
fun GameOverScreen(
    modifier: Modifier = Modifier,
    score: Int? = 0,
    navController: NavController,
    mode: String? = ""
) {


    val viewModel = viewModel<GameOverViewModel>()

    val gameData by viewModel.gameData


    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        LaunchedEffect(key1 = gameData) {
            if (!viewModel.isLoading.value) {
                viewModel.updateScore(score = score!!, gameMode = mode!!)
                Log.d(TAG, "${viewModel.gameData.value}")

            }


        }
        Spacer(modifier = modifier.padding(top = 24.dp))
        Text(
            text = "Your Score",
            color = Color.White,
            fontSize = 32.sp,
            modifier = modifier.padding(24.dp),
        )
        Text(
            text = "${score}",
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            modifier = modifier.padding(24.dp),
        )
        Spacer(modifier = Modifier.padding(24.dp))
        MenuButton1(onClick = {

            if (mode == "normal") {
                navController.navigate(Screen.NormalMode.route)
                {
                    popUpTo(Screen.GameOverScreen.withArgs("${score}", mode!!)) {
                        inclusive = true
                        saveState = true
                    }
                }

            } else if (mode == "blitz") {
                navController.navigate(Screen.BlitzMode.route)
                {
                    popUpTo(Screen.GameOverScreen.withArgs("${score}", mode!!)) {
                        inclusive = true
                        saveState = true
                    }
                }
            }

        }, text = "Play Again")
        Spacer(modifier = Modifier.padding(16.dp))
        MenuButton1(onClick = {
            navController.popBackStack()
        }, color = SoftRed, text = "Back to Menu")

        Text(
            text = "Your Best Score",
            modifier = modifier.padding(vertical = 15.dp),
            fontSize = 14.sp
        )
        LaunchedEffect(key1 = viewModel.isLoading.value) {
            if(!viewModel.isLoading.value){

            }
        }
        if(mode=="blitz"){
            Text(
                text = "${gameData.blitz}",
                modifier = modifier.padding(vertical = 15.dp),
                fontSize = 14.sp
            )
        }else{
            Text(
                text = "${gameData.normal}",
                modifier = modifier.padding(vertical = 15.dp),
                fontSize = 14.sp
            )
        }


    }

}

@Preview(showBackground = false)
@Composable
private fun GameOverScreenPreview() {
    HIgherLowerTheme {

    }
}