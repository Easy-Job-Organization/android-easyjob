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
import com.easyjob.jetpack.ui.theme.components.DropdownMenu1
import com.easyjob.jetpack.ui.theme.components.EasyJobLogo
import com.easyjob.jetpack.ui.theme.components.Input
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.TextButton
import com.easyjob.jetpack.viewmodels.RegisterViewModel

@Composable
fun RegisterScreen(
    navController: NavController = rememberNavController(),
    registerViewModel: RegisterViewModel = viewModel()
) {

    var name by remember {
        mutableStateOf("")
    }
    var last_name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var phone_number by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val options = listOf("Cliente", "Profesional")
    var selectedOption by remember { mutableStateOf("Selecciona una opción") }
    val authState by registerViewModel.authState.observeAsState()

    // Log authState changes in the composable to track updates
    LaunchedEffect(authState) {
        Log.d("RegisterScreen", "authState changed to $authState")
        if (authState == 3) { // Trigger navigation on success
            navController.navigate("home") {
                popUpTo("register") { inclusive = true } // Remove register from back stack
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
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

            Input(
                value = name,
                label = "Nombre",
                onValueChange = { name = it })
            Input(
                value = last_name,
                label = "Apellidos",
                onValueChange = { last_name = it })
            Input(value = email, label = "Correo", onValueChange = { email = it })
            Input(
                value = phone_number,
                label = "Teléfono",
                onValueChange = { phone_number = it })
            Input(
                value = password,
                label = "Contraseña",
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation()
            )
            DropdownMenu1(
                options = options,
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it })

            Box(modifier = Modifier.height(32.dp))

//            val options =
//                NavOptions.Builder().setPopUpTo(route = "register", inclusive = true).build()

            PrimaryButton(
                text = "Registrarse",
                onClick = {
                    registerViewModel.signUp(
                        name,
                        last_name,
                        email,
                        phone_number,
                        password,
                        selectedOption
                    )
                },
//                onClick = { navController.navigate("home", options) },
                width = 250
            )
        }

        // Layout UI with buttons and elements by authState
        when (authState) {
            1 -> CircularProgressIndicator()
            2 -> Text("Hubo un error", color = Color.Red)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .weight(1f),
//            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.Bottom
        ) {

            Text(text = "¿Ya tienes cuenta?")
            TextButton(
                text = "Inicia sesión",
                onClick = { navController.navigate("login") },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    RegisterScreen()
}