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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.ShoppingCart
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.ActionCard
import com.easyjob.jetpack.ui.theme.components.ButtonIconLink
import com.easyjob.jetpack.ui.theme.components.ProfileSection
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.ProfileViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController = rememberNavController(),
    profileViewModel: ProfileViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val profileState by profileViewModel.profileState.observeAsState()
    val profileData by profileViewModel.profile.observeAsState()

    LaunchedEffect(Unit) {
        profileViewModel.loadProfile("d53096cc-b538-41fd-9e6d-fd98b22a2765")
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Topbar(
                title = "Mi perfil",
                icon = Icons.Default.Edit,
                onEditClick = {},
                scrollBehavior = scrollBehavior,
                isBack = false
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 15.dp, vertical = 5.dp)
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
                    profileData?.let { profile ->
                        ProfileSection(
                            image = profile.photo_url ?: "https://example.com/default_profile.jpg",
                            descriptionImage = "profile image",
                            name = profile.name ?: "Nombre no disponible",
                            phoneNumber = profile.phone_number ?: "Numero no disponible",
                            cities = profile.cities ?: listOf(),
                            iconSize = 14,
                            stars = -1,
                            comments = ""
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
                                onClick = { /*TODO*/ }
                            )
                            ActionCard(
                                image = com.easyjob.jetpack.R.drawable.easyjobplus,
                                descriptionImage = "Easy job +",
                                title = "Easy job +",
                                onClick = { /*TODO*/ }
                            )
                            ActionCard(
                                image = com.easyjob.jetpack.R.drawable.history_toggle_off,
                                descriptionImage = "Historial",
                                title = "Historial",
                                onClick = { /*TODO*/ }
                            )
                        }

                        Box(modifier = Modifier.height(15.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            ButtonIconLink(
                                icon = Icons.Default.LocationOn,
                                descriptionIcon = "Mis direcciones",
                                onClick = { /*TODO*/ },
                                text = "Mis direcciones",
                                color = Color.Black
                            )
                            ButtonIconLink(
                                icon = Icons.Rounded.ShoppingCart,
                                descriptionIcon = "Mis medios de pago",
                                onClick = { /*TODO*/ },
                                text = "Mis medios de pago",
                                color = Color.Black
                            )
                            ButtonIconLink(
                                icon = Icons.Default.Settings,
                                descriptionIcon = "Configuración",
                                onClick = { /*TODO*/ },
                                text = "Configuración",
                                color = Color.Black
                            )
                            ButtonIconLink(
                                icon = Icons.Outlined.Person,
                                descriptionIcon = "Administrar cuenta",
                                onClick = { /*TODO*/ },
                                text = "Administrar cuenta",
                                color = Color.Black
                            )
                            ButtonIconLink(
                                icon = Icons.Default.LocationOn,
                                descriptionIcon = "Legal",
                                onClick = { /*TODO*/ },
                                text = "Legal",
                                color = Color.Black
                            )
                            ButtonIconLink(
                                icon = Icons.Default.ExitToApp,
                                descriptionIcon = "Cerrar sesión",
                                onClick = { /*TODO*/ },
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