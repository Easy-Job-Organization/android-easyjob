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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.Topbar
import com.easyjob.jetpack.viewmodels.LikesProfessionalViewModel
import com.easyjob.jetpack.viewmodels.ReviewsViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.easyjob.jetpack.R
import com.easyjob.jetpack.ui.theme.components.RatingStars
import com.easyjob.jetpack.viewmodels.ReviewsProfessionalViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsProfessionalScreen(
    navController: NavController = rememberNavController(),
    reviewsViewModel: ReviewsProfessionalViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val reviewsProfessional by reviewsViewModel.reviewsProfessional.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        reviewsViewModel.loadReviews()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Topbar(
                title = "Reseñas",
                isBack = true,
                scrollBehavior = scrollBehavior,
                navController = navController
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 5.dp)
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier.padding(
                    top = 20.dp,
                    bottom = 20.dp,
                )
            ) {
                Text(
                    text = "Tus Reseñas",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(reviewsProfessional) { review ->
                    Column(
                        modifier = Modifier
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                            .shadow(6.dp, RoundedCornerShape(8.dp))
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.padding(end = 16.dp)) {
                                AsyncImage(
                                    model = review.client?.photo_url,
                                    contentDescription = review.client?.name,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(40.dp),
                                    contentScale = ContentScale.Crop,
                                    error = painterResource(R.drawable.ic_launcher_background)
                                )
                            }

                            Text(
                                text = (review?.client?.name ?: "") + " ",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Text(
                                text = review?.client?.last_name ?: "",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RatingStars(rating = review.score.toDouble(), iconSize = 16)
                            Text(text = "( ${review.score.toString()} )", fontSize = 12.sp, fontWeight = FontWeight.Light)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = review.comment, fontSize = 14.sp, fontWeight = FontWeight.Light)
                    }
                }
            }
        }

    }
}