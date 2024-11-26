package com.easyjob.jetpack.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ContactSupport
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.easyjob.jetpack.R
import com.easyjob.jetpack.ui.theme.components.ActionCard
import com.easyjob.jetpack.ui.theme.components.ButtonIconLink
import com.easyjob.jetpack.ui.theme.components.EasyJobLogo
import com.easyjob.jetpack.ui.theme.components.Input
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.ProfileSectionClient
import com.easyjob.jetpack.ui.theme.components.TextButton
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.LikesProfessionalViewModel
import com.easyjob.jetpack.viewmodels.LoginViewModel
import com.easyjob.jetpack.viewmodels.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikesProfessionalScreen(
    navController: NavController = rememberNavController(),
    likesProfessionalViewModel: LikesProfessionalViewModel = hiltViewModel()
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val profileState by likesProfessionalViewModel.profileState.observeAsState(0)

    LaunchedEffect(Unit) {
        likesProfessionalViewModel.loadClientLikes()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Topbar(
                title = "Mis Favoritos",
                onEditClick = {},
                scrollBehavior = scrollBehavior,
                isBack = true,
                navController = navController
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 15.dp, vertical = 5.dp)
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier.padding(
                    top = 20.dp,
                    bottom = 20.dp
                )
            ) {
                Text(
                    text = "Tus profesionales favoritos",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                )
            }
            when (profileState) {
                1 -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                2 -> {
                    Text("Hubo un error al cargar el perfil", color = Color.Red)
                }
                3 -> {
                    val likedProfessionals by likesProfessionalViewModel.likedProfessionals.observeAsState()

                    likedProfessionals?.let {
                        if (it.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(it) { professional ->
                                    Row(
                                        modifier = Modifier

                                            .shadow(6.dp, RoundedCornerShape(8.dp))
                                            .background(Color.White, RoundedCornerShape(8.dp))
                                            .clickable {
                                                navController.navigate("professionalProfileClient/${professional?.id}")
                                            }
                                            .padding(20.dp)
                                            .fillMaxWidth()

                                        ,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,

                                    ){
                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(modifier = Modifier.padding(end = 16.dp)) {
                                                AsyncImage(
                                                    model = professional?.photo_url,
                                                    contentDescription = professional?.name,
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .size(32.dp),
                                                    contentScale = ContentScale.Crop,
                                                    error = painterResource(R.drawable.ic_launcher_background)
                                                )
                                            }

                                            Text(
                                                text = (professional?.name ?: "") + " ",
                                                color = Color.Black,
                                                fontSize = 18.sp
                                            )

                                            Text(
                                                text = professional?.last_name ?: "",
                                                color = Color.Black,
                                                fontSize = 18.sp
                                            )
                                        }

                                        Icon(
                                            imageVector = Icons.Rounded.Favorite,
                                            contentDescription = "Eliminar de favoritos",
                                            tint = Color.Black,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        } else {
                            Text("No tienes profesionales favoritos", color = Color.Black)
                        }
                    }
                }

            }

        }
    }
}
