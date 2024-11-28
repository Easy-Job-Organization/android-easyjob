package com.easyjob.jetpack.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.EasyJobLogo
import com.easyjob.jetpack.ui.theme.components.Input
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.TextButton
import com.easyjob.jetpack.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController = rememberNavController(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by loginViewModel.authState.observeAsState()


    // Log authState changes in the composable to track updates
    LaunchedEffect(authState) {
        Log.d("LoginScreen", "authState changed to $authState")
        when (authState) {
            3 -> { // Navegación exitosa para cliente
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }

            4 -> { // Navegación exitosa para profesional
                navController.navigate("homeProfessional") {
                    popUpTo("login") { inclusive = true }
                }
            }

            2 -> { // Credenciales incorrectas
                Toast.makeText(context, "Las credenciales son incorrectas", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (authState == 1) {
            // Mostrar CircularProgressIndicator centrado cuando esté cargando
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

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
                Input(
                    value = password,
                    label = "Contraseña",
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation()
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.Start),
                ) {
                    TextButton(
                        text = "¿Olvidaste tu contraseña?",
                        onClick = { navController.navigate("recover") })
                }

                Box(modifier = Modifier.height(48.dp))

                PrimaryButton(
                    text = "Iniciar sesión",
                    onClick = {
                        loginViewModel.signIn(email, password);
                    },
                    width = 250
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "¿Aún no tienes cuenta?")
                    TextButton(
                        text = "Regístrate",
                        onClick = { navController.navigate("register") })
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    LoginScreen()
}