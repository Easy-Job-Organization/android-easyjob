package com.easyjob.jetpack.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.easyjob.jetpack.ui.theme.components.Input
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.CreateServiceDTO
import com.easyjob.jetpack.viewmodels.CreateServiceViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateServiceScreen(
    navController: NavController,
    viewModel: CreateServiceViewModel = hiltViewModel()
) {
    val services by viewModel.allServices.observeAsState(emptyList())
    //val currentService by viewModel.currentService.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    //val serviceDescription by viewModel.serviceDescription.observeAsState("")
    //val servicePrice by viewModel.servicePrice.observeAsState(0.0)
    val updateResult by viewModel.updateResult.observeAsState()

    var serviceName by remember { mutableStateOf("") }
    var serviceDescription by remember { mutableStateOf("") }
    var servicePrice by remember { mutableDoubleStateOf(0.0) }

    /*LaunchedEffect(Unit) {
        viewModel.fetchAllServices()
    }*/

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
            Text(text = "Nombre del Servicio", fontWeight = FontWeight.Bold)

            Box(modifier = Modifier.fillMaxWidth()) {
                Input(
                    value = serviceName,
                    onValueChange = { serviceName = it },
                    label = "Nombre del servicio"
                )
            }

            Text(
                text = "Descripción del servicio",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = serviceDescription,
                onValueChange = { serviceDescription = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp)),
                placeholder = { Text("Descripción breve del servicio") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )

            Text(
                text = "Precio del servicio",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            Input(
                value = servicePrice.toString(),
                onValueChange = { newValue: String ->
                    val price = newValue.toDoubleOrNull()
                    if (price != null) {
                        servicePrice = price
                    }
                },
                label = "Precio en COP",
                //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            PrimaryButton(
                onClick = {
                    viewModel.createServiceForProfessional(
                        CreateServiceDTO(
                            title = serviceName,
                            description = serviceDescription,
                            price = servicePrice
                        )
                    )
                    navController.popBackStack()
                },
                text = "Guardar Servicio"
            )

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
