package com.excal.higherlower.component

import android.content.ContentValues.TAG
import android.provider.CalendarContract.Colors
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.excal.higherlower.ui.theme.HIgherLowerTheme


@Composable
fun LoginWithGoogle(modifier: Modifier = Modifier) {
    Button(onClick = { }, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.MailOutline,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Text(
            text = "Login With Google",
            color = Color.White,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
private fun LoginWithGooglePreview() {
    HIgherLowerTheme {
        LoginWithGoogle()
    }
}


@Composable
fun PlayButton(modifier: Modifier = Modifier) {
    Button(
        onClick = {},
        modifier = modifier
            .widthIn(min = 200.dp, max = 250.dp)
            .heightIn(min = 50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
    ) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = null,
            modifier = modifier.size(ButtonDefaults.IconSize.plus(7.dp))
        )
    }
}


@Preview
@Composable
private fun PlayButtonPreview() {
    HIgherLowerTheme {
        PlayButton()
    }
}

@Composable
fun ReplayButton(modifier: Modifier = Modifier) {
    Button(
        onClick = {},
        modifier = modifier
            .widthIn(min = 200.dp, max = 250.dp)
            .heightIn(min = 50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
    ) {
        Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = null,
            modifier = modifier.size(ButtonDefaults.IconSize.plus(7.dp)),

            )
    }
}


@Preview
@Composable
private fun ReplayButtonPreview() {
    HIgherLowerTheme {
        ReplayButton()
    }
}


@Composable
fun ExitButton(modifier: Modifier = Modifier) {
    Button(
        onClick = {},
        modifier = modifier
            .widthIn(min = 200.dp, max = 250.dp)
            .heightIn(min = 50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
    ) {
        Icon(
            imageVector = Icons.Filled.ExitToApp,
            contentDescription = null,
            modifier = modifier.size(ButtonDefaults.IconSize.plus(7.dp)),

            )
    }
}


@Preview
@Composable
private fun ExitButtonPreview() {
    HIgherLowerTheme {
        ExitButton()
    }
}


@Composable
fun MenuButton(modifier: Modifier = Modifier, title: String) {
    Button(
        onClick = {},
        modifier = modifier
            .widthIn(min = 200.dp, max = 250.dp)
            .heightIn(min = 50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            fontSize = 18.sp,
            modifier = modifier
        )
    }
}


@Preview
@Composable
private fun ClassicButtonPreview() {
    HIgherLowerTheme {
        MenuButton(title = "CLASSIC MODE")
    }
}

@Composable
fun CompareButton(modifier: Modifier = Modifier, title: String, icon: ImageVector,onClick:()->Unit) {
    OutlinedButton(
        onClick = onClick,
//        shape = MaterialTheme.shapes.small,
        modifier = modifier.widthIn(min=150.dp),
        border = BorderStroke(width = 2.dp, color = Color.Cyan)
    ) {
        Text(text = title, color = Color.Cyan)
        Spacer(modifier = modifier.padding(ButtonDefaults.IconSpacing))
        Icon(
            imageVector = icon,
            tint = Color.Cyan,
            contentDescription = null,
            modifier = modifier.size(ButtonDefaults.IconSize)
        )
    }
}

@Preview(showBackground = true, backgroundColor = Long.MAX_VALUE)
@Composable
private fun CompareButtonPreview() {
    HIgherLowerTheme {
        Row(){

            CompareButton(title = "HIGHER", icon = Icons.Outlined.KeyboardArrowUp, onClick = {})
            CompareButton(title = "LOWER", icon = Icons.Outlined.KeyboardArrowDown, onClick = {})

        }
    }

}