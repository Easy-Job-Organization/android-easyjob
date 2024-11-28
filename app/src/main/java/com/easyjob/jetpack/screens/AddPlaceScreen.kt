package com.easyjob.jetpack.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.sharp.Map
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.easyjob.jetpack.R
import com.easyjob.jetpack.ui.theme.components.Input
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.RatingStars
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.PlacesViewModel
import com.easyjob.jetpack.viewmodels.ReviewsViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaceScreen(
    navController: NavController = rememberNavController(),
    placesViewModel: PlacesViewModel = hiltViewModel(),
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var name by remember { mutableStateOf("") }

    var markerClickState = rememberMarkerState(
        position = LatLng(0.0, 0.0)
    )

    LaunchedEffect(Unit) {
        placesViewModel.retrivePlacesOfProfessional()
    }

    Scaffold(
        topBar = {
            Topbar(
                title = "Añadir Ubicaciones",
                isBack = true,
                scrollBehavior = scrollBehavior,
                navController = navController
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(padding)
                .padding(
                    vertical = 28.dp,
                    horizontal = 24.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(text = "Añadir Ubicación", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Agrega un nombre de la nueva ubicación", fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.Normal)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(4.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    value = name,
                    placeholder = { Text(modifier = Modifier.background(Color.Transparent), text = "Nombre Ubicación") },
                    shape = RoundedCornerShape(4.dp),
                    onValueChange = { name = it },
                )
                Spacer(modifier = Modifier.height(20.dp))
                GoogleMap(
                    cameraPositionState = rememberCameraPositionState {
                        position =
                            CameraPosition.fromLatLngZoom( LatLng(3.450889481819219, -76.5306617769318) , 10f)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(16.dp)),

                    onMapClick = { latLng ->
                        markerClickState.position = latLng
                    },
                ) {
                    Marker(
                        state = markerClickState
                    )
                }
            }

            PrimaryButton(text = "Guardar", onClick = {
                placesViewModel.savePlaceOfProfessional(name, markerClickState.position.latitude, markerClickState.position.longitude)
                navController.navigate("profile") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            })

        }
    }
}