package com.easyjob.jetpack.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.easyjob.jetpack.R
import com.easyjob.jetpack.ui.theme.components.AppointmentCard
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.AppointmentViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AppointmentScreen(
    navController: NavController = rememberNavController(),
    innerNavController: NavController = rememberNavController(),
    appointmentViewModel: AppointmentViewModel = hiltViewModel()
) {

    var selectedOption by remember { mutableStateOf("Selecciona una opción") }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val profileState by appointmentViewModel.profileState.observeAsState(0)


    LaunchedEffect(Unit) {
        appointmentViewModel.loadAppointments()
    }
    val pendingAppointments by appointmentViewModel.pendingAppointments.observeAsState(emptyList())
    val acceptedAppointments by appointmentViewModel.acceptedAppointments.observeAsState(emptyList())
    val cancelledAppointments by appointmentViewModel.cancelledAppointments.observeAsState(emptyList())
    val role by appointmentViewModel.role.observeAsState("")

    var selectedFilter by remember { mutableStateOf("Pendientes") }

    // Determinar la lista actual en función del filtro seleccionado
    val filteredAppointments = when (selectedFilter) {
        "Pendientes" -> pendingAppointments
        "Aceptadas" -> acceptedAppointments
        "Terminadas" -> cancelledAppointments
        else -> pendingAppointments
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            Topbar(
                title = "Citas",
                scrollBehavior = scrollBehavior,
                navController = navController,
                isBack = false,
            )
        },
    ) { innerPadding ->

        when (profileState) {
            1 -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            2 -> {
                Text("Hubo un error al los chats del perfil", color = Color.Red)
            }

            3 -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        listOf("Pendientes", "Aceptadas", "Terminadas").forEach { filter ->
                            Chip(
                                onClick = { selectedFilter = filter },
                                colors = ChipDefaults.chipColors(
                                    backgroundColor = if (selectedFilter == filter) Color(0xff3b82f6) else Color(0x32133c55),
                                )
                            ){
                                Text(
                                    text = filter,
                                    color = if (selectedFilter == filter) Color.White else Color(0xff133c55),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    fontWeight = if (selectedFilter == filter) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (filteredAppointments.isEmpty()) {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 30.dp, start = 24.dp, end = 24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    AsyncImage(
                                        model = R.drawable.screwdriver_icon,
                                        contentDescription = "Easyjob logo",
                                        modifier = Modifier
                                            .size(120.dp)
                                            .alpha(0.4f),
                                    )
                                    Text(
                                        text = "Todavia no tienes ninguna cita",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        color = Color(0xff424242),
                                    )
                                    Text(
                                        text = "Cuando tengas una cita programada, aparecerá aquí",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Light,
                                        textAlign = TextAlign.Center,
                                        color = Color(0xff96989e),
                                    )
                                }
                            }
                        } else {
                            items(filteredAppointments) { appointment ->
                                val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                                val parsedDate = LocalDate.parse(appointment!!.date, inputFormatter)
                                val formattedDate = parsedDate.format(outputFormatter)
                                Box(
                                    modifier = Modifier.padding(
                                        start = 14.dp,
                                        end = 14.dp,
                                        top = 14.dp
                                    )
                                ) {
                                    if (role.equals("client")) {
                                        AppointmentCard(
                                            id = appointment.id,
                                            name = "${appointment.professional?.name} ${appointment.professional?.last_name}",
                                            service = appointment.service,
                                            date = formattedDate,
                                            hour = appointment.hour,
                                            photo_url = appointment.professional?.photo_url ?: "",
                                            navController = innerNavController
                                        )
                                    } else {
                                        AppointmentCard(
                                            id = appointment.id,
                                            name = "${appointment.client?.name} ${appointment.client?.last_name}",
                                            service = appointment.service,
                                            date = formattedDate,
                                            hour = appointment.hour,
                                            photo_url = appointment.client?.photo_url ?: "",
                                            navController = innerNavController
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
