package com.easyjob.jetpack.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.CreateServiceViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateServiceScreen(
    navController: NavController,
    viewModel: CreateServiceViewModel = hiltViewModel()
) {
    val serviceName by viewModel.serviceName.observeAsState("")
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val serviceDescription by viewModel.serviceDescription.observeAsState("")
    val servicePrice by viewModel.servicePrice.observeAsState(0.0)
    val updateResult by viewModel.updateResult.observeAsState(false)
    // Flag para campos vacíos
    var empty by remember { mutableStateOf(false) }
    var firstLoad by remember {
        mutableStateOf((true))
    }

    Scaffold(
        topBar = {
            Topbar(
                title = "Agregar un servicio",
                scrollBehavior = scrollBehavior,
                navController = navController,
                isBack = true
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Nombre del servicio",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = serviceName,
                onValueChange = viewModel::onServiceNameChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Nombre del servicio") }
            )

            Text(
                text = "Descripción del servicio",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = serviceDescription,
                onValueChange = viewModel::onServiceDescriptionChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { Text("Descripción breve del servicio") }
            )

            Text(
                text = "Precio del servicio",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = servicePrice.toString(),
                onValueChange = { newValue: String ->
                    val price = newValue.toDoubleOrNull()
                    if (price != null) {
                        viewModel.onServicePriceChange(price)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Precio en COP") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Botón de guardar cambios
            Button(

                onClick = {
                    firstLoad = false

                    if (serviceName.isNotEmpty() && serviceDescription.isNotEmpty() && servicePrice != 0.0) {
                        empty = false
                        viewModel.createServiceForProfessional()
                    } else {
                        empty = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (!firstLoad){
                // Mostrar mensaje de error si los campos están vacíos
                if (empty) {
                    Text(
                        text = "Por favor, completa todos los campos antes de continuar.",
                        color = MaterialTheme.colors.error
                    )
                }

                // Mostrar mensaje de error si falla la creación del servicio
                if (!updateResult) {
                    Text(
                        text = "Error al crear el servicio. Inténtalo de nuevo.",
                        color = MaterialTheme.colors.error
                    )
                }
            }
        }

        // Navegación cuando la creación del servicio sea exitosa
        LaunchedEffect(updateResult) {
            if (updateResult) {
                navController.popBackStack()
            }
        }
    }
}