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
import androidx.compose.material.icons.automirrored.filled.ContactSupport
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material.icons.rounded.Plumbing
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
import androidx.hilt.navigation.compose.hiltViewModel
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
    generalNavController: NavController = rememberNavController(),
    innerNavController: NavController = rememberNavController(),
    viewModel: ProfessionalProfileViewModel = hiltViewModel()
) {


    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val profile by viewModel.professionalProfile.observeAsState()

    //val city by viewModel.city.observeAsState()

    val commentsCount by viewModel.commentsCount.observeAsState(0)
    val specialities by viewModel.specialities.observeAsState(emptyList())
    val profileState by viewModel.profileState.observeAsState(0)


    LaunchedEffect(Unit) {
            viewModel.loadProfessionalProfile()
            //viewModel.loadCity(it)
            viewModel.loadCommentsCount()
            viewModel.loadSpecialities()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            Topbar(
                title = "Perfil del profesional",
                scrollBehavior = scrollBehavior,
                navController = innerNavController,
                isBack = false
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
                            phoneNumber = professional.phone_number,
                            cities = professional.cities ?: listOf(),
                            iconSize = 16,
                            stars = professional.score.toDouble().roundToInt(),
                            comments = commentsCount.toString()
                        )
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
                            profile?.specialities?.forEach { speciality ->
                                FilterCard(
                                    descriptionIcon = speciality.speciality_name ?: "NO_SPECIALTY_NAME",
                                    iconSize = 16,
                                    text = speciality.speciality_name?: "NO_SPECIALTY_NAME",
                                    color = Color(0xff133c55),
                                    backgroundColor = Color(0x32133c55),
                                    click = false
                                )
                            }
                        }
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
                            icon = Icons.Rounded.Plumbing,
                            descriptionIcon = "Mis Servicios",
                            onClick = {
                                innerNavController.navigate("editServices")
                            },
                            text = "Mis Servicios",
                            color = Color.Black
                        )
                        ButtonIconLink(
                            icon = Icons.Default.Settings,
                            descriptionIcon = "Configuración",
                            onClick = { innerNavController.navigate("editProfile") },
                            text = "Editar perfil",
                            color = Color.Black
                        )
                        ButtonIconLink(
                            icon = Icons.Outlined.RateReview,
                            descriptionIcon = "Reseñas sobre mi",
                            onClick = { /*TODO*/ },
                            text = "Reseñas sobre mi",
                            color = Color.Black
                        )
                        ButtonIconLink(
                            icon = Icons.AutoMirrored.Filled.ContactSupport,
                            descriptionIcon = "Preguntas",
                            onClick = { /*TODO*/ },
                            text = "Preguntas",
                            color = Color.Black
                        )
                        ButtonIconLink(
                            icon = Icons.Default.ExitToApp,
                            descriptionIcon = "Cerrar sesión",
                            onClick = {
                                viewModel.logOut()
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
                }
            }


        }
    }
}