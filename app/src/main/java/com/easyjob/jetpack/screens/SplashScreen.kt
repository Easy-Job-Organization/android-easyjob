package com.easyjob.jetpack.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.R
import com.easyjob.jetpack.viewmodels.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController = rememberNavController(),
    splashViewModel: SplashViewModel = hiltViewModel()
) {

    val authState by splashViewModel.authState.observeAsState()

    LaunchedEffect(Unit) {
        splashViewModel.checkLoggedIn()
    }

    LaunchedEffect(authState) {
        if(authState == 1) {
            splashViewModel.updateAuthState(0)
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        } else if (authState == 2 ) {
            navController.navigate("homeProfessional") {
                popUpTo("splash") { inclusive = true }
            }
        } else if(authState == 3) {
            navController.navigate("login") {
                splashViewModel.updateAuthState(0)
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    // State to control the visibility
    var isVisible by remember { mutableStateOf(true) }

    // Animate the alpha value with smoother transitions
    val alpha: Float by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 2000) // Duration of the fade animation
    )

    // Coroutine to toggle visibility
    LaunchedEffect(Unit) {
        while (true) {
            isVisible = !isVisible // Toggle visibility
            delay(1000) // Adjust duration for fade in/out effect (increased for slower effect)
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), // Applies the system bars padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.easyjob_logo_main_color),
                contentDescription = "Easyjob logo",
                modifier = Modifier
                    .size(175.dp)
                    .alpha(alpha) // Apply the animated alpha
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // Centers horizontally
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Easy Job",
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(59, 130, 246),
                    modifier = Modifier.alpha(alpha) // Apply the animated alpha
                )
                Text(
                    text = "Profesionales a tu gusto",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.alpha(alpha) // Apply the animated alpha
                )
            }
        }
    }
}