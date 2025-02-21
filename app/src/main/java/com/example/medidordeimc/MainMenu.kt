package com.example.medidordeimc


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.medidordeimc.nav.BottomNavItem
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medidordeimc.db.fb.FBDatabase
import com.example.medidordeimc.nav.BottomNavBar
import com.example.medidordeimc.nav.MainNavHost
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.MedidorDeIMCTheme
import com.example.medidordeimc.ui.theme.White


class MainMenu : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modifier = Modifier
        setContent {
            //val viewModel: MainViewModel by viewModels()
            val fbDB = remember { FBDatabase() }
            val viewModel : MainViewModel = viewModel(
                factory = MainViewModelFactory(fbDB)
            )
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
