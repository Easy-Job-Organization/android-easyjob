package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.sharp.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.ui.theme.components.ActionCard
import com.easyjob.jetpack.ui.theme.components.ButtonIconLink
import com.easyjob.jetpack.ui.theme.components.FilterCard
import com.easyjob.jetpack.ui.theme.components.ProfileSection
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.ProfessionalProfileViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalProfileScreen(
    navController: NavController = rememberNavController(),
    id: String? = "NO_ID",
    viewModel: ProfessionalProfileViewModel = viewModel()
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())


    val profile by viewModel.professionalProfile.observeAsState()
    val city by viewModel.city.observeAsState()
    val commentsCount by viewModel.commentsCount.observeAsState(0)
    val specialities by viewModel.specialities.observeAsState(emptyList())
    val profileState by viewModel.profileState.observeAsState(0)


    LaunchedEffect(id) {
        id?.let {
            viewModel.loadProfessionalProfile(it)
            viewModel.loadCity(it)
            viewModel.loadCommentsCount(it)
            viewModel.loadSpecialities(it)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            Topbar(
                title = "Perfil del profesional",
                icon = Icons.Default.FavoriteBorder,
                onEditClick = {},
                scrollBehavior = scrollBehavior,
                navController = navController,
                isBack = true
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 17.dp, vertical = 8.dp),
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
                    profile?.let { professional: Professional ->
                        ProfileSection(
                            image = professional.photo_url,
                            descriptionImage = professional.description,
                            name = professional.name,
                            cityCountry = city ?: "Ciudad desconocida",
                            iconSize = 16,
                            stars = professional.score.toDouble().roundToInt(),
                            comments = commentsCount.toString()
                        )
                    }

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
                }
            }

            Column(modifier = Modifier
                .padding(vertical = 14.dp)
                .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    specialities.forEach { speciality ->
                        FilterCard(
                            icon = Icons.Sharp.Lock, // Cambiar por un ícono relevante si es necesario
                            descriptionIcon = speciality.name,
                            iconSize = 16,
                            text = speciality.name,
                            color = Color(0xff133c55),
                            backgroundColor = Color(0x32133c55)
                        )
                    }
                }
            }
        }
    }
}