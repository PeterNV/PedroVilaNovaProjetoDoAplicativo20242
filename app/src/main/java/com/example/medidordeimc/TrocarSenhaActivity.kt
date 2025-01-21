package com.example.medidordeimc

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
//import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
//import androidx.compose.material3.Checkbox
//import androidx.compose.material3.CheckboxColors
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExposedDropdownMenuBox
//import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
//import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medidordeimc.ui.theme.Aqua80
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.GrayL
import com.example.medidordeimc.ui.theme.MedidorDeIMCTheme
import com.example.medidordeimc.ui.theme.White

class TrocarSenhaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()
        setContent {
            MedidorDeIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SenhaPage(modifier = Modifier.padding(innerPadding))
                    Spacer(modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable

fun SenhaPage(modifier: Modifier = Modifier) {

    var email by rememberSaveable { mutableStateOf("") }

    var password by rememberSaveable { mutableStateOf("") }
    var cpassword by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity

    Column(
        //modifier = modifier.padding(19.dp,145.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,

        modifier = modifier
            .padding(19.dp, 195.dp)
            .fillMaxSize()
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(25.dp),
                clip = false,
                ambientColor = GrayD,
                spotColor = GrayD
            ).border(2.dp, White, shape = RoundedCornerShape(25.dp)).background(White,shape = RoundedCornerShape(25.dp)),



        ) {
        Text(
            text = "MUDAR SENHA",
            fontSize = 40.sp,
            color = GrayD,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = modifier.offset(0.dp, (-40).dp)
        )
        OutlinedTextField(
            value = email,

            modifier = modifier
                .width(315.dp)
                .height(50.dp)
                .offset(0.dp, (-24).dp).border(2.dp, GrayD, shape = RoundedCornerShape(25.dp)),
            onValueChange = { email = it },
            shape = RoundedCornerShape(25.dp),
            placeholder = { Text(text = "E-MAIL",
                fontStyle = FontStyle.Italic,
                color = GrayL,
                fontSize = 12.sp,
            )}
        )


        OutlinedTextField(
            value = password,
            placeholder = { Text(text = "SENHA",
                fontStyle = FontStyle.Italic,
                color = GrayL,
                fontSize = 12.sp
            ) },
            modifier = modifier
                .width(315.dp)
                .height(50.dp)
                .offset(0.dp, (-12).dp).border(2.dp, GrayD, shape = RoundedCornerShape(25.dp)),
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(25.dp)
        )
        OutlinedTextField(
            value = cpassword,
            placeholder = { Text(text = "CONFIRMAR SENHA",
                fontStyle = FontStyle.Italic,
                color = GrayL,
                fontSize = 12.sp
            ) },
            modifier = modifier
                .width(315.dp)
                .height(50.dp).border(2.dp, GrayD, shape = RoundedCornerShape(25.dp)),
            onValueChange = { cpassword = it },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(25.dp),




            )



        Button(

            enabled = email.isNotEmpty() && password.isNotEmpty()
                     && cpassword.isNotEmpty()
                    ,
            modifier = modifier
                .width(315.dp)
                .offset(0.dp, 2.dp),
            onClick = {
                Toast.makeText(activity, "Login OK!", Toast.LENGTH_LONG).show()
            },

            colors= ButtonColors(
                containerColor = Aqua80,
                contentColor = White,
                disabledContainerColor = GrayL,
                disabledContentColor = White,
            ),

            ) {
            Text("CONFIRMAR",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = { email = ""; password = ""
                activity?.startActivity(
                    Intent(activity, MainActivity::class.java).setFlags(
                        FLAG_ACTIVITY_SINGLE_TOP
                    )
                ) },
            modifier = modifier.width(315.dp),
            colors= ButtonColors(
                containerColor = Aqua80,
                contentColor = White,
                disabledContainerColor = Aqua80,
                disabledContentColor = White,
            ),

            ) {
            Text("CANCELAR",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold)
        }
        /*
        Button(
            onClick = {

                activity?.startActivity(
                    Intent(activity, MainActivity::class.java).setFlags(
                        FLAG_ACTIVITY_SINGLE_TOP
                    )
                )
            }

        ) {Text("Main") }


        Button(
            onClick = {

                activity?.startActivity(
                    Intent(activity, RegisterActivity::class.java).setFlags(
                        FLAG_ACTIVITY_SINGLE_TOP
                    )
                )
            }

        ) {Text("Registro") }
         */


    }
}