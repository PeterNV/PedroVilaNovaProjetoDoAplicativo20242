package com.example.medidordeimc

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.graphics.drawable.Icon
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medidordeimc.db.fb.FBDatabase
import com.example.medidordeimc.model.IMC
import com.example.medidordeimc.model.UserC
import com.example.medidordeimc.ui.theme.Aqua80
import com.example.medidordeimc.ui.theme.Black
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.GrayL
import com.example.medidordeimc.ui.theme.MedidorDeIMCTheme
import com.example.medidordeimc.ui.theme.Red
import com.example.medidordeimc.ui.theme.White
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class CadastroDeIMC1 : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modifier = Modifier
        //enableEdgeToEdge()
        setContent {
            MedidorDeIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TopAppBar(
                        modifier = modifier.shadow(
                            elevation = 5.dp,
                            clip = false,
                            ambientColor = GrayD,
                            spotColor = GrayD
                        ),
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = White,
                            titleContentColor = Black,
                        ),
                        title = {
                            Text("Cadastro de IMC",
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                modifier = modifier.offset(105.dp, (2).dp))

                        })

                    CIMC1(modifier = Modifier.padding(innerPadding))
                    Spacer(modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CIMC1(modifier: Modifier = Modifier) {

    var foto by rememberSaveable { mutableStateOf("") }
    val db = FBDatabase()
    val viewModel = MainViewModel(db)
    //val altura1 = viewModel.users?.altura?:"[não disponível]"
    var altura by rememberSaveable { mutableStateOf("") }
    var peso by rememberSaveable { mutableStateOf("") }
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
            text = "MIMC",
            fontSize = 35.sp,
            color = Black,
            fontWeight = FontWeight.Bold,

            modifier = modifier.offset(0.dp, (-40).dp)
        )
        Button(
            modifier = modifier
                .width(315.dp)
                .offset(0.dp, (-22).dp).border(2.dp, GrayD,RoundedCornerShape(25.dp)),
            onClick = {
                Toast.makeText(activity, "Login OK!", Toast.LENGTH_LONG).show()
            },

            colors= ButtonColors(
                containerColor = Color.Transparent,
                contentColor = White,
                disabledContainerColor = GrayL,
                disabledContentColor = White,
            ),



        ){ Text(text = "ESCOLHA UMA FOTO ",
            fontStyle = FontStyle.Italic,
            color = GrayD,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )}



        OutlinedTextField(
            value = peso,
            placeholder = { Text(text = "SEU PESO",
                fontStyle = FontStyle.Italic,
                color = GrayL,
                fontSize = 12.sp
            ) },
            modifier = modifier
                .width(315.dp)
                .height(50.dp).offset(0.dp,(-10).dp).border(2.dp, GrayD, shape = RoundedCornerShape(25.dp)),
            onValueChange = { peso = it },
            shape = RoundedCornerShape(25.dp),

            )

        Button(

            enabled = peso.isNotEmpty()
            ,
            modifier = modifier
                .width(315.dp)
                .offset(0.dp, 2.dp),
            onClick = {

                //val altur2 = viewModel.users?.altura
                //val alturaFinal = altur1?.toFloat()?.times(altur2?.toFloat()!!)
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val currentDate = LocalDateTime.now().format(formatter)
               // if (alturaFinal != null) {
                    db.addImc(IMC(imc = ((peso.toFloat())/6), datet = currentDate, peso = peso.toFloat()))
                //}
                activity?.startActivity(
                    Intent(activity, CadastroDeIMC2::class.java).setFlags(
                        FLAG_ACTIVITY_SINGLE_TOP
                    )
                )
            },

            colors= ButtonColors(
                containerColor = Aqua80,
                contentColor = GrayD,
                disabledContainerColor = GrayL,
                disabledContentColor = GrayD,
            ),

            ) {
            Text("PROSSEGUIR",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = { foto = ""; altura = ""; peso = ""
                activity?.startActivity(
                    Intent(activity, MainMenu::class.java).setFlags(
                        FLAG_ACTIVITY_SINGLE_TOP
                    )
                ) },
            modifier = modifier.width(315.dp),
            colors= ButtonColors(
                containerColor = Red,
                contentColor = GrayD,
                disabledContainerColor = Red,
                disabledContentColor = GrayD,
            ),

            ) {
            Text("CANCELAR",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold)
        }



    }
}