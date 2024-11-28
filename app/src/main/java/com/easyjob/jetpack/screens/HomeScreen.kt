package com.easyjob.jetpack.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.easyjob.jetpack.ui.theme.components.BottomNavBar

@Composable
fun HomeScreen(
    navController: NavController
) {

    LaunchedEffect(Unit) {
        val navGraph = navController.graph
        for (destination in navGraph) {
            Log.d("Destination", "${destination.route}")
        }
    }

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
        // Configuración de navegación de la pantalla principal
        NavHost(
            navController = nestedNavController,
            startDestination = "search",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("search") { SearchScreen(nestedNavController) }
            composable("appointments") { AppointmentScreen(nestedNavController, nestedNavController) }
            composable("messages") { MessageScreen(navController) }
            composable("profile") { ProfileScreen(generalNavController =  navController, clientNavController = nestedNavController) }
            composable("appointment/{id}", arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )) {entry ->
                val id = entry.arguments?.getString("id")
                AppointmentDetailsScreen(navController = nestedNavController, id = id?: "")
            }
            composable("editProfile") {
                EditClientProfileScreen(
                    nestedNavController
                )
            }
            composable("registerDate/{id}", arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )) { entry ->
                val id = entry.arguments?.getString("id")
                RegisterDateScreen(id = id ?: "", nestedNavController)
            }
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
            composable("likes") { LikesProfessionalScreen(nestedNavController)}
            composable("reviews") { ReviewsScreen(nestedNavController)}
            composable("map/{speciality}") { entry ->
                val speciality = entry.arguments?.getString("speciality")
                MapScreen(speciality ?: "")
            }
        }
    }
}
