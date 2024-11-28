package com.easyjob.jetpack.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.easyjob.jetpack.ui.theme.components.BottomNavBar
import com.easyjob.jetpack.ui.theme.components.BottomProfessionalNavBar
import com.easyjob.jetpack.ui.theme.components.Topbar

@Composable
fun HomeProfessionalScreen(
    navController: NavController
) {

    LaunchedEffect(Unit) {
        val navGraph = navController.graph
        for (destination in navGraph) {
            Log.d("Destination", "${destination.route}")
        }
    }

    val nestedNavController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomProfessionalNavBar(nestedNavController = nestedNavController)
        }
    ) { innerPadding ->

        NavHost(
            navController = nestedNavController,
            startDestination = "profile",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("profile") { ProfessionalProfileScreen(navController, nestedNavController) }
            composable("search") { SearchScreen(nestedNavController) }
            composable("appointments") { AppointmentScreen(navController, nestedNavController) }
            composable("messages") { MessageScreen(navController) }
            composable("editServices") { EditServicesScreen(nestedNavController) }
            composable("appointment/{id}", arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )) {entry ->
                val id = entry.arguments?.getString("id")
                AppointmentDetailsScreen(navController = nestedNavController, id = id?: "")
            }
            composable("editProfile") {
                EditProfessionalProfileScreen(
                    nestedNavController
                )
            }
            composable("createService") { CreateServiceScreen(nestedNavController) }
            composable("editService/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                EditServiceScreen(nestedNavController, serviceId = id)
            }
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

