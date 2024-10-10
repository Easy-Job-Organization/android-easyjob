package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.CardSearch

@Composable
fun SearchScreen(navController: NavController = rememberNavController()) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Text(text = "Prueba")

            CardSearch(
                id = "1",
                image = "",
                descriptionImage = "Photo",
                name = "Pepito Pérez Hernández",
                stars = 5,
                comments = "20",
                navController = navController
            )
        }

    }

}