package com.easyjob.jetpack.screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.EditProfessionalProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfessionalProfileScreen(
    navController: NavController,
    viewModel: EditProfessionalProfileViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val name by viewModel.name.observeAsState("")
    val lastName by viewModel.lastName.observeAsState("")
    val phoneNumber by viewModel.phoneNumber.observeAsState("")
    val cityId by viewModel.cityId.observeAsState("")
    val specialityId by viewModel.specialityId.observeAsState("")
    val professionalImage by viewModel.professionalImage.observeAsState(null)
    val specialities by viewModel.specialities.observeAsState(emptyList())
    val cities by viewModel.cities.observeAsState(emptyList())
    val updateResult by viewModel.updateResult.observeAsState(true)

    var selectedImageUri by remember { mutableStateOf<Uri?>(professionalImage) }

    LaunchedEffect(Unit) {
        viewModel.fetchCurrentProfessional()
        viewModel.fetchSpecialities()
        viewModel.fetchCities()
        selectedImageUri = professionalImage
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            Topbar(
                title = "Editar Perfil",
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Selección de foto
            SinglePhotoPicker(uri = selectedImageUri) { newUri ->
                selectedImageUri = newUri
                viewModel.onProfessionalImageChange(newUri) // Actualizar en el ViewModel
            }

            // Campo para el nombre
            OutlinedTextField(
                value = name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo para el apellido
            OutlinedTextField(
                value = lastName,
                onValueChange = viewModel::onLastNameChange,
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo para el teléfono
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = viewModel::onPhoneNumberChange,
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            // Dropdown para seleccionar ciudad
            Text(text = "Ciudad", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
            var expandedCities by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = cities.find { it.id == cityId }?.city_name ?: "Selecciona una ciudad",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    trailingIcon = {
                        IconButton(onClick = { expandedCities = !expandedCities }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                        }
                    }
                )
                DropdownMenu(
                    expanded = expandedCities,
                    onDismissRequest = { expandedCities = false }
                ) {
                    cities.forEach { city ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.onCityIdChange(city.id)
                                expandedCities = false
                            }
                        ) {
                            Text(city.city_name)
                        }
                    }
                }
            }

            // Dropdown para seleccionar especialidad
            Text(text = "Especialidad", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
            var expandedSpecialities by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = specialities.find { it.id == specialityId }?.speciality_name ?: "Selecciona una especialidad",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    trailingIcon = {
                        IconButton(onClick = { expandedSpecialities = !expandedSpecialities }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                        }
                    }
                )
                DropdownMenu(
                    expanded = expandedSpecialities,
                    onDismissRequest = { expandedSpecialities = false }
                ) {
                    specialities.forEach { speciality ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.onSpecialityIdChange(speciality.id)
                                expandedSpecialities = false
                            }
                        ) {
                            Text(speciality.speciality_name)
                        }
                    }
                }
            }

            // Botón para guardar cambios
            Button(
                onClick = {
                    viewModel.editProfessionalProfile(selectedImageUri, context.contentResolver)
                    if (updateResult) {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }

            // Mostrar error si la actualización falló
            if (!updateResult) {
                Text("Error al actualizar el perfil", color = MaterialTheme.colors.error)
            }
        }
    }
}