package com.example.medidordeimc.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medidordeimc.FotosPage
import com.example.medidordeimc.GrafPage
import com.example.medidordeimc.HomePage
import com.example.medidordeimc.LogoutPage
import com.example.medidordeimc.MainViewModel


@Composable
fun MainNavHost(navController: NavHostController,viewModel: MainViewModel) {
    NavHost(navController, startDestination = Route.Home) {
        composable<Route.Home> { HomePage(
            viewModel = viewModel
        )  }

        composable<Route.Graficos> { GrafPage(
            viewModel = viewModel
        )  }
        composable<Route.Fotos>  { FotosPage(
            viewModel = viewModel
        )   }


        composable<Route.Logout>  { LogoutPage(
            viewModel = MainViewModel()
        )   }
    }
}