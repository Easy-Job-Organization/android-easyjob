package com.easyjob.jetpack.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.EasyJobLogo
import com.easyjob.jetpack.ui.theme.components.Input
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.TextButton
import com.easyjob.jetpack.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController = rememberNavController(),
    loginViewModel: LoginViewModel = viewModel()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by loginViewModel.authState.observeAsState()

    // Log authState changes in the composable to track updates
    LaunchedEffect(authState) {
        Log.d("LoginScreen", "authState changed to $authState")
        if (authState == 3) { // Trigger navigation on success
            navController.navigate("home") {
                popUpTo("login") { inclusive = true } // Remove login from back stack
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(
            modifier = Modifier.weight(2f),
            verticalArrangement = Arrangement.Center
        ) {
            EasyJobLogo()
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Input(value = email, label = "Correo", onValueChange = { email = it })
            Input(value = password, label = "Contraseña", onValueChange = { password = it }, visualTransformation = PasswordVisualTransformation())

            Box(modifier = Modifier.height(48.dp))

            PrimaryButton(text = "Iniciar sesión",
                onClick = {
                    loginViewModel.signIn(email, password);
                },
                width = 250) //revisar que haya cumplido la condicion

        }

        // Layout UI con botones y elementos según authState.
        when (authState) {
            1 -> CircularProgressIndicator()
            2 -> Text("Hubo un error", color = Color.Red)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .weight(1f),
            verticalAlignment = Alignment.Bottom
        ) {

            Text(text = "¿Aún no tienes cuenta?")
            TextButton(text = "Regístrate", onClick = { navController.navigate("register") })

        }

    }

}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    LoginScreen()
}