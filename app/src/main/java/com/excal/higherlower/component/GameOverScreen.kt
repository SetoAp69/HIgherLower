package com.excal.higherlower.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.excal.higherlower.ui.theme.HIgherLowerTheme
import com.excal.higherlower.ui.theme.SoftRed

@Composable
fun GameOverScreen(modifier: Modifier = Modifier) {

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier=modifier.padding(top=24.dp))
        Text(
            text = "Your Score",
            color = Color.White,
            fontSize = 32.sp,
            modifier = modifier.padding(24.dp)

            ,
        )
        Text(
            text = "2143213",
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            modifier = modifier.padding(24.dp)


            ,
        )
        Spacer(modifier = Modifier.padding(24.dp))
        MenuButton1(onClick = { /*TODO*/ }, text = "Play Again")
        Spacer(modifier = Modifier.padding(16.dp))
        MenuButton1(onClick = { /*TODO*/ }, color = SoftRed ,text = "Back to Menu")

    }

}

@Preview(showBackground = false)
@Composable
private fun GameOverScreenPreview() {
    HIgherLowerTheme {
        GameOverScreen()
    }
}