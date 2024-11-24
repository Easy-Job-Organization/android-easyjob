package com.easyjob.jetpack.models

data class Professional(
    val id: String,
    val name: String,
    val last_name: String,
    val email: String,
    val phone_number: String,
    val photo_url: String,
    val roles: List<String>,
    val cities: List<City>,
    val specialities: List<SpecialitiesResponse>,
    val score: Double,
    val description: String,
)

data class SpecialitiesResponse(
    val id: String,
    val speciality_name: String
)

data class CitiesResponse(
    val id: String,
    val name: String
)

data class City(
    val id: String,
    val city_name: String
)
