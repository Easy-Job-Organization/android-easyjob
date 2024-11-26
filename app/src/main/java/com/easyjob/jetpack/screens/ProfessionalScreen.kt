package com.easyjob.jetpack.screens

import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.easyjob.jetpack.viewmodels.ProfessionalClientViewModel
import com.easyjob.jetpack.viewmodels.ProfessionalProfileViewModel
import com.easyjob.jetpack.viewmodels.ProfessionalViewModel
import com.easyjob.jetpack.viewmodels.ReviewViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalClientScreen(
    navController: NavController = rememberNavController(),
    professionalViewModel: ProfessionalViewModel = viewModel(),
    professionalProfileViewModel: ProfessionalClientViewModel = hiltViewModel(),
    reviewViewModel: ReviewViewModel = hiltViewModel(),
    id: String,
) {
    var showReviewDialog by remember { mutableStateOf(false) }
    var showLikeDialog by remember { mutableStateOf(false) }
    var hasSubmittedReview by remember { mutableStateOf(false) }
    var previousScore by remember { mutableDoubleStateOf(0.0) }
    var previousComment by remember { mutableStateOf("") }

    val professionalState by professionalViewModel.professional.observeAsState()
    val servicesState by professionalViewModel.services.observeAsState()
    val reviewsState by professionalViewModel.reviews.observeAsState() // Todas las reseñas del profesional
    //val city by professionalProfileViewModel.city.observeAsState()
    val commentsCount by professionalProfileViewModel.commentsCount.observeAsState(0)

    val loading by professionalViewModel.loading.observeAsState()
    val error by professionalViewModel.errorMessage.observeAsState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val reviewState by reviewViewModel.state.collectAsState()
    val oldReview by reviewViewModel.oldReview.collectAsState()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    reviewViewModel.checkSubmittedReview(reviewsState)

    LaunchedEffect(reviewState) {
        when (reviewState) {
            is ReviewViewModel.ReviewState.Success -> {
                Toast.makeText(context, "¡Reseña enviada con éxito!", Toast.LENGTH_SHORT).show()
                showReviewDialog = false
            }

            is ReviewViewModel.ReviewState.Error -> {
                Toast.makeText(
                    context,
                    (reviewState as ReviewViewModel.ReviewState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
                showReviewDialog = false
            }

            else -> Unit
        }
    }

    LaunchedEffect(id) {
        professionalViewModel.fetchProfessional(id)
        //professionalProfileViewModel.loadCity(id)
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
                onEditClick = {
                    showLikeDialog = true
                },
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

            if (showReviewDialog) {
                ReviewDialog(
                    professionalId = id,
                    initialScore = previousScore,
                    initialComment = previousComment,
                    onDismissRequest = { showReviewDialog = false },
                    onReviewSubmitted = { }
                )
            }

            if (showLikeDialog) {
                LikeDialog(
                    professionalId = id,
                    onDismissRequest = { showLikeDialog = false },
                    onLikeSubmitted = { showLikeDialog = false }
                )
            }

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
                            phoneNumber = professional.phone_number,
                            cities = professional.cities ?: listOf(),
                            iconSize = 16,
                            stars = professional.score?.toDouble()?.roundToInt()
                                ?: 0, //ajustar para el score del tecnico
                            comments = commentsCount.toString(),
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(vertical = 14.dp)
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        professionalState?.specialities?.let { specialities ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                specialities.forEach { speciality ->
                                    speciality?.let {
                                        FilterCard(
                                            descriptionIcon = speciality.speciality_name,
                                            iconSize = 16,
                                            text = speciality.speciality_name,
                                            color = Color(0xff133c55),
                                            backgroundColor = Color(0x32133c55),
                                            click = false
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

                            PrimaryButton(
                                text = "Agendar cita",
                                onClick = {
                                    navController.navigate("registerDate/${id}")
                                }
                            )

                            SecondaryButton(
                                text = "Enviar mensaje",
                                onClick = { navController.navigate("chat/$id") }
                            )
                            SecondaryButton(
                                text = if (oldReview != null) "Editar opinión" else "Escribir una opinión",
                                onClick = {
                                    previousScore = oldReview?.first ?: 0.0
                                    previousComment = oldReview?.second ?: ""
                                    showReviewDialog = true
                                }
                            )

                        }

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ButtonSection(
                                active = activeSection,
                                text = "Información",
                                onClick = { activeSection = !activeSection },
                                width = 220
                            )
                            ButtonSection(
                                active = !activeSection,
                                text = "Opiniones",
                                onClick = { activeSection = !activeSection },
                                width = 220
                            )
                        }
                        Box(modifier = Modifier.height(10.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            if (activeSection) {
                                servicesState?.let { InformationCard(services = it) }
                            } else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {

                                    Text(
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 22.sp,
                                        color = Color(0xFF133c55),
                                        text = "Opiniones totales: ${reviewsState?.size ?: 0}",
                                    )

                                    /*SecondaryButton(
                                    text = "Escribir una opinión",
                                    onClick = { showReviewDialog = true }
                                )*/

                                    reviewsState?.let {
                                        CommentsCard(reviews = it)
                                    }

                                }

                            }
                        }
                    }
                }

            }

        }

    }
}