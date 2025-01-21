package com.example.medidordeimc.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data object Graficos : Route

    @Serializable
    data object Fotos : Route

    @Serializable
    data object Logout : Route
}

sealed class BottomNavItem(
    var title: String,
    var icon: ImageVector,
    var route: Route)
{
    data object HomeButton :
        BottomNavItem("HOME", Icons.Default.Home, Route.Home)
    data object GrafButton :
        BottomNavItem("GRÁFICOS", Icons.Default.Analytics, Route.Graficos)
    data object PicButton  :
        BottomNavItem("FOTOS", Icons.Default.CameraAlt, Route.Fotos)
    data object LogoutButton  :
        BottomNavItem("LOGOUT", Icons.Default.ExitToApp, Route.Logout)
}


