package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.viewmodels.ProfessionalViewModel

@Composable
fun SearchScreen(
    navController: NavController = rememberNavController(),
    professionalViewModel: ProfessionalViewModel = viewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 200.dp)
        ) {
            PrimaryButton(
                text = "prueba",
                onClick = {
                    val professionalId = "8cd3aed3-c2a7-42a2-a5d7-d47afa0ae9fd"
                    navController.navigate("professionalProfileClient/$professionalId")
                },
                width = 200
            )
        }

    }

}