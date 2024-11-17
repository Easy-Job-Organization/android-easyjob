package com.easyjob.jetpack.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topbar(
    title: String,
    icon: ImageVector? = null,
    onEditClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController = rememberNavController(),
    isBack: Boolean,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xff3b82f6),
            titleContentColor = Color.White,
        ),
        modifier = Modifier
            .shadow(elevation = 4.dp)
            .zIndex(1f),
        title = {
            Text(
                title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        },
        navigationIcon = {
            if (isBack) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            if (icon != null) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopbarChat(
    title: String,
    onCallClick: () -> Unit = {},
    onSettingClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController = rememberNavController(),
) {

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xff3b82f6),
            titleContentColor = Color.White,
        ),
        modifier = Modifier
            .shadow(elevation = 4.dp)
            .zIndex(1f),
        title = {
            Text(
                title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = onCallClick) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Llamar",
                    tint = Color.White
                )
            }
            IconButton(onClick = onSettingClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Más",
                    tint = Color.White
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )

}