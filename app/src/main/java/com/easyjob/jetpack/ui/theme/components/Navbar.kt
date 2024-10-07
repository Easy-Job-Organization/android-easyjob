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
fun BottomNavBar(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }

    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White,
        contentColor = Color(0xff4091b6)
    ) {
        BottomNavItem(
            icon = Icons.Default.Search,
            descriptionIcon = "Buscar",
            isSelected = selectedIndex == 0,
            text = "Buscar",
            onClick = {
                selectedIndex = 0
                navController.navigate("search")
            }
        )
        BottomNavItem(
            icon = Icons.Default.DateRange,
            descriptionIcon = "Citas",
            isSelected = selectedIndex == 1,
            text = "Citas",
            onClick = {
                selectedIndex = 1
                navController.navigate("appointments")
            }
        )
        BottomNavItem(
            icon = Icons.Default.Send,
            descriptionIcon = "Mensajes",
            isSelected = selectedIndex == 2,
            text = "Mensajes",
            onClick = {
                selectedIndex = 2
                navController.navigate("messages")
            }
        )
        BottomNavItem(
            icon = Icons.Default.Person,
            descriptionIcon = "Perfil",
            isSelected = selectedIndex == 3,
            text = "Perfil",
            onClick = {
                selectedIndex = 3
                navController.navigate("profile")
            }
        )
    }
}


