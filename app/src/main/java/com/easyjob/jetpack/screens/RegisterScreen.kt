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
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.easyjob.jetpack.R
import com.easyjob.jetpack.ui.theme.components.DropdownMenu1
import com.easyjob.jetpack.ui.theme.components.Input
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.TextButton
import com.easyjob.jetpack.viewmodels.RegisterViewModel
import com.easyjob.jetpack.viewmodels.ResourcesViewModel

@Composable
fun RegisterScreen(
    navController: NavController = rememberNavController(),
    registerViewModel: RegisterViewModel = hiltViewModel(),
    resourcesViewModel: ResourcesViewModel = viewModel(),
    context: Context = LocalContext.current
) {
    var name by remember { mutableStateOf("") }
    var last_name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone_number by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val options = listOf("Cliente", "Profesional")
    var selectedOption by remember { mutableStateOf("Selecciona una opción") }

    val cities by resourcesViewModel.cities.observeAsState(listOf())
    val services by resourcesViewModel.services.observeAsState(listOf())
    val languages by resourcesViewModel.languages.observeAsState(listOf())
    val specialities by resourcesViewModel.specialities.observeAsState(listOf())

    var selectedCity by remember { mutableStateOf("") }
    var selectedService by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf("") }
    var selectedSpeciality by remember { mutableStateOf("") }

    val authState by registerViewModel.authState.observeAsState()

    var uri by remember { mutableStateOf<Uri?>(null) }
    var permissionsGranted by remember { mutableStateOf(false) }
    var photoError by remember { mutableStateOf(false) }

    RequestMediaPermissions(
        onPermissionsGranted = {
            permissionsGranted = true
            Log.d("RegisterScreen", "Permisos de medios otorgados")
        },
        onPermissionsDenied = {
            Log.d("RegisterScreen", "Permisos de medios denegados")
        }
    )

    // Navegación al home si el registro es exitoso
    LaunchedEffect(authState) {
        if (authState == 3) {
            navController.navigate("splash") {
                popUpTo("register") { inclusive = true }
            }
        }
    }

    // Obtener recursos de la API al seleccionar Profesional
    LaunchedEffect(selectedOption) {
        if (selectedOption == "Profesional") {
            resourcesViewModel.getCities()
            resourcesViewModel.getLanguages()
            resourcesViewModel.getServices()
            resourcesViewModel.getSpecialities()
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
        Row(modifier = Modifier.weight(2f)) {}

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Selección de foto
            SinglePhotoPicker(uri = uri) { newUri ->
                uri = newUri
                photoError = false
            }

            if (photoError) {
                Text(
                    text = "Por favor selecciona una foto",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Campos de registro
            Input(value = name, label = "Nombre", onValueChange = { name = it })
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

                DropdownMenu1(
                    options = cities.map { it.city_name },
                    selectedOption = selectedCity,
                    onOptionSelected = { selectedCity = it },
                    placeholder = "Selecciona tu ciudad"
                )

                DropdownMenu1(
                    options = languages.map { it.language_name },
                    selectedOption = selectedLanguage,
                    onOptionSelected = { selectedLanguage = it },
                    placeholder = "Selecciona tu idioma"
                )

                DropdownMenu1(
                    options = specialities.map { it.speciality_name },
                    selectedOption = selectedSpeciality,
                    onOptionSelected = { selectedSpeciality = it },
                    placeholder = "Selecciona una especialidad"
                )

                DropdownMenu1(
                    options = services.map { it.title },
                    selectedOption = selectedService,
                    onOptionSelected = { selectedService = it },
                    placeholder = "Selecciona un servicio"
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            // Registro con la URI de la imagen y datos de usuario
            PrimaryButton(
                text = "Registrarse",
                onClick = {
                    if (uri == null) {
                        photoError = true
                        return@PrimaryButton
                    }

                    uri?.let { photo ->
                        if (selectedOption == "Cliente") {
                            registerViewModel.signUpClient(
                                name,
                                last_name,
                                email,
                                phone_number,
                                password,
                                selectedOption,
                                photo,
                                context.contentResolver
                            )
                        } else {
                            val language_id =
                                languages.find { it.language_name == selectedLanguage }?.id.toString()
                            val speciality_id =
                                specialities.find { it.speciality_name == selectedSpeciality }?.id.toString()
                            val service_id =
                                services.find { it.title == selectedService }?.id.toString()
                            val city_id =
                                cities.find { it.city_name == selectedCity }?.id.toString()


                            registerViewModel.signUpProfessional(
                                name,
                                last_name,
                                email,
                                phone_number,
                                password,
                                selectedOption,
                                photo,
                                city_id,
                                language_id,
                                service_id,
                                speciality_id,
                                context.contentResolver
                            )
                        }
                    }
                },
                width = 250
            )
        }

        // Indicador de carga o mensaje de error
        when (authState) {
            1 -> CircularProgressIndicator()
            2 -> Text("Hubo un error", color = Color.Red)
        }

        Box(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "¿Ya tienes cuenta?")
                TextButton(
                    text = "Inicia sesión",
                    onClick = {
                        navController.navigate("login")
                    }
                )
            }
        }
        Box(modifier = Modifier.height(48.dp))
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
            .size(100.dp)
            .clip(CircleShape)
    )

    Button(
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