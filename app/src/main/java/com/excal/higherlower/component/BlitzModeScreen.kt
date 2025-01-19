package com.excal.higherlower.component

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.excal.higherlower.data.Movie
import com.excal.higherlower.data.MovieApi
import com.excal.higherlower.data.dummyListMovie
import com.excal.higherlower.ui.BlitzViewModel
import com.excal.higherlower.ui.BlitzViewModelFactory
import com.excal.higherlower.ui.MovieViewModel
import com.excal.higherlower.ui.MovieViewModelFactory
import com.excal.higherlower.ui.Screen
import com.excal.higherlower.ui.UiState
import com.excal.higherlower.ui.theme.HIgherLowerTheme
import com.excal.higherlower.ui.theme.SoftBlue
import com.excal.higherlower.ui.theme.SoftGreen
import com.excal.higherlower.ui.theme.SoftRed
import com.excal.higherlower.ui.theme.TransparentBlack
import kotlinx.coroutines.delay

@Composable
fun BlitzModeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val factory = BlitzViewModelFactory(MovieApi.retrofitServices)
    val viewModel: BlitzViewModel = viewModel(factory = factory)
    var movieIndex by remember { mutableStateOf(0) }

    val movieList = viewModel.movieListFlow.collectAsState(initial = UiState.Loading)
    when (val state = movieList.value) {
        is UiState.Loading -> {
            val alpha = remember { androidx.compose.animation.core.Animatable(1f) }
            LaunchedEffect(UiState.Loading) {
                while (true) {
                    delay(100L)
                    alpha.animateTo(0f)
                    alpha.animateTo(1f)
                }
            }
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingScreen(alpha = alpha.value)

            }
        }

        is UiState.Success -> {
            val listMovie = state.data
            BlitzMode(listMovie = listMovie, sharedViewModel = viewModel, navController = navController)
        }

        is UiState.Error -> {
            Text(text = "Error")
        }
    }
}



@Composable
fun BlitzMode(
    modifier: Modifier = Modifier,
    listMovie: List<Movie>,
    sharedViewModel: BlitzViewModel,
    navController: NavController
) {

    val viewModel: BlitzViewModel = sharedViewModel
    val gameState by viewModel.gameState.collectAsState()
    val movieIndex = gameState.movieIndex

    val timer by remember{ mutableStateOf(gameState.timer) }

    val isLeftImageLoaded = remember{ mutableStateOf(false) }
    val isRightImageLoaded = remember{ mutableStateOf(false) }

    var timerSize=1

    LaunchedEffect( key1 = isRightImageLoaded.value,key2=isLeftImageLoaded.value) {
        Log.d(TAG,"Right Image ${isRightImageLoaded.value }")
        Log.d(TAG,"Left Image ${isLeftImageLoaded.value }")
        if(isRightImageLoaded.value&&isLeftImageLoaded.value){
            viewModel.startTimer()
        }

    }

    LaunchedEffect(key1=gameState.timer){
        if(gameState.timer==0){
            viewModel.nextMovie()
        }

        while(gameState.timer<=3){

            timerSize=2
            delay(500)
            timerSize=1
        }
    }


    LaunchedEffect(key1=gameState.isFinish) {
        if(gameState.isFinish){
            navController.navigate(Screen.GameOverScreen.withArgs("${gameState.score}","blitz")){
                popUpTo(Screen.NormalMode.route){
                    saveState =true
                    inclusive =true
                }
            }
        }
    }



    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    MovieContainer(movie = listMovie[movieIndex], isLeft = true,isImageLoaded = isLeftImageLoaded)
                    MovieContainer(movie = listMovie[movieIndex + 1], isLeft = false, isImageLoaded = isRightImageLoaded)
                }

                if (!gameState.isAnswered) {
                    Text(
                        text = "VS",
                        modifier = modifier
                            .clip(shape = CircleShape)
                            .background(Color.White)
                            .padding(8.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                } else {
                    if (gameState.isCorrect) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = modifier
                                .clip(shape = CircleShape)
                                .background(Color.Green)
                                .padding(8.dp)
                                .size(30.dp),
                            tint = Color.White

                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            modifier = modifier
                                .clip(shape = CircleShape)
                                .background(Color.Red)
                                .padding(8.dp)
                                .size(30.dp),
                            tint = Color.White
                        )
                    }
                }
            }
            Column(modifier=modifier.padding(top=20.dp)) {

                Text(
                    text = "${gameState.timer}",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize=24.sp,
                    fontWeight=FontWeight.Bold,
                    modifier = modifier
                        .background(
                            shape = RoundedCornerShape(2.dp),
                            color = TransparentBlack,
                        )
                        .size(width = (32*1.5).dp, height = (24*1.5).dp)
                )
            }






        }

        Text(
            text = "Movie on the RIGHT has Higher or Lower Rating?",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .clip(RoundedCornerShape(0.dp))
                .background(color = SoftBlue)
                .padding(top = 8.dp, bottom = 8.dp)
        )

        Spacer(modifier = modifier.padding(top = 24.dp, bottom = 24.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val scope = rememberCoroutineScope()
            CompareButton(
                title = "LOWER",
                icon = Icons.Default.KeyboardArrowDown,
                color = SoftRed,
                onClick = {
                    if(isRightImageLoaded.value&&isLeftImageLoaded.value){
                        viewModel.onCompareClick(
                            movieIndex = movieIndex,
                            list = listMovie,
                            isHigher = false
                        )
                    }



                })
            Spacer(modifier = modifier.padding(start = 2.dp, end = 2.dp))
            CompareButton(
                title = "HIGHER",
                icon = Icons.Default.KeyboardArrowUp,
                color = SoftGreen,
                onClick = {
                    if(isRightImageLoaded.value&&isLeftImageLoaded.value){
                        viewModel.onCompareClick(
                            movieIndex = movieIndex,
                            list = listMovie,
                            isHigher = true
                        )
                    }
                }
            )


        }

        Spacer(modifier = modifier.padding(top = 12.dp, bottom = 12.dp))
        Text(text = "Score : ", fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold)

        Text(
            text = "${gameState.score}",
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(top = 5.dp)
        )

        Spacer(modifier = modifier.padding(top = 12.dp, bottom = 12.dp))
        Text(text = "Streak : ", fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold)

        Text(
            text = "${gameState.streak}",
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(top = 5.dp)
        )

    }

}

@Preview
@Composable
private fun BlitzModePreview() {
    HIgherLowerTheme {
//        BlitzModeScreen()
    }

}

@Composable
fun TimerTest(modifier: Modifier = Modifier) {
    Column {
        var timer by remember{mutableStateOf(10)}
        LaunchedEffect(key1 = Unit) {
            while(timer!=0){
                delay(1000)
                if(timer!=0) timer--
            }
        }
        Text(
            text = "$timer",
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(2.dp),
                    color = TransparentBlack,
                )
                .size(width = 32.dp, height = 24.dp)
        )
    }

}

@Preview
@Composable
private fun TimerTestPreview() {
    HIgherLowerTheme {
        TimerTest()
    }
    
}