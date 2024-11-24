package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.EasyJobLogo
import com.easyjob.jetpack.ui.theme.components.Input
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.TextButton
import com.easyjob.jetpack.viewmodels.RecoverPassViewModel

@Composable
fun RecoverPasswordScreen(
    navController: NavController = rememberNavController(),
    viewModel: RecoverPassViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }

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

            Text(text = "Ingresa tu correo para recuperar tu contraseña")
            Input(value = email, label = "Correo", onValueChange = { email = it })

            Box(modifier = Modifier.height(48.dp))

            PrimaryButton(
                text = "Enviar correo",
                onClick = {
                    viewModel.recoverPass(email)
                },
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .weight(1f),
            verticalAlignment = Alignment.Bottom
        ) {

            Text(text = "¿Ya tienes cuenta?")
            TextButton(text = "Iniciar sesión", onClick = { navController.navigate("login") })
        }

    }

}