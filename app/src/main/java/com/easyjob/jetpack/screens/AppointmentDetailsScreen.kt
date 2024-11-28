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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AttachMoney
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentDetailsScreen(
    navController: NavController = rememberNavController(),
    appointmentDetailsViewModel: AppointmentDetailsViewModel = hiltViewModel(),
    id: String
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val appointment by appointmentDetailsViewModel.appointment.observeAsState()
    val status by appointmentDetailsViewModel.statusFinal.observeAsState()
    val error by appointmentDetailsViewModel.error.observeAsState()
    val statusUpdateSuccess by appointmentDetailsViewModel.statusUpdateSuccess.observeAsState()
    val role by appointmentDetailsViewModel.role.observeAsState("")

    LaunchedEffect(Unit) {
        appointmentDetailsViewModel.loadAppointmentDetails(id)
        appointmentDetailsViewModel.getRole()
        Log.e("AppointmentDetailsScreen", "ROLE: $role")
        Log.e("AppointmentDetailsScreen", "APPOINTMENT: ${appointment}")
    }

    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    // Variable para la fecha formateada
    val formattedDate = appointment?.date?.let { date ->
        try {
            val parsedDate = LocalDate.parse(date, inputFormatter)
            parsedDate.format(outputFormatter)
        } catch (e: Exception) {
            Log.e("AppointmentDetailsScreen", "Error parsing date: $date", e)
            "Fecha no disponible"
        }
    } ?: "Cargando..."



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
                .padding(16.dp)
                .padding(top = 20.dp)
        ) {
            //TITULO
            appointment?.service?.let {
                Text(
                    text = it.title,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth(),
                    color = Color(0xff3B82F6)
                    )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy((-4).dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
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
                            fontSize = 20.sp,
                            color = Color(0xFF636363),
                            text = formattedDate,
                            lineHeight = 30.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {

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
                            fontSize = 20.sp,
                            color = Color(0xFF636363),
                            text = appointment?.hour.toString(),
                            lineHeight = 30.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))


                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            color = if(status=="aceptada"){Color(0xFFD4EDDA)}else if (status=="pendiente") {Color(0xFFFFF3CD)} else {Color(0xFFF8D7DA)}, // Verde claro
                            shape = RoundedCornerShape(10.dp) // Bordes redondeados
                        )
                        .padding(horizontal = 20.dp, vertical = 10.dp) // Espaciado interno
                ) {
                    Text(
                        text = if(status=="aceptada"){"Aceptada"}else if (status=="pendiente") {"Pendiente"} else if(status=="rechazada") {"Cancelada"} else {"Terminada"} ,
                        color = if(status=="aceptada"){Color(0xFF155724)}else if (status=="pendiente") {Color(0xFF856404)} else {Color(0xFF721C24)}, // Texto verde oscuro
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            role?.let {
                Text(
                    text = if (role == "client"){"Te va a ayudar:"}else{"Vas a ayudar a:"},
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(bottom = 8.dp, top = 16.dp)
                        .fillMaxWidth(),
                    color = Color.Black
                )
            }



            //NOMBRE Y FOTO DEL CLIENTE/PROFESIONAL
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                AsyncImage(
                    model = if (role.equals("client")) appointment?.professional?.photo_url else appointment?.client?.photo_url,
                    contentDescription = if (role.equals("client")) appointment?.professional?.name else appointment?.client?.name,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.ic_launcher_background)
                )
                Spacer(modifier = Modifier.width(16.dp))
                (if (role.equals("client")) appointment?.professional?.name else appointment?.client?.name)?.let {
                    Text(
                        text = it,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                (if (role.equals("client")) appointment?.professional?.last_name else appointment?.client?.last_name)?.let {
                    Text(
                        text = it,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp
                    )
                }
            }

            Text(
                text = "Detalles del servicio:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(bottom = 8.dp, top = 16.dp)
                    .fillMaxWidth(),
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {

                Icon(
                    imageVector = Icons.Outlined.AttachMoney,
                    contentDescription = "Clock Icon",
                    tint = Color(0xFF636363),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 4.dp)
                )

                Text(
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = Color(0xFF636363),
                    text = appointment?.service?.price.toString()?:"---",
                    lineHeight = 30.sp
                )
            }

            appointment?.service?.description?.let {
                Text(
                    text = it,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                )
            }

            if(appointment?.description!=null){
                Text(
                    text = if (role == "client"){"Tus indicaciones:"}else{"Indicaciones del cliente:"},
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(bottom = 8.dp, top = 16.dp)
                        .fillMaxWidth(),
                    color = Color.Black
                )
            }


            // Descripción
            appointment?.description?.let { it1 ->
                Text(
                    text = it1,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                )
            }







            Spacer(modifier = Modifier.weight(1f))


            if (status != "rechazada" && status != "terminada")
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (status == "pendiente") {
                        SecondaryButton(
                            text = "Cancelar",
                            onClick = {
                                appointmentDetailsViewModel.updateAppointmentStatus(appointment?.id?:"","rechazada")
                                navController.popBackStack()
                            },
                            width = 200)
                        if(role!="client"){
                            PrimaryButton(
                                text = "Aceptar",
                                onClick = {
                                    appointmentDetailsViewModel.updateAppointmentStatus(appointment?.id?:"","aceptada")
                                    navController.popBackStack()
                                },
                                width = 200)
                        }
                    } else if (status == "aceptada") {
                        SecondaryButton(
                            text = "Cancelar",
                            onClick = {
                                appointmentDetailsViewModel.updateAppointmentStatus(appointment?.id?:"","rechazada")
                                navController.popBackStack()
                                  },
                            width = 200)
                    }
                }
            }


        }

    }

}