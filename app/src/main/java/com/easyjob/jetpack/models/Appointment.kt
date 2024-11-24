package com.easyjob.jetpack.models

data class Appointment(
    var id: String,
    var date: String,
    val location: String,
    val hour: String,
    val service: Service,
    var client: Client,
    val professional: Professional? = null, // Opcional
    val paymentMethod: String? = null // Opcional
)
