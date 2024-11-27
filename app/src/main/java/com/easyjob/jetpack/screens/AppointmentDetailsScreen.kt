package com.easyjob.jetpack.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.easyjob.jetpack.R
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.SecondaryButton
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.AppointmentDetailsViewModel
import com.easyjob.jetpack.viewmodels.AppointmentViewModel
import com.easyjob.jetpack.viewmodels.CreateAppointmentDTO


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentDetailsScreen(
    navController: NavController = rememberNavController(),
    appointmentDetailsViewModel: AppointmentDetailsViewModel = hiltViewModel(),
    id: String
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val appointment by appointmentDetailsViewModel.appointment.observeAsState()
    val error by appointmentDetailsViewModel.error.observeAsState()
    val statusUpdateSuccess by appointmentDetailsViewModel.statusUpdateSuccess.observeAsState()
    val role by appointmentDetailsViewModel.role.observeAsState("")

    LaunchedEffect(Unit) {
        appointmentDetailsViewModel.loadAppointmentDetails(id)
        appointmentDetailsViewModel.getRole()
        Log.e("AppointmentDetailsScreen", "ROLE: $role")
    }




    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            Topbar(
                title = "Detalle de cita",
                scrollBehavior = scrollBehavior,
                navController = navController,
                isBack = true,
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            //TITULO
            appointment?.service?.let {
                Text(
                    text = it.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            //NOMBRE Y FOTO DEL CLIENTE/PROFESIONAL
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                AsyncImage(
                    model = if (role.equals("client")) appointment?.professional?.photo_url else appointment?.client?.photo_url,
                    contentDescription = if (role.equals("client")) appointment?.professional?.name else appointment?.client?.name,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.ic_launcher_background)
                )
                (if (role.equals("client")) appointment?.professional?.name else appointment?.client?.name)?.let {
                    Text(
                        text = it,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                (if (role.equals("client")) appointment?.professional?.last_name else appointment?.client?.last_name)?.let {
                    Text(
                        text = it,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            // Descripción
            appointment?.description?.let {
                Text(
                    text = it,//appointment.description,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Event,
                    contentDescription = "Calendar Icon",
                    tint = Color(0xFF636363),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )

                Text(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color(0xFF636363),
                    text = appointment?.date.toString(),
                    lineHeight = 30.sp
                )

                Icon(
                    imageVector = Icons.Outlined.AccessTime,
                    contentDescription = "Clock Icon",
                    tint = Color(0xFF636363),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )

                Text(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color(0xFF636363),
                    text = appointment?.hour.toString(),
                    lineHeight = 30.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (appointment?.appointmentStatus?.status != "Cancelada" && appointment?.appointmentStatus?.status != "Terminada")
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (appointment?.appointmentStatus?.status == "Pendiente") {
                        SecondaryButton(text = "Cancelar", onClick = { navController.popBackStack()}, width = 200)

                        PrimaryButton(
                            text = "Agendar",
                            onClick = {
                                navController.popBackStack()
                            },
                            width = 200)
                    } else if (appointment?.appointmentStatus?.status == "Aceptada") {
                        SecondaryButton(text = "Cancelar", onClick = { navController.popBackStack()}, width = 200)
                    }
                }
            }


        }

    }

}