package com.easyjob.jetpack.screens

import android.util.Log
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
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.ui.theme.components.ButtonSection
import com.easyjob.jetpack.ui.theme.components.CommentsCard
import com.easyjob.jetpack.ui.theme.components.FilterCard
import com.easyjob.jetpack.ui.theme.components.InformationCard
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.ProfileSection
import com.easyjob.jetpack.ui.theme.components.SecondaryButton
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.ProfessionalProfileViewModel
import com.easyjob.jetpack.viewmodels.ProfessionalViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalClientScreen(
    navController: NavController = rememberNavController(),
    professionalViewModel: ProfessionalViewModel = viewModel(),
    professionalProfileViewModel: ProfessionalProfileViewModel = viewModel(),
    id: String,
) {

    val professionalState by professionalViewModel.professional.observeAsState()
    val servicesState by professionalViewModel.services.observeAsState()
    val reviewsState by professionalViewModel.reviews.observeAsState()
    val city by professionalProfileViewModel.city.observeAsState()
    //val commentsCount by professionalProfileViewModel.commentsCount.observeAsState(0)

    val loading by professionalViewModel.loading.observeAsState()
    val error by professionalViewModel.errorMessage.observeAsState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(id) {
        professionalViewModel.fetchProfessional(id)
        professionalProfileViewModel.loadCity(id)
        professionalProfileViewModel.loadCommentsCount(id)
        professionalViewModel.fetchServicesOfProfessinal(id)
        professionalViewModel.fetchReviewsOfProfessinal(id)
    }

    var activeSection by remember {
        mutableStateOf(true)
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

            when {
                loading == true -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                error != null && error!!.isNotEmpty() -> {
                    Text(text = error ?: "Error desconocido", color = Color.Red)
                }

                loading == false -> {
                    professionalState?.let { professional: Professional ->
                        ProfileSection(
                            image = professional.photo_url,
                            descriptionImage = "profile photo",
                            name = "${professional.name} ${professional.last_name}",
                            cityCountry = city ?: "Ciudad desconocida",
                            iconSize = 16,
                            stars = professional.score?.toInt() ?: 0, //ajustar para el score del tecnico
                            comments = "",
                        )
                    }

                    Column(modifier = Modifier
                        .padding(vertical = 14.dp)
                        .wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        servicesState?.let { services ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                services.forEach { service ->
                                    service?.let {
                                        FilterCard(
                                            icon = Icons.Sharp.Lock,
                                            descriptionIcon = service.title,
                                            iconSize = 16,
                                            text = service.title,
                                            color = Color(0xff133c55),
                                            backgroundColor = Color(0x32133c55)
                                        )
                                    }
                                }
                            }
                        }

                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            PrimaryButton(text = "Agendar cita", onClick = { /*TODO*/ })
                            SecondaryButton(text = "Enviar mensaje", onClick = { /*TODO*/ })

                        }

                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ButtonSection(active = activeSection, text = "Información", onClick = { activeSection = !activeSection }, width = 200)
                        ButtonSection(active = !activeSection, text = "Opiniones", onClick = { activeSection = !activeSection }, width = 200)
                    }
                    Box(modifier = Modifier.height(10.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (activeSection) {
                            servicesState?.let { InformationCard(services = it) }
                        } else {
                            reviewsState?.let { CommentsCard(reviews = it) }
                        }
                    }
                }
            }

        }

    }

}