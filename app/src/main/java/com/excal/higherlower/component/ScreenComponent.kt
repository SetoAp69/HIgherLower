package com.excal.higherlower.component

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

import com.excal.higherlower.data.Movie

import com.excal.higherlower.ui.MovieViewModel
import com.excal.higherlower.ui.Screen
import com.excal.higherlower.ui.theme.HIgherLowerTheme
import com.excal.higherlower.ui.theme.SoftBlue
import com.excal.higherlower.ui.theme.SoftGreen
import com.excal.higherlower.ui.theme.SoftRed

@Composable
fun EndGameScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(40.dp))
            .background(color = Color.Black)
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Your Score",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 32.sp,
            modifier = modifier.padding(top = 150.dp)
        )

        Text(
            text = "69",
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            fontSize = 32.sp,
            modifier = modifier.padding(8.dp)
        )
        Row(modifier = modifier) {
            Text(
                text = "Best Score :",
                color = Color.White,
                fontWeight = FontWeight.Light,
                fontSize = 24.sp,
                modifier = modifier.padding(top = 8.dp, bottom = 8.dp, start = 2.dp, end = 2.dp)
            )
            Text(
                text = "69 ",
                color = Color.White,
                fontWeight = FontWeight.Light,
                fontSize = 24.sp,
                modifier = modifier.padding(top = 8.dp, bottom = 8.dp, start = 2.dp, end = 2.dp)
            )

        }


        Spacer(modifier = Modifier.padding(top = 25.dp, bottom = 25.dp))
        ReplayButton(modifier.padding(start = 20.dp, end = 20.dp))
        Spacer(modifier = Modifier.padding(top = 12.dp, bottom = 12.dp))
        ExitButton(modifier.padding(start = 20.dp, end = 20.dp))


    }
}

@Preview(showBackground = true)
@Composable
private fun EndGameScreenPreview() {
    HIgherLowerTheme {
        EndGameScreen()
    }
}




@Composable
fun CompareScreen(
    modifier: Modifier = Modifier,
    listMovie: List<Movie>,
    sharedViewModel: MovieViewModel,
    navController:NavController
) {

    val viewModel: MovieViewModel = sharedViewModel
    val gameState by viewModel.gameState.collectAsState()
    val movieIndex = gameState.movieIndex
    val isLeftImageLoaded = remember{ mutableStateOf(false) }
    val isRightImageLoaded = remember{ mutableStateOf(false) }


    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
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
                LaunchedEffect(key1=gameState.isFinish) {
                    if(gameState.isFinish){
                        navController.navigate(Screen.GameOverScreen.withArgs("${gameState.score}","normal")){
                            popUpTo(Screen.NormalMode.route){
                                saveState =true
                                inclusive =true
                            }
                        }
                    }
                }
                MovieContainer(movie = listMovie[movieIndex], isLeft = true, isImageLoaded = isLeftImageLoaded)
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
                    Log.d(TAG, "${movieIndex}")
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

    }
}

fun CompareMovie(movieRate1: Movie, movieRate2: Movie): Boolean {
    return (movieRate1.vote_average > movieRate2.vote_average)
}

@Preview(showBackground = true, backgroundColor = Long.MAX_VALUE)
@Composable
private fun CompareScreenPreview() {
    HIgherLowerTheme {

    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier,alpha:Float) {
    Box(modifier=modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Loading", fontSize = 24.sp, modifier = modifier.alpha(alpha))
    }
}

