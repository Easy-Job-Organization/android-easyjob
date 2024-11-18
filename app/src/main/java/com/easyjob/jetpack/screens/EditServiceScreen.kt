package com.easyjob.jetpack.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.EditServiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditServiceScreen(
    navController: NavController = rememberNavController(),
    serviceId: String,
    viewModel: EditServiceViewModel = hiltViewModel()
) {
    val serviceName by viewModel.serviceName.observeAsState("")
    val serviceDescription by viewModel.serviceDescription.observeAsState("")
    val servicePrice by viewModel.servicePrice.observeAsState(0.0)
    val updateResult by viewModel.updateResult.observeAsState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    // Cargar el servicio actual cuando la pantalla se monta
    LaunchedEffect(serviceId) {
        Log.d("EditServiceScreen", "------------------ Hace el launchedEffect ------------")
        viewModel.fetchServiceById(serviceId)
        Log.d("EditServiceScreen", "------------------ Service{ ${serviceName} - ${servicePrice} - ${serviceDescription} ")
    }

    Scaffold(
        topBar = {
            Topbar(
                title = "Editar servicios",
                navController = navController,
                scrollBehavior = scrollBehavior,
                isBack = true
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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

            Button(
                onClick = { viewModel.updateService(serviceId)
                    navController.popBackStack()},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }

            // Mostrar el resultado de la actualización
            updateResult?.let { result ->
                Spacer(modifier = Modifier.height(8.dp))
                if (result.isSuccess && result.getOrNull() == true) {
                    Text("Servicio actualizado exitosamente", color = MaterialTheme.colors.primary)
                } else if (result.isFailure) {
                    Text("Error al actualizar el servicio", color = MaterialTheme.colors.error)
                }
            }
        }
    }
}

