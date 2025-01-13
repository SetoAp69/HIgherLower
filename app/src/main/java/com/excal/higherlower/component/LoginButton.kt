package com.excal.higherlower.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.excal.higherlower.ui.theme.HIgherLowerTheme

class LoginButton {

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
}