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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.excal.higherlower.data.Movie
import com.excal.higherlower.ui.CompareViewModel
import com.excal.higherlower.ui.CompareViewModel2
import com.excal.higherlower.ui.CompareViewModelFactory
import com.excal.higherlower.ui.MovieViewModel

@Composable
fun GamePlayNormalScreen(
    modifier: Modifier = Modifier,
    listMovie: List<Movie>,
    sharedViewModel: MovieViewModel
) {
    val factory=CompareViewModelFactory(sharedViewModel)
    val viewModel:CompareViewModel = viewModel(factory=factory)
    val gameState by viewModel.state.collectAsState()
    val movieIndex = gameState.movieIndex

    val viewModel2 = viewModel<CompareViewModel2>()
    val gameState2= viewModel2.gameStateFlow.collectAsState(initial = CompareViewModel2.GameUiState.Loading)


    when (val gameUIState=gameState2.value){
        is CompareViewModel2.GameUiState.Loading->{

        }
        is CompareViewModel2.GameUiState.Playing->{


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
                            .padding(start = 8.dp, end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MovieContainer(movie = listMovie[movieIndex], isLeft = true)
                        MovieContainer(movie = listMovie[movieIndex + 1], isLeft = false)
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

                Spacer(modifier = modifier.padding(top = 12.dp, bottom = 12.dp))

                Text(
                    text = "Movie on the RIGHT has Higher or Lower Rating?",
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.padding(top = 12.dp, bottom = 12.dp))

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
                        icon = Icons.Default.KeyboardArrowUp,
                        onClick = {
                            viewModel.onCompareClick(
                                movieIndex = movieIndex,
                                list = listMovie,
                                isHigher = false
                            )
                            if(gameState.isFinish){
                                sharedViewModel.fetchMovie(gameState.page)
                            }
                            Log.d(TAG, "${movieIndex}")
                        })
                    Spacer(modifier = modifier.padding(start = 2.dp, end = 2.dp))
                    CompareButton(
                        title = "HIGHER",
                        icon = Icons.Default.KeyboardArrowDown,
                        onClick = {
                            viewModel.onCompareClick(
                                movieIndex = movieIndex,
                                list = listMovie,
                                isHigher = true
                            )
                            if(gameState.isFinish){
                                sharedViewModel.fetchMovie(gameState.page)
                            }
                            Log.d(TAG, "${movieIndex}")

                        }
                    )


                }
                Spacer(modifier = modifier.padding(start = 2.dp, end = 2.dp))
                Text(text = "${gameState.score}", fontSize = 30.sp, color = Color.White)


            }

        }
        is CompareViewModel2.GameUiState.Next->{

        }
        is CompareViewModel2.GameUiState.Finish->{

        }
    }


}
