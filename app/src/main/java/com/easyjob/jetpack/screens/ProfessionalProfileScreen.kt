package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.easyjob.jetpack.Service
import com.easyjob.jetpack.ui.theme.components.ButtonSection
import com.easyjob.jetpack.ui.theme.components.InformationCard
import com.easyjob.jetpack.ui.theme.components.ProfileSection

@Composable
fun ProfessionalProfileScreen() {



    var activeSection by remember {
        mutableStateOf(true)
    }

    val profesiones = listOf(
        Service(
            title = "Electricista",
            descriptions = listOf("Montaje de una red electrica en un apartamento pequeno"),
            prices = listOf(45900.0)
        ),
        Service(
            title = "Plomeria",
            descriptions = listOf("Asesoria en fugas de agua de viviendas", "Recoga su mangera que no sirve"),
            prices = listOf(70000.0, 45000.0)
        )
    )


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {

            ProfileSection(
                image = "https://e7.pngegg.com/pngimages/324/645/png-clipart-pokeball-pokeball-thumbnail.png",
                descriptionImage = "mclovin",
                name = "Sebastián Escobar Marín",
                cityCountry = "Cali, Colombia",
                stars = 4,
                comments = "444"
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                ButtonSection(active = activeSection, text = "Información", onClick = { activeSection = !activeSection }, width = 185)
                ButtonSection(active = !activeSection, text = "Información", onClick = { activeSection = !activeSection }, width = 185)
            }
            Box(modifier = Modifier.height(10.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                if (activeSection) {
                    InformationCard(services = profesiones)
                }
            }

        }

    }

}