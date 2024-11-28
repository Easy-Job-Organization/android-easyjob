package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ContactSupport
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.ActionCard
import com.easyjob.jetpack.ui.theme.components.ButtonIconLink
import com.easyjob.jetpack.ui.theme.components.InfoAlert
import com.easyjob.jetpack.ui.theme.components.ProfileSectionClient
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    generalNavController: NavController,
    clientNavController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {

    var showHelpInfo by remember {
        mutableStateOf(false)
    }

    var showInProgressInfo by remember {
        mutableStateOf(false)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val profileState by profileViewModel.profileState.observeAsState()
    val profileData by profileViewModel.profile.observeAsState()

    LaunchedEffect(Unit) {
        profileViewModel.loadProfile()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Topbar(
                title = "Mi perfil",
                scrollBehavior = scrollBehavior,
                isBack = false
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 15.dp, vertical = 5.dp)
                .fillMaxSize()
        ) {
            when (profileState) {
                1 -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                2 -> {
                    Text("Hubo un error al cargar el perfil", color = Color.Red)
                }
                3 -> {

                    if(showHelpInfo){
                        InfoAlert(
                            title = "Ayuda",
                            info = "Comunicate con nosotros al correo:\n\ncontacto@easyjob.com",
                            onAccept = {showHelpInfo=false}
                        )
                    }

                    if(showInProgressInfo){
                        InfoAlert(
                            title = "Funcion en desarrollo",
                            info = "Agradecemos tu interes\n\nPronto tendremos esta funcion disponible",
                            onAccept = {showInProgressInfo=false}
                        )
                    }
                    profileData?.let { profile ->
                        ProfileSectionClient(
                            image = profile.photo_url ?: "https://example.com/default_profile.jpg",
                            descriptionImage = "profile image",
                            name = profile.name ?: "Nombre no disponible",
                            phoneNumber = profile.phone_number ?: "Numero no disponible",
                            email = profile.email ?: "Correo no disponible",
                        )

                        Box(modifier = Modifier.height(15.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ActionCard(
                                image = com.easyjob.jetpack.R.drawable.ayuda,
                                descriptionImage = "Ayuda",
                                title = "Ayuda",
                                onClick = { showHelpInfo=true }
                            )
                            ActionCard(
                                image = com.easyjob.jetpack.R.drawable.easyjobplus,
                                descriptionImage = "Easy job +",
                                title = "EasyJob+",
                                onClick = { showInProgressInfo=true }
                            )
                            ActionCard(
                                image = com.easyjob.jetpack.R.drawable.history_toggle_off,
                                descriptionImage = "Historial",
                                title = "Historial",
                                onClick = { showInProgressInfo=true }
                            )
                        }

                        Box(modifier = Modifier.height(15.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                           /* ButtonIconLink(
                                icon = Icons.Default.LocationOn,
                                descriptionIcon = "Mis direcciones",
                                onClick = { *//*TODO*//* },
                                text = "Mis direcciones",
                                color = Color.Black
                            )*/
                            ButtonIconLink(
                                icon = Icons.Default.Settings,
                                descriptionIcon = "Configuración",
                                onClick = { clientNavController.navigate("editProfile") },
                                text = "Editar perfil",
                                color = Color.Black
                            )
                            ButtonIconLink(
                                icon = Icons.Rounded.Favorite,
                                descriptionIcon = "Mis profesionales favoritos",
                                onClick = {
                                    clientNavController.navigate("likes")
                                },
                                text = "Favoritos",
                                color = Color.Black
                            )
                            ButtonIconLink(
                                icon = Icons.Outlined.RateReview,
                                descriptionIcon = "Mis reseñas",
                                onClick = {
                                    clientNavController.navigate("reviews")
                                },
                                text = "Mis reseñas",
                                color = Color.Black
                            )
                            /*ButtonIconLink(
                                icon = Icons.AutoMirrored.Filled.ContactSupport,
                                descriptionIcon = "Mis preguntas",
                                onClick = { *//*TODO*//* },
                                text = "Mis preguntas",
                                color = Color.Black
                            )*/
                            ButtonIconLink(
                                icon = Icons.Default.ExitToApp,
                                descriptionIcon = "Cerrar sesión",
                                onClick = {
                                    profileViewModel.logOut()
                                    generalNavController.navigate("splash"){
                                        //Dont let the user go back to the previous screens
                                        popUpTo("splash") {
                                            inclusive = true
                                        }
                                    }
                                },
                                text = "Cerrar sesión",
                                color = Color.Red
                            )
                        }
                    } ?: run {
                        Text("Perfil no disponible", color = Color.Gray)
                    }
                }
            }
        }
    }
}