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
    val specialities by resourcesViewModel.specialities.observeAsState(listOf())

    var selectedCity by remember { mutableStateOf("") }
    var selectedSpeciality by remember { mutableStateOf("") }

    val authState by registerViewModel.authState.observeAsState()

    var uri by remember { mutableStateOf<Uri?>(null) }
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

    LaunchedEffect(authState) {
        if (authState == 3) {
            navController.navigate("splash") {
                popUpTo("register") { inclusive = true }
            }
        } else if (authState == 2) {
            Toast.makeText(context, "Hubo un error al registrarte", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(selectedOption) {
        if (selectedOption == "Profesional") {
            resourcesViewModel.getCities()
            resourcesViewModel.getSpecialities()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        if (authState == 1) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                SinglePhotoPicker(uri = uri) { newUri -> uri = newUri }

                Input(value = name, label = "Nombre", onValueChange = { name = it })
                Input(value = last_name, label = "Apellidos", onValueChange = { last_name = it })
                Input(value = email, label = "Correo", onValueChange = { email = it })
                Input(
                    value = phone_number,
                    label = "Teléfono",
                    onValueChange = { phone_number = it })
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
                        options = specialities.map { it.speciality_name },
                        selectedOption = selectedSpeciality,
                        onOptionSelected = { selectedSpeciality = it },
                        placeholder = "Selecciona una especialidad"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                PrimaryButton(
                    text = "Registrarse",
                    onClick = {
                        if (uri == null) {
                            Toast.makeText(
                                context,
                                "Por favor selecciona una foto",
                                Toast.LENGTH_SHORT
                            )
                                .show()
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
                                val speciality_id =
                                    specialities.find { it.speciality_name == selectedSpeciality }?.id.toString()
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
                                    speciality_id,
                                    context.contentResolver,
                                )
                            }
                        }
                    },
                    width = 250
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 16.dp),
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
        shape = RoundedCornerShape(8.dp),
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