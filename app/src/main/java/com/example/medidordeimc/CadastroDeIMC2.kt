package com.example.medidordeimc

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
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
import com.example.medidordeimc.ui.theme.Aqua80
import com.example.medidordeimc.ui.theme.Black
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.GrayL
import com.example.medidordeimc.ui.theme.MedidorDeIMCTheme
import com.example.medidordeimc.ui.theme.White

class CadastroDeIMC2 : ComponentActivity() {
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
                    CIMC2(modifier = Modifier.padding(innerPadding))
                    Spacer(modifier = Modifier.size(24.dp))

                }
            }
        }
    }
}
//@Preview(showBackground = true)
@Composable
fun CIMC2(modifier: Modifier = Modifier) {



    var isSelectedI1 by rememberSaveable { mutableStateOf(false) }
    var isSelectedI2 by rememberSaveable { mutableStateOf(false) }
    var isSelectedI3 by rememberSaveable { mutableStateOf(false) }

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
            text = "ESCOLHA O INTERVALO PARA VERIFICAR NOVMANTE O IMC",
            fontSize = 23.sp,
            color = GrayL,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = modifier.offset(0.dp, (-40).dp)
        )
        Row(){
            RadioButton(
                selected = isSelectedI1, // Estado inicial
                onClick = { isSelectedI1 = !isSelectedI1;isSelectedI2 = false; isSelectedI3 = false },
                modifier = Modifier, // Personalize se necessário
                enabled = true, // Controle de habilitação
                interactionSource = remember { MutableInteractionSource() }, // Gerencia interações

                colors = RadioButtonColors(
                    selectedColor = Aqua80, // Fundo cinza claro ao ser selecionado
                    unselectedColor = GrayD,  // Borda preta quando não selecionado

                    disabledSelectedColor = GrayD,
                    disabledUnselectedColor = GrayD      // Cinza claro quando desativado
                )
            )
            Text("2 SEMANAS",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = GrayL,
                modifier = modifier.offset(0.dp,15.dp))
        }
        Row(){
            RadioButton(
                selected = isSelectedI2, // Estado inicial
                onClick = { isSelectedI2 = !isSelectedI2;isSelectedI1 = false; isSelectedI3 = false},
                modifier = Modifier, // Personalize se necessário
                enabled = true, // Controle de habilitação
                interactionSource = remember { MutableInteractionSource() }, // Gerencia interações

                colors = RadioButtonColors(
                    selectedColor = Aqua80, // Fundo cinza claro ao ser selecionado
                    unselectedColor = GrayD,  // Borda preta quando não selecionado

                    disabledSelectedColor = GrayD,
                    disabledUnselectedColor = GrayD      // Cinza claro quando desativado
                )
            )
            Text("4 SEMANAS",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = GrayL,
                modifier = modifier.offset(0.dp,15.dp))
        }
        Row(){
            RadioButton(
                selected = isSelectedI3, // Estado inicial
                onClick = { isSelectedI3 = !isSelectedI3;isSelectedI1 = false; isSelectedI2 = false },
                modifier = Modifier, // Personalize se necessário
                enabled = true, // Controle de habilitação
                interactionSource = remember { MutableInteractionSource() }, // Gerencia interações

                colors = RadioButtonColors(
                    selectedColor = Aqua80, // Fundo cinza claro ao ser selecionado
                    unselectedColor = GrayD,  // Borda preta quando não selecionado

                    disabledSelectedColor = GrayD,
                    disabledUnselectedColor = GrayD      // Cinza claro quando desativado
                )
            )
            Text("6 SEMANAS",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = GrayL,
                modifier = modifier.offset(0.dp,15.dp))
        }



        Button(

            enabled =  isSelectedI1 == true
                    || isSelectedI2 == true
                    || isSelectedI3 == true
            ,
            modifier = modifier
                .width(315.dp)
                .offset(0.dp, 2.dp),


            colors= ButtonColors(
                containerColor = Aqua80,
                contentColor = White,
                disabledContainerColor = GrayL,
                disabledContentColor = White,
            ),
            onClick = { isSelectedI1 = false; isSelectedI2 = false; isSelectedI3 = false
                activity?.startActivity(
                    Intent(activity, IMCFinal::class.java).setFlags(
                        FLAG_ACTIVITY_SINGLE_TOP
                    )
                ) }
            ) {
            Text("FINALIZAR",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = { isSelectedI1 = false; isSelectedI2 = false; isSelectedI3 = false
                activity?.startActivity(
                    Intent(activity, MainMenu::class.java).setFlags(
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



    }
}