package com.excal.higherlower.component


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.excal.higherlower.R
import com.excal.higherlower.presentation.sign_in.SignInState
import com.excal.higherlower.ui.theme.HIgherLowerTheme

@Composable
fun SignInScreen(modifier: Modifier = Modifier, state: SignInState, onSignInClick: () -> Unit) {

    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.app_logo_placeholder),
            contentDescription = null,
            modifier = modifier
                .padding(top = 70.dp)
                .size(245.dp),
        )
        Spacer(modifier = modifier.padding(top = 25.dp))
        Text(
            text = "Login",
            modifier = modifier.padding(16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            color = Color.White
        )
        Button(
            onClick = { onSignInClick() },
            colors = ButtonDefaults.buttonColors(Color.White),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp),


            ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_google),
                    contentDescription = null,
                    Modifier.size(18.dp)
                )
                Text(text = "Login with Google", fontSize = 18.sp, color = Color.Black)

            }
        }

    }


}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    HIgherLowerTheme {

//        LoginScreen()
    }
}