package com.easyjob.jetpack.screens
/*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.EditServiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditServiceScreen(
    navController: NavController = rememberNavController(),
    viewModel: EditServiceViewModel = viewModel(),
    serviceId: String
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val serviceName by viewModel.serviceName.collectAsState()
    val serviceDescription by viewModel.serviceDescription.collectAsState()
    val servicePrice by viewModel.servicePrice.collectAsState()

    Scaffold(
        topBar = {
            Topbar(
                title = "Editar servicio",
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = serviceName,
                onValueChange = { viewModel.onServiceNameChange(it) },
                label = { Text("Nombre del servicio") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = serviceDescription,
                onValueChange = { viewModel.onServiceDescriptionChange(it) },
                label = { Text("Describe brevemente el servicio a ofrecer") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            OutlinedTextField(
                value = servicePrice,
                onValueChange = { viewModel.onServicePriceChange(it) },
                label = { Text("Cantidad a cobrar por el servicio") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = { viewModel.updateService(serviceId) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }
        }
    }
}
*/