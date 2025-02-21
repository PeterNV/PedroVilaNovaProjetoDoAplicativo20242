package com.example.medidordeimc

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.Red
import com.example.medidordeimc.ui.theme.White
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun LogoutPage(modifier: Modifier = Modifier, viewModel: MainViewModel)  {
    Log.d("Navigation", "Navigated to LogoutPage")
    val activity = LocalContext.current as? Activity
    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,

        modifier = modifier.padding(16.dp,185.dp).fillMaxSize().shadow(
            elevation = 5.dp,
            shape = RoundedCornerShape(25.dp),
            clip = false,
            ambientColor = GrayD,
            spotColor = GrayD
        ).border(2.dp, White, shape = RoundedCornerShape(25.dp)).background(White,shape = RoundedCornerShape(25.dp)),

        ) {
        Text(
            text = "DESEJA FAZER LOGOUT?",
            fontSize = 22.sp,
            color = GrayD,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = modifier.offset(12.dp, (-15).dp).align(Alignment.CenterHorizontally)
        )

        Button(


            modifier = modifier.width(315.dp).offset(0.dp, 15.dp),
            onClick = {
                Firebase.auth.signOut();
               activity?.startActivity(
                    Intent(activity, MainActivity::class.java).setFlags(
                        FLAG_ACTIVITY_SINGLE_TOP
                    )
                )

            },
            colors = ButtonColors(
                containerColor = Red,
                contentColor = GrayD,
                disabledContainerColor = GrayD,
                disabledContentColor = White,
            ),

            ) {
            Text(
                "SIM",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        }




    }
}

