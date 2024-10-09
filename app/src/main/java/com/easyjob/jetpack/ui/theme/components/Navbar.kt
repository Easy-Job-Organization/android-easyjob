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
fun BottomNavItem(
    icon: ImageVector,
    descriptionIcon: String,
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = descriptionIcon,
            tint = if (isSelected) Color(0xff4091b6) else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            color = if (isSelected) Color(0xff4091b6) else Color.Gray
        )
    }
}


@Composable
fun BottomNavBar(nestedNavController: NavController = rememberNavController()) {
    var selectedIndex by remember { mutableStateOf(0) }

    NavigationBar {
        NavigationBarItem(
            selected = selectedIndex == 0,
            onClick = {
                selectedIndex = 0
                nestedNavController.navigate("search") {
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            label = { Text(text = "Buscar") }
        )
        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = {
                selectedIndex = 1
                nestedNavController.navigate("appointments") {
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.DateRange, contentDescription = "Citas") },
            label = { Text(text = "Citas") }
        )
        NavigationBarItem(
            selected = selectedIndex == 2,
            onClick = {
                selectedIndex = 2
                nestedNavController.navigate("messages") {
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Filled.Send, contentDescription = "Mensajes") },
            label = { Text(text = "Mensajes") }
        )
        NavigationBarItem(
            selected = selectedIndex == 3,
            onClick = {
                selectedIndex = 3
                nestedNavController.navigate("profile") {
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text(text = "Perfil") }
        )
    }
}


