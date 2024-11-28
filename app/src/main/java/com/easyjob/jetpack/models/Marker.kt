package com.easyjob.jetpack.models

import com.google.android.gms.maps.model.LatLng


data class Marker(
    val name: String,
    val position: LatLng
)