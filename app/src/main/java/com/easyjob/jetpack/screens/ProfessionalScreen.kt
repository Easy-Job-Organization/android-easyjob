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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.easyjob.jetpack.ui.theme.components.ButtonSection
import com.easyjob.jetpack.ui.theme.components.CommentsCard
import com.easyjob.jetpack.ui.theme.components.FilterCard
import com.easyjob.jetpack.ui.theme.components.InformationCard
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.ProfileSection
import com.easyjob.jetpack.ui.theme.components.SecondaryButton
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.ProfessionalProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalProfileScreen(
    navController: NavController = rememberNavController(),
    professionalProfileViewModel: ProfessionalProfileViewModel = viewModel(),
    id: String,
) {

    val professionalState by professionalProfileViewModel.professional.observeAsState()
    val servicesState by professionalProfileViewModel.services.observeAsState()
    val reviewsState by professionalProfileViewModel.reviews.observeAsState()
    Log.e(">>>", professionalState.toString())
    Log.e(">>>", servicesState.toString())
    Log.e(">>>", reviewsState.toString())

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(id) {
        professionalProfileViewModel.fetchProfessional(id)
        professionalProfileViewModel.fetchServicesOfProfessinal(id)
        professionalProfileViewModel.fetchReviewsOfProfessinal(id)
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

            ProfileSection(
                image = professionalState?.photo_url,
                descriptionImage = "profile photo",
                name = "${professionalState?.name} ${professionalState?.last_name}",
                cityCountry = "Cali, Colombia",
                iconSize = 16,
                stars = 4,
                comments = "444"
            )
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

                    FilterCard(
                        icon = Icons.Sharp.Lock,
                        descriptionIcon = "Electrodomésticos",
                        iconSize = 16,
                        text = "Electrodomésticos",
                        color = Color(0xff133c55),
                        backgroundColor = Color(0x32133c55)
                    )

                    FilterCard(
                        icon = Icons.Sharp.Lock,
                        descriptionIcon = "Plomería",
                        iconSize = 16,
                        text = "Plomería",
                        color = Color(0xff133c55),
                        backgroundColor = Color(0x32133c55)
                    )

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