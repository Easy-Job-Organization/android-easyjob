package com.easyjob.jetpack.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.EditClientProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditClientProfileScreen(
    navController: NavController,
    viewModel: EditClientProfileViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val name by viewModel.name.observeAsState("")
    val lastName by viewModel.lastName.observeAsState("")
    val phoneNumber by viewModel.phoneNumber.observeAsState("")
    val clientImage by viewModel.clientImage.observeAsState(null)
    val updateResult by viewModel.updateResult.observeAsState(null)

    var selectedImageUri by remember { mutableStateOf<Uri?>(clientImage) }

    var permissionsGranted by remember { mutableStateOf(false) }

    RequestMediaPermissions(
        onPermissionsGranted = {
            permissionsGranted = true
            Log.d("RegisterScreen", "Permisos de medios otorgados")
        },
        onPermissionsDenied = {
            Log.d("RegisterScreen", "Permisos de medios denegados")
        }
    )

    // Muestra el Toast si la actualización fue exitosa
    LaunchedEffect(updateResult) {
        updateResult?.let { isSuccess ->
            if (isSuccess) {
                Toast.makeText(context, "Perfil actualizado con éxito", Toast.LENGTH_SHORT).show()
                navController.popBackStack() // Navegar hacia atrás al guardar
            } else {
                Toast.makeText(context, "Error al actualizar el perfil", Toast.LENGTH_SHORT).show()
            }

            // Restablece el estado después de manejarlo
            viewModel.resetUpdateResult()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchCurrentClient()
    }

    LaunchedEffect(clientImage) {
        if (clientImage != null) {
            selectedImageUri = clientImage
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            Topbar(
                title = "Editar Perfil de Cliente",
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
            // Selección de foto
            SinglePhotoPickerA(uri = selectedImageUri) { newUri ->
                selectedImageUri = newUri
                viewModel.onClientImageChange(newUri)
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

            // Separador
            Spacer(modifier = Modifier.height(16.dp))

            // Botón para guardar cambios
            PrimaryButton(
                text = "Guardar Cambios",
                onClick = {
                    viewModel.editClientProfile(selectedImageUri, context.contentResolver)
                },
                width = 250
            )
        }
    }
}
