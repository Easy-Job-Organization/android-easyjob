package com.easyjob.jetpack.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Lock
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.easyjob.jetpack.R
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.ui.theme.components.CardSearch
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.viewmodels.ProfessionalViewModel
import com.easyjob.jetpack.ui.theme.components.FilterCard
import com.easyjob.jetpack.ui.theme.components.SearchBar
import com.easyjob.jetpack.viewmodels.ProfessionalProfileViewModel
import com.easyjob.jetpack.viewmodels.SearchScreenViewModel
import kotlin.math.floor

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController = rememberNavController(), // Initialize with the appropriate context
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        searchScreenViewModel.loadProfessionalCards()
    }

    val professionalCards by searchScreenViewModel.professionalCards.observeAsState(emptyList())
    val userName by searchScreenViewModel.userName.observeAsState("")

    val searchSpecialities = listOf(
        "Carpintero",
        "Plomero",
        "Pintor",
        "Electricista",
        "Limpiador",
        "Albañil",
        "Cerrajero",
        "Jardinero",
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding),
        ) {

            Column(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .align(Alignment.Start)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.easyjob_logo_main_color),
                    contentDescription = "Easyjob logo",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(start = 25.dp),
                )

                Row( modifier = Modifier.padding(start = 20.dp)) {
                    Text("Hola ", fontSize = 28.sp)
                    Text(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff3B82F6),
                        text ="$userName",
                        fontSize = 28.sp)
                }



            }

            SearchBar(
                "Encuentra un técnico a tu medida",
                navController = navController,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 15.dp)
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 28.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                searchSpecialities.forEach { specialityName ->
                    FilterCard(
                        descriptionIcon = specialityName,
                        iconSize = 16,
                        text = specialityName,
                        color = Color(0xff133c55),
                        backgroundColor = Color(0x32133c55),
                        navController = navController,
                        click = true
                    )
                }
            }

                Text(
                    "Destacados",
                    fontSize = 28.sp,
                    modifier = Modifier.padding(start = 24.dp, bottom = 24.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxWidth()
                ) {
                    professionalCards.forEach { card ->
                        CardSearch(
                            id = card.id,
                            image = card.photo_url,
                            descriptionImage = "Profile photo",
                            name = card.name + " " + card.last_name,
                            stars = card.score?.toInt() ?: 0, //Pasar a double las estrellas
                            navController = navController,
                            cities = card.cities.map { it.city_name }

                        )
                    }
                }

            }

        }
    }