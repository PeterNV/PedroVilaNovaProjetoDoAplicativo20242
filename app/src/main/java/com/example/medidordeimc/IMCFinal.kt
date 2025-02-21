package com.example.medidordeimc

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medidordeimc.db.fb.FBDatabase
import com.example.medidordeimc.ui.theme.Aqua80
import com.example.medidordeimc.ui.theme.Black
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.GrayL
import com.example.medidordeimc.ui.theme.MedidorDeIMCTheme
import com.example.medidordeimc.ui.theme.White

class IMCFinal : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modifier = Modifier
        val peso = intent.getStringExtra("peso") ?: "0.0"
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
                    IMCF(modifier = Modifier.padding(innerPadding), peso = peso)
                    Spacer(modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}
//@Preview(showBackground = true)
@Composable
fun IMCF(modifier: Modifier = Modifier, peso: String) {


    val fbDB = remember { FBDatabase() }
    val viewModel : MainViewModel = viewModel(
        factory = MainViewModelFactory(fbDB)
    )
    var valorImc by rememberSaveable { mutableStateOf("") }
   // var peso by rememberSaveable { mutableStateOf("") }
    var altura by rememberSaveable { mutableStateOf("") }
    altura = (viewModel.user?.altura?:1).toString()
    //val peso = intent.getStringExtra("peso") ?: "0.0"

    //peso = (Intent.getIntent("peso") ?: 1.0).toString()

    valorImc =  ((peso.toFloat())/(altura.toFloat()*altura.toFloat())).toString()
    val valImc = valorImc.toFloat()
    var isSelectedI1 by rememberSaveable { mutableStateOf(false) }
    var isSelectedI2 by rememberSaveable { mutableStateOf(false) }
    var isSelectedI3 by rememberSaveable { mutableStateOf(false) }
    var resultado by rememberSaveable { mutableStateOf("") }
    var recomendacao by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity
    if (valImc <= 18.5){
        resultado = "(${valorImc}) Abaixo do peso"
        recomendacao = "Procurar orientação médica e nutricional para investigar possíveis \n" +
                "causas (como distúrbios alimentares, condições médicas subjacentes) e desenvolver um plano de \n" +
                "alimentação para alcançar um peso saudável. "
    }
    if (18.5 < valImc && valImc <= 24.9){
        resultado = "(${valorImc}) Peso normal"
        recomendacao = "Manter um estilo de vida equilibrado, com uma dieta saudável e \n" +
                "atividade física regular para sustentar o\n "+
                "peso e a saúde geral."
    }
    if (25.1 < valImc && valImc <= 29.9){
        resultado = "(${valorImc}) Sobrepeso"
        recomendacao = "Adotar um plano de alimentação balanceada e aumentar a atividade \n" +
                "física para ajudar na perda de peso e melhorar a saúde geral."
    }
    if (30.1 < valImc && valImc <= 34.9){
        resultado = "(${valorImc}) Obesidade Grau 1"
        recomendacao = "Consultar um profissional de saúde para uma avaliação \n" +
                "completa e considerar mudanças mais estruturadas na dieta e nos exercícios físicos. "
    }
    if (35.1 < valImc && valImc <= 39.9){
        resultado = "(${valorImc}) Obesidade Grau 2"
        recomendacao = "Intervenções mais intensivas, como programas \n" +
                "supervisionados de perda de peso e consultas com nutricionistas, médicos e possivelmente psicólogos, para \n" +
                "tratar questões relacionadas à alimentação e ao estilo de vida. "
    }
    if ( valImc >= 40.1){
        resultado = "(${valorImc}) Obesidade Grau 3"
        recomendacao = " Necessidade de acompanhamento médico rigoroso. Podem ser \n" +
                "necessárias intervenções como terapia comportamental intensiva, medicamentos para perda de peso e, em \n" +
                "alguns casos, cirurgia bariátrica. \n"
    }
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
            )
            .border(2.dp, White, shape = RoundedCornerShape(25.dp))
            .background(White, shape = RoundedCornerShape(25.dp)),



        ) {
        Text(
            text = "CADASTRO DO IMC REALIZADO COM SUCESSO",
            fontSize = 23.sp,
            color = GrayL,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = modifier.offset(0.dp, (-40).dp)
        )

            Text(
                text = "RESULTADO: $resultado",
                fontSize = 15.sp,
                color = GrayL,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = modifier.offset((-30).dp, (-20).dp)
                )


            Text(
                text = "RECOMENDAÇÃO: $recomendacao",
                fontSize = 15.sp,
                color = GrayL,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = modifier.offset(18.dp)
            )


        Button(
            onClick = { isSelectedI1 = false; isSelectedI2 = false; isSelectedI3 = false
                activity?.startActivity(
                    Intent(activity, MainMenu::class.java).setFlags(
                        FLAG_ACTIVITY_SINGLE_TOP
                    )
                ) },
            modifier = modifier
                .width(315.dp)
                .offset(0.dp, 25.dp),
            colors= ButtonColors(
                containerColor = Aqua80,
                contentColor = White,
                disabledContainerColor = Aqua80,
                disabledContentColor = White,
            ),

            ) {
            Text("VOLTAR",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold)
        }



    }
}