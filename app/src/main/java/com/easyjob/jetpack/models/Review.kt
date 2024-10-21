package com.easyjob.jetpack.models

data class Review (
    val id: String,
    val score: Double,
    val comment: String,
    val client: Client,
)