package com.example.medidordeimc

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier

import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.medidordeimc.nav.BottomNavItem

import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp

import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import com.example.medidordeimc.nav.BottomNavBar
import com.example.medidordeimc.nav.MainNavHost
import com.example.medidordeimc.nav.Route
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.MedidorDeIMCTheme
import com.example.medidordeimc.ui.theme.White

class MainMenu : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modifier = Modifier
        setContent {
            val viewModel: MainViewModel by viewModels()
            val navController = rememberNavController()

            MedidorDeIMCTheme {
                Scaffold(

                    topBar = {


                        TopAppBar(
                            modifier = modifier.shadow(
                                elevation = 5.dp,
                                clip = false,
                                ambientColor = GrayD,
                                spotColor = GrayD
                            ),
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = White,
                                titleContentColor = White,
                            ),
                            title = {


                                Image(
                                    painter = painterResource(id = R.drawable.logotipologin_pressed),
                                    contentDescription = "Imagem",
                                    modifier = Modifier.size(165.dp).offset(105.dp,0.dp)
                                )

                            })
                    },


                    bottomBar = {
                        val items = listOf(
                            BottomNavItem.HomeButton,
                            BottomNavItem.GrafButton,
                            BottomNavItem.PicButton,
                            BottomNavItem.LogoutButton
                        )
                        BottomNavBar(navController = navController, items)

                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainNavHost(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }
}
