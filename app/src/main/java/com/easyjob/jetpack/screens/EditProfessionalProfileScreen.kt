package com.easyjob.jetpack.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.easyjob.jetpack.R
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
    val updateResult by viewModel.updateResult.observeAsState(null) // Manejar éxito o error

    var selectedImageUri by remember { mutableStateOf<Uri?>(professionalImage) }
    var expandedCities by remember { mutableStateOf(false) }
    var expandedSpecialities by remember { mutableStateOf(false) }

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
            SinglePhotoPickerA(uri = selectedImageUri) { newUri ->
                selectedImageUri = newUri
                viewModel.onProfessionalImageChange(newUri)
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
            DropdownField(
                label = "Ciudad",
                options = cities.map { it.city_name },
                selectedIndex = cities.indexOfFirst { it.id == cityId },
                onOptionSelected = { index ->
                    viewModel.onCityIdChange(cities[index].id)
                },
                expanded = expandedCities,
                onExpandedChange = { expandedCities = it }
            )

            // Dropdown para seleccionar especialidad
            DropdownField(
                label = "Especialidad",
                options = specialities.map { it.speciality_name },
                selectedIndex = specialities.indexOfFirst { it.id == specialityId },
                onOptionSelected = { index ->
                    viewModel.onSpecialityIdChange(specialities[index].id)
                },
                expanded = expandedSpecialities,
                onExpandedChange = { expandedSpecialities = it }
            )

            // Botón para guardar cambios
            Button(
                onClick = {
                    viewModel.editProfessionalProfile(selectedImageUri, context.contentResolver)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }

            // Mostrar mensaje según el resultado de la actualización
            updateResult?.let { isSuccess ->
                Text(
                    text = if (isSuccess) "Perfil actualizado con éxito" else "Error al actualizar el perfil",
                    color = if (isSuccess)  MaterialTheme.colors.primary else MaterialTheme.colors.error
                )
            }
        }
    }
}

@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = if (selectedIndex >= 0) options[selectedIndex] else "Selecciona una $label",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            label = { Text(label) },
            trailingIcon = {
                IconButton(onClick = { onExpandedChange(!expanded) }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(index)
                        onExpandedChange(false)
                    }
                ) {
                    Text(option)
                }
            }
        }
    }
}

@Composable
fun SinglePhotoPickerA(uri: Uri?, onUriChange: (Uri?) -> Unit) {
    val defaultImage = R.drawable.default_profile
    val context = LocalContext.current

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { newUri ->
            onUriChange(newUri)
        }
    )

    val painter = if (uri != null) {
        rememberAsyncImagePainter(uri)
    } else {
        rememberAsyncImagePainter(defaultImage)
    }

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
    )

    androidx.compose.material3.Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff3b82f6),
            contentColor = Color.White
        ),
        modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically)
            .height(38.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp, // Elevación normal
            pressedElevation = 8.dp // Elevación cuando se presiona
        ),
        onClick = {
            // Verifica si los permisos han sido otorgados
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                singlePhotoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                Toast.makeText(
                    context,
                    "Se necesitan permisos para acceder a la galería.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("SinglePhotoPicker", "Se necesitan permisos para acceder a la galería.")
            }
        }
    ) {
        androidx.compose.material3.Text(text = "Selecciona una foto")
    }
}