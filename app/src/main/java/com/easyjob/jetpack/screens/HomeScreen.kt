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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.easyjob.jetpack.ui.theme.components.BottomNavBar
import com.easyjob.jetpack.ui.theme.components.Topbar

@Composable
fun HomeScreen(navController: NavController = rememberNavController()) {
    val nestedNavController = rememberNavController()

    val currentBackStackEntry = nestedNavController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            if (currentRoute != null && !currentRoute.startsWith("chat")) {
                BottomNavBar(nestedNavController = nestedNavController)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = nestedNavController,
            startDestination = "search",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("search") { SearchScreen(nestedNavController) }
            composable("appointments") { AppointmentScreen(navController) }
            composable("messages") { MessageScreen(navController) }
            composable("profile") { ProfileScreen(navController) }
            composable("results/{search}", arguments = listOf(
                navArgument("search") { type = NavType.StringType }
            )) { entry ->
                val search = entry.arguments?.getString("search")
                ResultsScreen(searchText = search ?: "", nestedNavController)
            }
            composable("professionalProfileClient/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                ProfessionalClientScreen(nestedNavController, id = id)
            }
            composable("makeAppointment") { MakeAppointmentScreen(navController) }
            composable("listChat") { ChatList(nestedNavController) }
            composable(
                "chat/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { entry ->
                val id = entry.arguments?.getString("id") ?: ""
                Chat(nestedNavController, idProfessional = id)
            }
        }

    }

}