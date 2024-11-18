package com.easyjob.jetpack.models

data class AppointmentGet(
    val id: String,
    val date: String,
    val location: String,
    val hour: String,
    val service: String,
    val client: String,
    val professional: Professional? = null, // Opcional
    val paymentMethod: String? = null // Opcional

)
