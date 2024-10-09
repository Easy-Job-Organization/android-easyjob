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
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.DropdownMenu1
import com.easyjob.jetpack.ui.theme.components.Input
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.TextButton

@Composable
fun RegisterScreen(navController: NavController = rememberNavController()) {

    var name by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val options = listOf("Cliente", "Técnico")
    var selectedOption by remember { mutableStateOf("Selecciona una opción") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Box(modifier = Modifier) {

            //Logo

        }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

            Input(value = name, label = "Nombre", onValueChange = { newValue -> name = newValue }, width = 300)
            Input(value = phone, label = "Teléfono", onValueChange = { newValue -> phone = newValue }, width = 300)
            DropdownMenu1(options = options, selectedOption = selectedOption, onOptionSelected = { option -> selectedOption = option })
            Input(value = email, label = "Correo", onValueChange = { newValue -> email = newValue }, width = 300)
            Input(value = password, label = "Contraseña", onValueChange = { newValue -> password = newValue }, width = 300, visualTransformation = PasswordVisualTransformation())

            Box(modifier = Modifier.height(18.dp))

            val options = NavOptions.Builder().setPopUpTo(route = "register", inclusive = true).build()
            PrimaryButton(text = "Registrarse", onClick = { navController.navigate("home", options) }, width = 200)

        }

        Row(
            modifier = Modifier
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {

            Text(text = "¿Ya tienes cuenta?")
            TextButton(text = "Inicia sesión", onClick = { navController.navigate("login") }, width = 50)

        }

    }

}