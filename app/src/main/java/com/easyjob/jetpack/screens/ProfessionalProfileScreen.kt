package com.easyjob.jetpack.screens

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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.sharp.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.Comment
import com.easyjob.jetpack.Service
import com.easyjob.jetpack.ui.theme.components.ButtonSection
import com.easyjob.jetpack.ui.theme.components.CommentsCard
import com.easyjob.jetpack.ui.theme.components.FilterCard
import com.easyjob.jetpack.ui.theme.components.InformationCard
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.ProfileSection
import com.easyjob.jetpack.ui.theme.components.SecondaryButton
import com.easyjob.jetpack.ui.theme.components.Topbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalProfileScreen(navController: NavController = rememberNavController(), id: String? = "NO_ID") {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

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
        ),
        Service(
            title = "Plomeria",
            descriptions = listOf("Asesoria en fugas de agua de viviendas", "Recoga su mangera que no sirve"),
            prices = listOf(70000.0, 45000.0)
        ),
        Service(
            title = "Plomeria",
            descriptions = listOf("Asesoria en fugas de agua de viviendas", "Recoga su mangera que no sirve"),
            prices = listOf(70000.0, 45000.0)
        ),
    )


    val comentarios = listOf(
        Comment(
            name = "Marco Rosa",
            starts = 4,
            date = "06 Ago de 1945",
            description = "Montaje de una red electrica en un apartamento pequeno"
        ),
        Comment(
            name = "Nicolas Do Santos",
            starts = 1,
            date = "08 Sep de 2029",
            description = "Muito ruim, deixei ele sozinho por 5 minutos e ele levou comigo a caixa de dente da minha avó"
        ),
        Comment(
            name = "Byan Cafre",
            starts = 2,
            date = "30 Feb de 2012",
            description = "Se me llevó la licuadora, no lo contraten más"
        ),
        Comment(
            name = "Nicolas Maduro",
            starts = 2,
            date = "29 Nov de 2019",
            description = "Me obligó a pagarle más, y aparte de todo me robó ropa en la cara"
        ),
    )


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
                image = "https://e7.pngegg.com/pngimages/324/645/png-clipart-pokeball-pokeball-thumbnail.png",
                descriptionImage = "mclovin",
                name = "Sebastián Escobar Marín",
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
                ButtonSection(active = activeSection, text = "Información", onClick = { activeSection = !activeSection }, width = 185)
                ButtonSection(active = !activeSection, text = "Opiniones", onClick = { activeSection = !activeSection }, width = 185)
            }
            Box(modifier = Modifier.height(10.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                if (activeSection) {
                    InformationCard(services = profesiones)
                } else {
                    CommentsCard(comments = comentarios)
                }
            }

        }

    }

}