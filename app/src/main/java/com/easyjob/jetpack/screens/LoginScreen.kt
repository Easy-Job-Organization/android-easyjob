package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.easyjob.jetpack.ui.theme.components.Input
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.TextButton

@Composable
fun LoginScreen() {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }




    Scaffold(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box(modifier = Modifier) {

                //Logo

            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

                Input(value = email, label = "Correo", onValueChange = { email = it }, width = 300)
                Input(value = password, label = "Contraseña", onValueChange = { password = it }, width = 300, visualTransformation = PasswordVisualTransformation())

                Box(modifier = Modifier.height(18.dp))

                PrimaryButton(text = "Iniciar sesión", onClick = { /*TODO*/ }, width = 200)

            }

            Row(
                modifier = Modifier
                    .padding(top = 40.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                Text(text = "¿Aún no tienes cuenta?")
                TextButton(text = "Regístrate", onClick = { /*TODO*/ }, width = 50)

            }

        }

    }

}