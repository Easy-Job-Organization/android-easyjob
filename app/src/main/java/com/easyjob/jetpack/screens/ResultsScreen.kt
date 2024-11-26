package com.easyjob.jetpack.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.CardSearch
import com.easyjob.jetpack.ui.theme.components.SearchBar
import com.easyjob.jetpack.ui.theme.components.Topbar
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
                    .fillMaxWidth()
                    ,
                verticalAlignment = Alignment.CenterVertically

            ){
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = "Ingresar"
                    )
                }
                SearchBar("Encuentra un técnico a tu medida", searchText, navController= navController)
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
