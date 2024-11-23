package com.easyjob.jetpack.models

data class Service (
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
)

data class CreateServiceDTO(
    val title: String,
    val description: String,
    val price: Double,
)