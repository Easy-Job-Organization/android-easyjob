package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.R
import com.easyjob.jetpack.models.City
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.ui.theme.components.BottomNavBar
import com.easyjob.jetpack.ui.theme.components.CardSearch
import com.easyjob.jetpack.ui.theme.components.SearchBar
import com.easyjob.jetpack.ui.theme.components.Topbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatList(
    navController: NavController = rememberNavController(),
) {

    val searchText = ""

    //data quemada en código
    val professionals = listOf(
        Professional(
            id = "1",
            name = "John",
            last_name = "Doe",
            email = "john@example.com",
            phone_number = "123456789",
            photo_url = "https://example.com/photo1.jpg",
            roles = listOf("Engineer"),
            cities = listOf(City("1", "New York")),
            score = "4.5",
            description = "Experienced engineer"
        ),
        Professional(
            id = "2",
            name = "Jane",
            last_name = "Smith",
            email = "jane@example.com",
            phone_number = "987654321",
            photo_url = "https://example.com/photo2.jpg",
            roles = listOf("Designer"),
            cities = listOf(City("2", "Los Angeles")),
            score = "4.0",
            description = "Creative designer"
        ),
        Professional(
            id = "2",
            name = "Jane",
            last_name = "Smith",
            email = "jane@example.com",
            phone_number = "987654321",
            photo_url = "https://example.com/photo2.jpg",
            roles = listOf("Designer"),
            cities = listOf(City("2", "Los Angeles")),
            score = "4.0",
            description = "Creative designer"
        ),
        Professional(
            id = "2",
            name = "Jane",
            last_name = "Smith",
            email = "jane@example.com",
            phone_number = "987654321",
            photo_url = "https://example.com/photo2.jpg",
            roles = listOf("Designer"),
            cities = listOf(City("2", "Los Angeles")),
            score = "4.0",
            description = "Creative designer"
        ),
    )

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

        topBar = {
            Topbar(
                title = "Chats",
                scrollBehavior = scrollBehavior,
                isBack = true,
            )
        },

        bottomBar = { BottomNavBar(nestedNavController = navController) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 17.dp, vertical = 8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                SearchBar("Buscar chat", searchText, navController= navController, width = 260)

                Box(modifier = Modifier.width(20.dp))

                Row(modifier = Modifier.width(80.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Rounded.Create,
                            contentDescription = "Crear chat",
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Ajustes",
                            modifier = Modifier.size(30.dp)
                        )
                    }


                }

            }

            Text(
                text = "Técnicos disponibles",
                fontSize = 20.sp,
                color = Color(0xff424242),
                modifier = Modifier
                    .padding(vertical = 14.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxSize(),
            ) {

                items(professionals) { professional ->

                    CardSearch(
                        id = (professional.id.toIntOrNull() ?: 0).toString(),
                        image = painterResource(R.drawable.ic_launcher_background).toString(),
                        descriptionImage = "Profile photo",
                        name = "${professional.name} ${professional.last_name}",
                        stars = professional.score.toDoubleOrNull()?.toInt() ?: 0,
                        navController = navController
                    )

                }

            }

        }

    }

}