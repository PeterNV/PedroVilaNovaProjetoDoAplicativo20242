package com.example.medidordeimc

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.util.Log
import android.widget.Toast
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
import com.example.medidordeimc.ui.theme.Aqua80
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.GrayL
import com.example.medidordeimc.ui.theme.White

@Composable
fun GrafPage (modifier: Modifier = Modifier, viewModel: MainViewModel) {

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


        Button(


            modifier = modifier.width(315.dp).offset(0.dp, 15.dp),
            onClick = {


            },
            colors = ButtonColors(
                containerColor = Aqua80,
                contentColor = White,
                disabledContainerColor = GrayL,
                disabledContentColor = White,
            ),

            ) {
            Text(
                "PRÓXIMO GRÁFICO",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            onClick = {


            },
            modifier = modifier.width(315.dp).offset(0.dp,15.dp),
            colors= ButtonColors(
                containerColor = Aqua80,
                contentColor = White,
                disabledContainerColor = Aqua80,
                disabledContentColor = White,
            ),

            ) {
            Text("GRÁFICO ANTERIOR",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold)
        }



    }
}