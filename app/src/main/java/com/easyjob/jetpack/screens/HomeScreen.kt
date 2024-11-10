package com.easyjob.jetpack.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.easyjob.jetpack.ui.theme.components.BottomNavBar

@Composable
fun HomeScreen(navController: NavController = rememberNavController()) {
    val nestedNavController = rememberNavController()


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { BottomNavBar(nestedNavController = nestedNavController) }
    ) { innerPadding ->
        // Mostrar diálogo de reseña
        DialogReview(nestedNavController)


        // Configuración de navegación de la pantalla principal
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
            composable("reviewScreen/{professionalId}") { backStackEntry ->
                val professionalId = backStackEntry.arguments?.getString("professionalId") ?: ""
                Log.d("HomeScreen", "professionalId: $professionalId")
                ReviewScreen(
                    professionalId = professionalId,
                    onCancel = { nestedNavController.popBackStack() },
                    onReviewSubmitted = { nestedNavController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun DialogReview(nestedNavController: NavController) {
    // Estado para controlar la visibilidad del diálogo
    var showReviewDialog by remember { mutableStateOf(true) } // Mostrar el diálogo al inicio

    // Mostrar el AlertDialog cuando se abre la app
    if (showReviewDialog) {
        AlertDialog(
            onDismissRequest = { showReviewDialog = false },
            title = { Text("¿Deseas dejar una reseña?") },
            text = { Text("¿Te gustaría dejar una reseña sobre el último servicio recibido?") },
            confirmButton = {
                Button(onClick = {
                    showReviewDialog = false
                    // Navegar a la pantalla de reseñas, pasando el ID del profesional (reemplaza "professionalId" por el ID real)
                    nestedNavController.navigate("reviewScreen/professionalId")
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(onClick = { showReviewDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}
