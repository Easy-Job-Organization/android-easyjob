package com.easyjob.jetpack.models

data class ClientAppointment(
    val id: String,
    val name: String,
    val last_name: String,
    val email: String,
    val phone_number: String,
    val photo_url: String,
    val roles: List<String>,
    val appointments: List<Appointment>
)
