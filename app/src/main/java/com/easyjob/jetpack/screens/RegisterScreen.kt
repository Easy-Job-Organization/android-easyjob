package com.easyjob.jetpack.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.easyjob.jetpack.R
import com.easyjob.jetpack.ui.theme.components.DropdownMenu1
import com.easyjob.jetpack.ui.theme.components.EasyJobLogo
import com.easyjob.jetpack.ui.theme.components.Input
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.TextButton
import com.easyjob.jetpack.viewmodels.RegisterViewModel

@Composable
fun RegisterScreen(
    navController: NavController = rememberNavController(),
    registerViewModel: RegisterViewModel = viewModel(),
    context: Context = LocalContext.current
) {
    var name by remember { mutableStateOf("") }
    var last_name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone_number by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val options = listOf("Cliente", "Profesional")
    var selectedOption by remember { mutableStateOf("Selecciona una opción") }
    val authState by registerViewModel.authState.observeAsState()

    var uri by remember { mutableStateOf<Uri?>(null) }
    var permissionsGranted by remember { mutableStateOf(false) }

    RequestMediaPermissions(
        onPermissionsGranted = {
            permissionsGranted = true // Actualiza el estado cuando los permisos son otorgados
            Log.d("RegisterScreen", "Permisos de medios otorgados")
        },
        onPermissionsDenied = {
            Log.d("RegisterScreen", "Permisos de medios denegados")
        }
    )

    LaunchedEffect(authState) {
        Log.d("RegisterScreen", "authState changed to $authState")
        if (authState == 3) { // Trigger navigation on success
            navController.navigate("home") {
                popUpTo("register") { inclusive = true } // Remove register from back stack
            }
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier.weight(2f)) { }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Mostrar el componente de selección de foto siempre
            SinglePhotoPicker(uri = uri) { newUri ->
                uri = newUri
            }

            Box(modifier = Modifier.height(16.dp))

            Input(
                value = name, label = "Nombre", onValueChange = { name = it })
            Input(value = last_name, label = "Apellidos", onValueChange = { last_name = it })
            Input(value = email, label = "Correo", onValueChange = { email = it })
            Input(value = phone_number, label = "Teléfono", onValueChange = { phone_number = it })
            Input(
                value = password,
                label = "Contraseña",
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation()
            )
            DropdownMenu1(
                options = options,
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it }
            )

            // Campos adicionales para profesionales
            if (selectedOption == "Profesional") {
                val services = listOf("Carpinteria", "Plomeria", "Electricista") // Obtener de la DB
                val languages = listOf("Español", "Inglés", "Francés") // Obtener de la DB
                val cities = listOf("Bogotá", "Medellín", "Cali") // Obtener de la DB
                val specialties = listOf("Especialidad 1", "Especialidad 2") // Obtener de la DB

                var selectedService by remember { mutableStateOf("") }
                var selectedLanguage by remember { mutableStateOf("") }
                var selectedCity by remember { mutableStateOf("") }
                var selectedSpecialty by remember { mutableStateOf("") }

                DropdownMenu1(
                    options = services,
                    selectedOption = selectedService,
                    onOptionSelected = { selectedService = it }
                )

                DropdownMenu1(
                    options = languages,
                    selectedOption = selectedLanguage,
                    onOptionSelected = { selectedLanguage = it }
                )

                DropdownMenu1(
                    options = cities,
                    selectedOption = selectedCity,
                    onOptionSelected = { selectedCity = it }
                )

                DropdownMenu1(
                    options = specialties,
                    selectedOption = selectedSpecialty,
                    onOptionSelected = { selectedSpecialty = it }
                )
            }


            Box(modifier = Modifier.height(32.dp))

            PrimaryButton(
                text = "Registrarse",
                onClick = {
                    uri?.let {
                        registerViewModel.signUp(
                            name,
                            last_name,
                            email,
                            phone_number,
                            password,
                            selectedOption,
                            it,
                            context.contentResolver
                        )
                    }
                },
                width = 250
            )
        }

        when (authState) {
            1 -> CircularProgressIndicator()
            2 -> Text("Hubo un error", color = Color.Red)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .weight(1f),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = "¿Ya tienes cuenta?")
            TextButton(
                text = "Inicia sesión",
                onClick = { navController.navigate("login") }
            )
        }
    }
}


@Composable
fun SinglePhotoPicker(uri: Uri?, onUriChange: (Uri?) -> Unit) {
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
            .size(128.dp)
            .clip(CircleShape)
    )

    Button(onClick = {
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
    }) {
        Text(text = "Selecciona una foto")
    }
}


@Composable
fun RequestMediaPermissions(
    onPermissionsGranted: () -> Unit,
    onPermissionsDenied: () -> Unit
) {
    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allPermissionsGranted = permissions.entries.all { it.value }
        if (allPermissionsGranted) {
            onPermissionsGranted()
        } else {
            onPermissionsDenied()
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
                )
            )
        } else {
            permissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    RegisterScreen()
}