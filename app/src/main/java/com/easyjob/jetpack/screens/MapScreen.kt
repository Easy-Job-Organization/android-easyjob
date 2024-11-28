package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.easyjob.jetpack.viewmodels.SearchScreenViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    speciality: String = "",
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {

    val cameraPositionState = rememberCameraPositionState{
        position =  CameraPosition.fromLatLngZoom(
            LatLng(3.342, -76.530),
            15f
        )
    }
    val markerList by viewModel.markerList.observeAsState(emptyList())

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onMapLoaded = {
                val formattedText = speciality.trim().replaceFirstChar { it.uppercaseChar() }
                viewModel.loadSearch(formattedText)
            },
            cameraPositionState = cameraPositionState
        ) {
            markerList.forEach { marker ->
                Marker(
                    state = MarkerState(position = marker.position),
                    title = marker.name
                )
            }
        }
    }
}