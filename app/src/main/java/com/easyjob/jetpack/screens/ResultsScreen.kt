package com.easyjob.jetpack.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.CardSearch
import com.easyjob.jetpack.ui.theme.components.SearchBar
import com.easyjob.jetpack.viewmodels.SearchScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    searchText: String = "",
    navController: NavController = rememberNavController(),
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        val formattedText = searchText.trim().replaceFirstChar { it.uppercaseChar() }
        searchScreenViewModel.loadSearch(formattedText)
    }

    val searchResults by searchScreenViewModel.searchResult2.observeAsState(null)
    val markerList by searchScreenViewModel.markerList.observeAsState(emptyList())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(top = 50.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically

            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = "Ingresar"
                    )
                }
                SearchBar(
                    "Encuentra un técnico a tu medida",
                    searchText,
                    navController = navController,
                    modifier = Modifier.padding(
                        end = 15.dp,
                    )
                )
            }

            if (searchResults?.size == 0) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {

                    Icon(
                        imageVector = Icons.Outlined.WarningAmber,
                        contentDescription = "No professionals found",
                        tint = Color(0xFF5A5A5A),
                        modifier = Modifier
                            .size(150.dp)
                            .padding(bottom = 6.dp)
                    )

                    Text(
                        text = "No se han encontrado resultados",
                        color = Color(0xFF5A5A5A)
                    )

                }

            } else {

                MapButton(onClick = {
                    navController.navigate("map/$searchText")
                })

                Column(modifier = Modifier.padding(top = 20.dp, start = 15.dp, end = 15.dp)) {

                    Text(
                        text = "${searchResults?.size.toString()} resultados encontrados",
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(start = 15.dp, bottom = 15.dp),
                        fontWeight = FontWeight.Bold
                    )

                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxWidth()
                ) {

                    searchResults?.forEach { card ->
                        CardSearch(
                            id = card.id,
                            image = card.photo_url,
                            descriptionImage = "Profile photo",
                            name = card.name + card.last_name,
                            stars = card.score?.toInt() ?: 0, //Pasar a double las estrellas
                            navController = navController
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun MapButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    ) {
        Image(
            painter = painterResource(id = com.easyjob.jetpack.R.drawable.map),
            contentDescription = "Mapa",
            modifier = Modifier.fillMaxSize()
        )

        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF133c55)
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            ),
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = com.easyjob.jetpack.R.drawable.map_pin),
                    contentDescription = "Map Icon",
                    tint = Color(0xFF133c55),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ver en el mapa",
                    fontSize = 16.sp,
                    color = Color(0xFF133c55)
                )
            }
        }
    }
}
