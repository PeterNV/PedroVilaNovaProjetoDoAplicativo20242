package com.example.medidordeimc

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medidordeimc.db.fb.FBDatabase
import com.example.medidordeimc.model.User
import com.example.medidordeimc.ui.theme.Aqua80
import com.example.medidordeimc.ui.theme.Black
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.GrayL
import com.example.medidordeimc.ui.theme.GreenL
import com.example.medidordeimc.ui.theme.MedidorDeIMCTheme
import com.example.medidordeimc.ui.theme.Red
import com.example.medidordeimc.ui.theme.White
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class CadastroActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MedidorDeIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val modifier = Modifier
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
                            Text("Cadastro de usuário",
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                modifier = modifier.offset(105.dp, (2).dp))

                        })
                    RegisterPage(modifier = Modifier.padding(innerPadding))
                    Spacer(modifier = Modifier.size(24.dp))

                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun RegisterPage(modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var altura by rememberSaveable { mutableStateOf("") }
    var cpassword by rememberSaveable { mutableStateOf("") }
    var sexoesco by rememberSaveable { mutableStateOf("") }
    sexoesco = ""

    val activity = LocalContext.current as? Activity
    var isSelectedF by rememberSaveable { mutableStateOf(false) }
    var isSelectedM by rememberSaveable { mutableStateOf(false) }
    Column(
        //modifier = modifier.padding(19.dp,145.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,

        modifier = modifier
            .padding(19.dp, 85.dp)
            .fillMaxSize()
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(25.dp),
                clip = false,
                ambientColor = GrayD,
                spotColor = GrayD
            ).border(2.dp, White, shape = RoundedCornerShape(25.dp)).background(White,shape = RoundedCornerShape(25.dp)),



    ) {
        OutlinedTextField(
            value = name,

            modifier = modifier
                .width(315.dp)
                .height(50.dp)
                .offset(0.dp, (-32).dp).border(2.dp, GrayD, shape = RoundedCornerShape(25.dp)),
            onValueChange = { name = it },
            shape = RoundedCornerShape(25.dp),
            placeholder = { Text(text = "NOME E SOBRENOME",
                fontStyle = FontStyle.Italic,
                color = GrayL,
                fontSize = 12.sp,
            )}
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
        val regexDate = "^(3[01]|[12][0-9]|0[1-9]|[1-9])/(1[0-2]|0[1-9]|[1-9])/[0-9]{4}$".toRegex()
        OutlinedTextField(
            value = date,

            modifier = modifier
                .width(315.dp)
                .height(50.dp)
                .offset(0.dp, (-16).dp).border(2.dp, color = if(date.isEmpty()) GrayD else if( !date.contains(regexDate)) Red else GreenL, shape = RoundedCornerShape(25.dp)),
            onValueChange = { date = it },
            shape = RoundedCornerShape(25.dp),
            placeholder = { Text(text = "DATA DE NASCIMENTO (DD/MM/AAAA)",
                fontStyle = FontStyle.Italic,
                color = GrayL,
                fontSize = 12.sp,
            )}
        )
        Text("SEXO",
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color = GrayL,
            fontSize = 18.sp)
        Row(modifier = Modifier){


            RadioButton(
                selected = isSelectedM, // Estado inicial
                onClick = { isSelectedM = !isSelectedM;isSelectedF = false },
                modifier = Modifier
                    .padding(3.dp)
                    .offset((-20).dp,(-10).dp), // Personalize se necessário
                enabled = true, // Controle de habilitação
                interactionSource = remember { MutableInteractionSource() }, // Gerencia interações
                colors = RadioButtonColors(
                    selectedColor = Aqua80, // Fundo cinza claro ao ser selecionado
                    unselectedColor = GrayD,  // Borda preta quando não selecionado

                    disabledSelectedColor = GrayD,
                    disabledUnselectedColor = GrayD      // Cinza claro quando desativado
                )
            )
            Text("MASCULINO",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = GrayL,
                modifier = modifier.offset((-20).dp,8.dp))

            RadioButton(
                selected = isSelectedF, // Estado inicial
                onClick = { isSelectedF = !isSelectedF; isSelectedM = false },
                modifier = Modifier
                    .padding(3.dp)
                    .offset((-20).dp,(-10).dp), // Personalize se necessário
                enabled = true, // Controle de habilitação
                interactionSource = remember { MutableInteractionSource() }, // Gerencia interações
                colors = RadioButtonColors(
                    selectedColor = Aqua80, // Fundo cinza claro ao ser selecionado
                    unselectedColor = GrayD,  // Borda preta quando não selecionado

                    disabledSelectedColor = GrayD,
                    disabledUnselectedColor = GrayD      // Cinza claro quando desativado
                )
            )
            Text("FEMININO",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = GrayL,
                modifier = modifier.offset((-20).dp,8.dp))
        }
        OutlinedTextField(
            value = altura,
            placeholder = { Text(text = "SUA ALTURA",
                fontStyle = FontStyle.Italic,
                color = GrayL,
                fontSize = 12.sp
            ) },
            modifier = modifier
                .width(315.dp)
                .height(50.dp)
                .offset(0.dp, (-16).dp).border(2.dp, GrayD, shape = RoundedCornerShape(25.dp)),
            onValueChange = { altura = it },
            shape = RoundedCornerShape(25.dp)
        )
        val regexPassword = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")

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
                .offset(0.dp, (-8).dp).border(2.dp,  color = if(password.isEmpty()) GrayD else if(!password.contains(regexPassword)) Red else GreenL, shape = RoundedCornerShape(25.dp)),
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
                .height(50.dp).border(2.dp,  color = if(cpassword.isEmpty()) GrayD else if(cpassword != password) Red else GreenL, shape = RoundedCornerShape(25.dp)),
            onValueChange = { cpassword = it },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(25.dp),
        )
        Text("A SENHA DEVE CONTER NO MÍNIMO 8 CARACTERES,\nINCLUINDO LETRAS MAIÚSCULAS E MINÚSCULAS, E \nNÚMEROS.",
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = GrayD,
            modifier = modifier.offset((0).dp,8.dp))


        Button(
            enabled = (email.isNotEmpty() && password.isNotEmpty()
                    && name.isNotEmpty() && date.isNotEmpty() && cpassword.isNotEmpty()
                    && cpassword == password) && (isSelectedF || isSelectedM) && password.contains(regexPassword) && (date.length == 9 || date.length == 10) && date.contains(regexDate),
            modifier = modifier
                .width(315.dp)
                .offset(0.dp, 20.dp),
            onClick = {

                Firebase.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity!!) { task ->

                        if(isSelectedF){
                            sexoesco = "Feminino"
                        }
                        if(isSelectedM){
                            sexoesco = "Masculino"
                        }


                        if (task.isSuccessful) {
                            Toast.makeText(activity,"Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show()
                            activity.startActivity(
                                Intent(activity, MainActivity::class.java).setFlags(
                                    FLAG_ACTIVITY_SINGLE_TOP )
                            )
                            FBDatabase().register(User(name, email,date,altura.toFloat(),sexoesco))

                        } else {
                            Toast.makeText(activity,
                                "Registro FALHOU!", Toast.LENGTH_LONG).show()
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
            modifier = modifier.width(315.dp).offset(0.dp, 20.dp),
            colors= ButtonColors(
                containerColor = Red,
                contentColor = GrayD,
                disabledContainerColor = Aqua80,
                disabledContentColor = GrayD,
            ),

            ) {
            Text("CANCELAR",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold)
        }
    }
}