package com.example.medidordeimc

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.graphics.drawable.Icon
import android.media.Image
//import android.icu.text.ListFormatter.Width
//import android.icu.text.Transliterator.Position
//import android.location.Address
import android.os.Bundle
//import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.content.MediaType.Companion.Image
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement


import androidx.compose.foundation.layout.Column


//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row


//import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
//import androidx.compose.material3.CheckboxDefaults
//import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
//import androidx.compose.ui.graphics.Shadow
//import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.medidordeimc.ui.theme.MedidorDeIMCTheme
//import com.example.medidordeimc.ui.theme.PurpleGrey40
import com.example.medidordeimc.ui.theme.Aqua80
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.GrayL
import com.example.medidordeimc.ui.theme.GreenL
import com.example.medidordeimc.ui.theme.White
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        val modifier = Modifier



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
                                titleContentColor = White,
                            ),
                            title = {


                                Image(
                                    painter = painterResource(id = R.drawable.logotipologin_pressed),
                                    contentDescription = "Imagem",
                                    modifier = Modifier.size(165.dp).offset(105.dp,0.dp)
                                )

                            })

                    LoginPage(

                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun LoginPage(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isChecked by rememberSaveable { mutableStateOf(false) }
    val activity = LocalContext.current as? Activity
    Column(
        //modifier = modifier.padding(19.dp,145.dp).fillMaxSize(),
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
            text = "BEM-VINDO(A)",
            fontSize = 40.sp,
            color = GrayD,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = modifier.offset(0.dp, (-15).dp)
            )

        OutlinedTextField(
            value = email,

            modifier = modifier.width(315.dp).height(50.dp).offset(0.dp, (-8).dp).border(2.dp, GrayD, shape = RoundedCornerShape(25.dp)),
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
                modifier = modifier.width(315.dp).height(50.dp).border(2.dp, GrayD, shape = RoundedCornerShape(25.dp)),
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(25.dp)
            )

        Row(modifier = Modifier){
            Checkbox(

                checked = isChecked, // Uma variável booleana que controla o estado do checkbox
                onCheckedChange = { newValue ->
                    isChecked = newValue // Atualize o estado conforme necessário
                },
                modifier = Modifier.offset((-22).dp,12.dp), // Exemplo de modificador
                enabled = true,
                colors = CheckboxColors(
                    checkedCheckmarkColor = White,
                    uncheckedCheckmarkColor = Color(0xFF80D8FF), // Aqua80 (cor azul clara)
                    checkedBoxColor = GreenL, // Fundo verde quando marcado
                    uncheckedBoxColor = White, // Fundo branco quando desmarcado
                    disabledCheckedBoxColor =  GrayL ,
                    disabledUncheckedBoxColor = GrayL ,
                    disabledIndeterminateBoxColor = GrayL,
                    checkedBorderColor = GreenL,
                    uncheckedBorderColor = GrayD, // Borda cinza escura
                    disabledBorderColor = GrayL,
                    disabledUncheckedBorderColor = GrayL,
                    disabledIndeterminateBorderColor = GrayL
                )

            )

            Text(text = "Lembrar-me?",
                fontSize = 12.sp,
                color = GrayD,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                modifier = modifier.offset((-26).dp,26.dp)
                )
            Button(
                modifier = modifier.background(Color.Transparent,shape = RoundedCornerShape(25.dp)).offset(25.dp,14.dp),
                onClick = {

                    try {
                        val intent = Intent(activity, TrocarSenhaActivity::class.java)
                        intent.flags = FLAG_ACTIVITY_SINGLE_TOP
                        activity?.startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace() // Captura exceções para depuração
                        Toast.makeText(activity, "Erro ao iniciar atividade", Toast.LENGTH_SHORT).show()
                    }
                } ,
                colors= ButtonColors(
                        containerColor = Color.Transparent,
                contentColor = GrayD,
                disabledContainerColor = GrayL,
                disabledContentColor = GrayD,
            )
            ){ Text(text = "Esqueceu a senha?",
                fontSize = 12.sp,

                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                ) }
        }


        Button(

                enabled = email.isNotEmpty() && password.isNotEmpty(),
                modifier = modifier.width(315.dp).offset(0.dp,15.dp),
                onClick = {

                    Firebase.auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                activity.startActivity(
                                    Intent(activity, MainMenu::class.java).setFlags(
                                        FLAG_ACTIVITY_SINGLE_TOP
                                    )
                                )
                                Toast.makeText(activity, "Login OK!", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(activity, "Login FALHOU!", Toast.LENGTH_LONG).show()
                            }
                        }
                },
                colors= ButtonColors(
                    containerColor = Aqua80,
                    contentColor = GrayD,
                    disabledContainerColor = GrayL,
                    disabledContentColor = GrayD,
                ),

                ) {
                Text("LOGIN",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = {

                    try {

                        activity?.startActivity(
                            Intent(activity, CadastroActivity::class.java).setFlags(
                                FLAG_ACTIVITY_SINGLE_TOP or FLAG_ACTIVITY_NO_HISTORY
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace() // Captura exceções para depuração
                        Toast.makeText(activity, "Erro ao iniciar atividade", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = modifier.width(315.dp).offset(0.dp,15.dp),
                colors= ButtonColors(
                    containerColor = Aqua80,
                    contentColor = GrayD,
                    disabledContainerColor = Aqua80,
                    disabledContentColor = GrayD,
                ),

            ) {
                Text("CADASTRAR",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold)
            }
    }
}