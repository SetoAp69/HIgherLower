package com.excal.higherlower.component

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.excal.higherlower.presentation.gamedata.DatabaseClient
import com.excal.higherlower.presentation.gamedata.GameData
import com.excal.higherlower.ui.GameOverViewModel
import com.excal.higherlower.ui.theme.HIgherLowerTheme

val databaseClient = DatabaseClient()
val demoViewModel = DemoViewModel()

@Composable
fun DemoScreen(modifier: Modifier = Modifier, navController: NavController) {
    val viewModel:GameOverViewModel=GameOverViewModel()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = demoViewModel.input.value,
            onValueChange = { newValue -> demoViewModel.updateData(newValue) },
            modifier = modifier.padding(bottom = 15.dp),
            placeholder = { Text("Type in NIgga!!!") }
        )
        Button(onClick = {
//            databaseClient.updateScore(
//                score = demoViewModel.input.value.toInt(),
//                gameMode = "blitzMode"
//            )
            Log.d(TAG,"${viewModel.gameData.value}")

        }) {
            Text(text = "Input")
        }
    }
}

@Composable
fun GetScoreDemo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Username", fontSize = 14.sp, modifier = modifier.padding(bottom = 15.dp))
        Text(text = "Normal Mode", fontSize = 14.sp, modifier = modifier.padding(bottom = 15.dp))
        Text(text = "Blitz Mode", fontSize = 14.sp, modifier = modifier.padding(bottom = 15.dp))
    }

}

@Preview(showBackground = true)
@Composable
private fun DemoScreenPreview() {
    HIgherLowerTheme {

//        DemoScreen(navController = Nav)
    }
}

class DemoViewModel() : ViewModel() {
    private val _input = mutableStateOf("")
    val input: State<String> get() = _input

    fun updateData(input: String) {
        _input.value = input
    }
}