package com.easyjob.jetpack.models

data class Appointment(
    var date: String,
    val location: String,
    val hour: String,
    val service: String,
    var client: String,
    val professional: String? = null, // Opcional
    val paymentMethod: String? = null // Opcional
)
