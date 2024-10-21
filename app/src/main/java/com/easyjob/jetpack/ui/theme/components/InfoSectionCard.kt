package com.easyjob.jetpack.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easyjob.jetpack.models.Review
import com.easyjob.jetpack.models.Service
import kotlin.math.roundToInt

@Composable
fun InformationCard(services: List<Service?>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(services) { service ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp, vertical = 4.dp)
            ) {

                if (service != null) {
                    Text(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        color = Color(0xFF133c55),
                        text = service.title,
                        lineHeight = 26.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(3.dp)
                    ) {

                        Text(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xff444444),
                            text = service.description,
                            lineHeight = 22.sp,
                            modifier = Modifier.padding(bottom = 0.dp)
                        )

                        Text(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xff444444),
                            text = "$${service.price}",
                            lineHeight = 22.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                    }
                }

            }
        }
    }
}


@Composable
fun CommentsCard(reviews: List<Review?>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(reviews) { review ->
            Row(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (review != null) {
                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                    ) {

                        Box(
                            modifier = Modifier
                                .background(Color(0xff1498D5), RoundedCornerShape(50.dp))
                                .size(30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = review.client.name.substring(0, 1),
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp,
                                color = Color(0xFF133c55),
                                text = review.client.name,
                                lineHeight = 26.sp,
                            )
                            RatingStars(rating = review.score.roundToInt(), iconSize = 16)
                        }

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color.Black,
                            text = review.comment,
                            lineHeight = 20.sp,
                        )
                    }
                }
            }
        }
    }
}
