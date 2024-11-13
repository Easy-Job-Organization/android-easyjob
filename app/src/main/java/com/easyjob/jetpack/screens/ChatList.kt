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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
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
import com.easyjob.jetpack.ui.theme.components.ChatCard
import com.easyjob.jetpack.ui.theme.components.SearchBar
import com.easyjob.jetpack.ui.theme.components.Topbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatList(
    navController: NavController = rememberNavController(),
) {
    var searchText by remember { mutableStateOf("") }

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
        )
    )

    val filteredProfessionals = if (searchText.isEmpty()) {
        professionals
    } else {
        professionals.filter {
            it.name.contains(searchText, ignoreCase = true) ||
                    it.last_name.contains(searchText, ignoreCase = true) ||
                    it.description.contains(searchText, ignoreCase = true)
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Topbar(
                title = "Chats",
                scrollBehavior = scrollBehavior,
                isBack = true,
            )
        }
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

                SearchChat(
                    hint = "Buscar chat",
                    prevText = searchText,
                    onValueChange = { searchText = it },
                    navController = navController,
                    width = 260
                )

                Box(modifier = Modifier.width(20.dp))

                Row(
                    modifier = Modifier.width(80.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

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
                modifier = Modifier.padding(vertical = 14.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(filteredProfessionals) { professional ->
                    ChatCard(
                        id = (professional.id.toIntOrNull() ?: 0).toString(),
                        image = painterResource(R.drawable.ic_launcher_background).toString(),
                        descriptionImage = "Profile photo",
                        name = "${professional.name} ${professional.last_name}",
                        profession = professional.description,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun SearchChat(
    hint: String,
    prevText: String = "",
    navController: NavController = rememberNavController(),
    width: Int? = null,
    onValueChange: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf(prevText) }

    Box(
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(50))
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onValueChange(it)
            },
            shape = RoundedCornerShape(50),
            singleLine = true,
            modifier = Modifier.then(
                if (width != null) {
                    Modifier.width(width.dp)
                } else {
                    Modifier.fillMaxWidth()
                }
            ),
            leadingIcon = {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray
                )
            },
            placeholder = {
                Text(text = hint, fontSize = 16.sp)
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(20, 152, 213),
                unfocusedIndicatorColor = Color(20, 152, 213),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
        )
    }
}