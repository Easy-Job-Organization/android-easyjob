package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.BottomNavBar
import com.easyjob.jetpack.ui.theme.components.Topbar

@Composable
fun HomeScreen(navController: NavController = rememberNavController()) {
    val nestedNavController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { BottomNavBar(nestedNavController = nestedNavController) }
    ) { innerPadding ->

        NavHost(navController = nestedNavController, startDestination = "search", modifier = Modifier.padding(innerPadding)) {
            composable("search"){ SearchScreen() }
            composable("appointments"){ AppointmentScreen() }
            composable("messages") { MessageScreen(navController) }
            composable("profile"){ ProfileScreen() }
        }

    }

}