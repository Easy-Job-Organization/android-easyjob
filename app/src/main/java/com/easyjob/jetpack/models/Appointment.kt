package com.easyjob.jetpack.models

data class Appointment(
    var id: String,
    val description: String,
    var date: String,
    val hour: String,
    val service: Service,
    var client: Client,
    var appointmentStatus: AppointmentStatus,
    val professional: Professional? = null, // Opcional
    val paymentMethod: String? = null // Opcional
)
