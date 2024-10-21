package com.easyjob.jetpack.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.easyjob.jetpack.R
import com.easyjob.jetpack.ui.theme.components.CardSearch
import com.easyjob.jetpack.ui.theme.components.FilterCard
import com.easyjob.jetpack.ui.theme.components.SearchBar

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController = rememberNavController()) {
    val scrollState = rememberScrollState()

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

            Column (modifier = Modifier
                .padding(top = 50.dp)
                .align(Alignment.Start)){
                AsyncImage(
                    model = "https://media.tutellus.com/libraries/45/01/lib/1360445882784.jpg",
                    contentDescription = "Logos",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(100.dp)
                        .padding(start = 10.dp),
                    error = painterResource(R.drawable.ic_launcher_background)
                )

                Text("Hola.", fontSize = 32.sp, modifier = Modifier.padding(start = 20.dp))

            }

            SearchBar("Encuentra un técnico a tu medida")

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                ,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                FilterCard(
                    icon = Icons.Sharp.Lock,
                    descriptionIcon = "Electrodomésticos",
                    iconSize = 16,
                    text = "Electrodomésticos",
                    color = Color(0xff133c55),
                    backgroundColor = Color(0x32133c55),
                    navController = navController
                )

                FilterCard(
                    icon = Icons.Sharp.Lock,
                    descriptionIcon = "Plomería",
                    iconSize = 16,
                    text = "Plomería",
                    color = Color(0xff133c55),
                    backgroundColor = Color(0x32133c55),
                    navController = navController
                )
                FilterCard(
                    icon = Icons.Sharp.Lock,
                    descriptionIcon = "Electricista",
                    iconSize = 16,
                    text = "Electricista",
                    color = Color(0xff133c55),
                    backgroundColor = Color(0x32133c55),
                    navController = navController
                )

                FilterCard(
                    icon = Icons.Sharp.Lock,
                    descriptionIcon = "Aseo",
                    iconSize = 16,
                    text = "Aseo",
                    color = Color(0xff133c55),
                    backgroundColor = Color(0x32133c55),
                    navController = navController
                )
                FilterCard(
                    icon = Icons.Sharp.Lock,
                    descriptionIcon = "Pintura",
                    iconSize = 16,
                    text = "Pintura",
                    color = Color(0xff133c55),
                    backgroundColor = Color(0x32133c55),
                    navController = navController
                )

                FilterCard(
                    icon = Icons.Sharp.Lock,
                    descriptionIcon = "Carpinteria",
                    iconSize = 16,
                    text = "Carpinteria",
                    color = Color(0xff133c55),
                    backgroundColor = Color(0x32133c55),
                    navController = navController

                )
            }

            Text("Destacados", fontSize = 32.sp, modifier = Modifier.padding(start = 15.dp, bottom = 15.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth()
            ) {
                CardSearch(
                    id = "1",
                    image = "",
                    descriptionImage = "Photo",
                    name = "Pepito Pérez Hernández",
                    stars = 5,
                    comments = "20",
                    navController = navController
                )

                CardSearch(
                    id = "2",
                    image = "",
                    descriptionImage = "Photo",
                    name = "Leonardo Bustamante",
                    stars = 1,
                    comments = "230",
                    navController = navController
                )
                CardSearch(
                    id = "1",
                    image = "",
                    descriptionImage = "Photo",
                    name = "Pepito Pérez Hernández",
                    stars = 5,
                    comments = "20",
                    navController = navController
                )

                CardSearch(
                    id = "2",
                    image = "",
                    descriptionImage = "Photo",
                    name = "Leonardo Bustamante",
                    stars = 1,
                    comments = "230",
                    navController = navController
                )
                CardSearch(
                    id = "1",
                    image = "",
                    descriptionImage = "Photo",
                    name = "Pepito Pérez Hernández",
                    stars = 5,
                    comments = "20",
                    navController = navController
                )

                CardSearch(
                    id = "2",
                    image = "",
                    descriptionImage = "Photo",
                    name = "Leonardo Bustamante",
                    stars = 1,
                    comments = "230",
                    navController = navController
                )
            }

        }

    }

}