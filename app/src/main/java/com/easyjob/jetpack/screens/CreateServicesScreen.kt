package com.easyjob.jetpack.screens

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
    val services by viewModel.allServices.observeAsState(emptyList())
    val currentService by viewModel.currentService.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val serviceDescription by viewModel.serviceDescription.observeAsState("")
    val servicePrice by viewModel.servicePrice.observeAsState(0.0)
    val updateResult by viewModel.updateResult.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllServices()
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
            Text(text = "Elige un servicio", style = MaterialTheme.typography.h6)

            var expanded by remember { mutableStateOf(false) }

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = currentService?.title ?: "Selecciona un servicio",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                        }
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    services.forEach { service ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.setCurrentService(service)
                                expanded = false
                            }
                        ) {
                            Text(service.title)
                        }
                    }
                }
            }

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
                onClick = {
                    viewModel.createServiceForProfessional()
                    currentService?.let { viewModel.updateService(it.id) }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
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
