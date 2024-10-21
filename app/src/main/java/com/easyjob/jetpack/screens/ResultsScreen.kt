package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    searchScreenViewModel: SearchScreenViewModel = viewModel()
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        searchScreenViewModel.loadSearchResults("Cali",searchText)
    }

    val searchResults by searchScreenViewModel.searchResult.observeAsState(null)

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

            Text("${searchResults?.total.toString()} resultados encontrados", fontSize = 32.sp, modifier = Modifier.padding(start = 15.dp, bottom = 15.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth()
            ) {
                searchResults?.data?.forEach { card ->
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
