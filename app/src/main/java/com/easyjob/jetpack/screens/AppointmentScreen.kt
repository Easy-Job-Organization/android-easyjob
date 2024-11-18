package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.AppointmentCard
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.AppointmentViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentScreen(
    navController: NavController = rememberNavController(),
    appointmentViewModel: AppointmentViewModel = hiltViewModel()
) {

    var selectedOption by remember { mutableStateOf("Selecciona una opción") }
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(Unit) {
        appointmentViewModel.loadClientAppointments()
    }

    val clientAppointments by appointmentViewModel.clientAppointments.observeAsState(emptyList())

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 25.dp)
                .verticalScroll(scrollState), // Make this column scrollable
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth()
            ) {
                clientAppointments?.forEach { appointment ->
                    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                    val parsedDate = LocalDate.parse(appointment!!.date, inputFormatter)
                    val formattedDate = parsedDate.format(outputFormatter)
                    AppointmentCard(
                        id = appointment!!.id,
                        name = "${appointment.professional?.name} ${appointment.professional?.last_name}",
                        service = appointment.service,
                        date = formattedDate,
                        hour = appointment.hour,
                        photo_url = appointment.professional?.photo_url ?: ""
                    )
                }
            }
        }
    }
}
