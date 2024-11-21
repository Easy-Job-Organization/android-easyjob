package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.models.Appointment
import com.easyjob.jetpack.ui.theme.components.DateTimePicker
import com.easyjob.jetpack.ui.theme.components.DescriptionTextArea
import com.easyjob.jetpack.ui.theme.components.DropdownMenu1
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.SecondaryButton
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.AppointmentViewModel
import com.easyjob.jetpack.viewmodels.CreateAppointmentDTO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterDateScreen(
    id: String,
    navController: NavController = rememberNavController(),
    registerAppointmentViewModel: AppointmentViewModel = hiltViewModel()
){
    var selectedOption by remember { mutableStateOf("Selecciona una opción") }
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(Unit) {
        registerAppointmentViewModel.loadProfessionalServices(id)
    }

    val professionalServices by registerAppointmentViewModel.professionalServices.observeAsState(emptyList())

    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),


        topBar = {
            Topbar(
                title = "Agenda tu cita",
                scrollBehavior = scrollBehavior,
                navController = navController,
                isBack = true,
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(top = 50.dp, start = 15.dp, end = 15.dp)
        ) {
            Text(
                "Seleccionar un Servicio",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 15.dp)
            )
            DropdownMenu1(
                options = professionalServices.map { it?.title ?: "" },
                selectedOption = selectedOption,
                onOptionSelected = {selectedOption = it}
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Descripción de la solicitud",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 15.dp)
            )

            DescriptionTextArea(
                description = "Describe tu avería o inconveniente a solucionar"
            ) {

            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Selecciona fecha y hora",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 15.dp)
            )

            DateTimePicker(
                selectedDate = selectedDate,
                selectedTime = selectedTime,
                onDateSelected = { selectedDate = it },
                onTimeSelected = { selectedTime = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(){
                SecondaryButton(text = "Cancelar", onClick = { navController.popBackStack()}, width = 200)
                Spacer(modifier = Modifier.width(16.dp))
                PrimaryButton(
                    text = "Agendar",
                    onClick = {
                        val nwAppointment = CreateAppointmentDTO(
                            date = selectedDate,
                            location = "Cali",
                            hour = selectedTime,
                            service = professionalServices.find { it?.title == selectedOption }?.id.toString(),
                            client = "",
                            professional = id
                            )
                        registerAppointmentViewModel.createDate(nwAppointment)
                        navController.popBackStack()
                              },
                    width = 200)
            }

        }

    }
}