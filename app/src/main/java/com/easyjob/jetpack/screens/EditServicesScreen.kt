package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import java.text.DecimalFormat
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.EditServicesViewModel
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditServicesScreen(
    navController: NavController = rememberNavController(),
    viewModel: EditServicesViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val services by viewModel.services.observeAsState(emptyList())
    val loading by viewModel.loading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState("")

    LaunchedEffect(Unit) {
        viewModel.fetchServicesOfProfessinal()
    }

    Scaffold(
        topBar = {
            Topbar(
                title = "Mis servicios",
                scrollBehavior = scrollBehavior,
                navController = navController,
                isBack = true
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                loading -> {
                    CircularProgressIndicator()
                }

                errorMessage.isNotEmpty() -> {
                    Text(
                        text = "Error: $errorMessage",
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center
                    )
                }

                services.isEmpty() -> {
                    Text(
                        text = "No hay servicios disponibles",
                        textAlign = TextAlign.Center
                    )
                }

                else -> {
                    ServiceList(
                        services = services,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun ServiceList(
    services: List<Service?>,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(services) { service ->
            service?.let {
                ServiceItem(
                    service = service,
                    onEditService = {navController.navigate("editService/${service.id}")},
                    onDeleteService = {}
                )
            }
        }
    }
}

@Composable
fun ServiceItem(
    service: Service,
    onEditService: (Service) -> Unit,
    onDeleteService: (Service) -> Unit
) {
    val decimalFormat = remember { DecimalFormat("#,###") }

    val roundedPrice = floor(service.price / 10).toInt() * 10

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Información del servicio
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = service.title,
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = service.description,
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Formateo del precio con separadores de miles
                val formattedPrice = decimalFormat.format(roundedPrice)  // Se aplica el formato con comas
                Text(
                    text = "Precio: $$formattedPrice",  // Muestra el precio formateado
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.Bold,
                    )
                )
            }

            // Botones de edición y eliminación, uno encima del otro
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = { onEditService(service) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar servicio")
                }
                IconButton(onClick = { onDeleteService(service) }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar servicio",
                        tint = MaterialTheme.colors.error
                    )
                }
            }
        }
    }
}