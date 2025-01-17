package com.excal.higherlower.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.excal.higherlower.R
import com.excal.higherlower.ui.theme.Black
import com.excal.higherlower.ui.theme.HIgherLowerTheme
import com.excal.higherlower.ui.theme.SoftBlue
import com.excal.higherlower.ui.theme.SoftRed
import com.excal.higherlower.ui.theme.SpaceMono

@Composable
fun TopBarMainMenu(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SoftBlue),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box {
                Image(
                    modifier = modifier
                        .padding(8.dp)
                        .clip(shape = CircleShape)
                        .background(Color.Black)
                        .size(72.dp),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )

            }
            Text(
                text = "Username",
                modifier = modifier.padding(start = 24.dp, end = 8.dp),
                fontSize = 24.sp,
                softWrap = true,
                fontWeight = FontWeight.Bold,
                color = Color.White

            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier =modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = null,
                tint = Color.White,
                modifier=Modifier.size(50.dp).background(shape = RoundedCornerShape(3.dp), color = SoftRed).padding(8.dp)
            )
        }


    }
}

@Composable
fun MainMenu(modifier: Modifier = Modifier) {
    Scaffold() { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            TopBarMainMenu()
            Spacer(modifier = modifier.padding(72.dp))
            MenuButton1(onClick = { /*TODO*/ }, text = "NORMAL MODE")
            Spacer(modifier = modifier.padding(12.dp))
            MenuButton1(onClick = { /*TODO*/ }, text = "BLITZ")
            Spacer(modifier = modifier.padding(12.dp))
            MenuButton1(onClick = { /*TODO*/ }, text = "Exit", color = SoftRed)
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun TopBarMainMenuPreview() {
    HIgherLowerTheme {
        TopBarMainMenu()
    }
}

@Preview(showBackground = true)
@Composable
private fun MainMenuPreview() {
    MainMenu()
}