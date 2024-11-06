package com.easyjob.jetpack.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavBar(nestedNavController: NavController = rememberNavController()) {
    var selectedIndex by remember { mutableStateOf(0) }
    val selectedColor = Color(0xff4091b6)

    NavigationBar {
        NavigationBarItem(
            selected = selectedIndex == 0,
            onClick = {
                selectedIndex = 0
                nestedNavController.navigate("search") {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = if (selectedIndex == 0) selectedColor else Color.Black
                ) },
            label = {
                Text(
                    text = "Buscar",
                    color = if (selectedIndex == 0) selectedColor else Color.Black
                )
            }
        )
        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = {
                selectedIndex = 1
                nestedNavController.navigate("appointments") {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Citas",
                    tint = if (selectedIndex == 1) selectedColor else Color.Black
                ) },
            label = {
                Text(
                    text = "Citas",
                    color = if (selectedIndex == 1) selectedColor else Color.Black
                )
            }
        )
        NavigationBarItem(
            selected = selectedIndex == 2,
            onClick = {
                selectedIndex = 2
                nestedNavController.navigate("chatList") {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = "Mensajes",
                    tint = if (selectedIndex == 2) selectedColor else Color.Black
                ) },
            label = {
                Text(
                    text = "Mensajes",
                    color = if (selectedIndex == 2) selectedColor else Color.Black
                )
            }
        )
        NavigationBarItem(
            selected = selectedIndex == 3,
            onClick = {
                selectedIndex = 3
                nestedNavController.navigate("profile") {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Perfil",
                    tint = if (selectedIndex == 3) selectedColor else Color.Black
                ) },
            label = {
                Text(
                    text = "Perfil",
                    color = if (selectedIndex == 3) selectedColor else Color.Black
                )
            }
        )
    }
}


