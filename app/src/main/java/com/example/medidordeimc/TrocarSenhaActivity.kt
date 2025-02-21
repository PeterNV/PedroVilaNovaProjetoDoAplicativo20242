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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.medidordeimc.ui.theme.*
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore

fun alterarSenha(email: String, senhaAtual: String, novaSenha: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
    val auth = FirebaseAuth.getInstance()

    // Autentica o usuário antes de alterar a senha
    auth.signInWithEmailAndPassword(email, senhaAtual)
        .addOnSuccessListener {
            val usuario = auth.currentUser
            usuario?.updatePassword(novaSenha)
                ?.addOnSuccessListener { onSuccess() }
                ?.addOnFailureListener { exception -> onFailure(exception) }
        }
        .addOnFailureListener { exception -> onFailure(exception) }
}

fun verificarEmailFirestore(email: String, onResult: (Boolean) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("users").whereEqualTo("email", email).get()
        .addOnSuccessListener { documents ->
            onResult(!documents.isEmpty)
        }
        .addOnFailureListener {
            onResult(false)
        }
}

class TrocarSenhaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    var senhaAtual by rememberSaveable { mutableStateOf("") }
    var novaSenha by rememberSaveable { mutableStateOf("") }
    var confirmarSenha by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
        modifier = modifier
            .padding(19.dp, 195.dp)
            .fillMaxSize()
            .shadow(5.dp, RoundedCornerShape(25.dp))
            .border(2.dp, White, RoundedCornerShape(25.dp))
            .background(White, RoundedCornerShape(25.dp))
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
            onValueChange = { email = it },
            shape = RoundedCornerShape(25.dp),
            placeholder = { Text("E-MAIL", fontStyle = FontStyle.Italic, color = GrayL, fontSize = 12.sp) },
            modifier = modifier.width(315.dp).height(50.dp).offset(0.dp, (-15).dp).border(2.dp, GrayD, shape = RoundedCornerShape(25.dp))
        )

        OutlinedTextField(
            value = senhaAtual,
            onValueChange = { senhaAtual = it },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(25.dp),
            placeholder = { Text("SENHA ATUAL", fontStyle = FontStyle.Italic, color = GrayL, fontSize = 12.sp) },
            modifier = modifier.width(315.dp).height(50.dp).offset(0.dp, (-10).dp).border(2.dp, GrayD, shape = RoundedCornerShape(25.dp))
        )

        OutlinedTextField(
            value = novaSenha,
            onValueChange = { novaSenha = it },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(25.dp),
            placeholder = { Text("NOVA SENHA", fontStyle = FontStyle.Italic, color = GrayL, fontSize = 12.sp) },
            modifier = modifier.width(315.dp).height(50.dp).offset(0.dp, (-5).dp).border(2.dp, GrayD, shape = RoundedCornerShape(25.dp))
        )

        OutlinedTextField(
            value = confirmarSenha,
            onValueChange = { confirmarSenha = it },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(25.dp),
            placeholder = { Text("CONFIRMAR SENHA", fontStyle = FontStyle.Italic, color = GrayL, fontSize = 12.sp) },
            modifier = modifier.width(315.dp).height(50.dp).offset(0.dp, (0).dp).border(2.dp, GrayD, shape = RoundedCornerShape(25.dp))
        )

        Button(
            enabled = email.isNotEmpty() && senhaAtual.isNotEmpty() && novaSenha.isNotEmpty() && novaSenha == confirmarSenha,
            modifier = modifier.width(315.dp).offset(0.dp, 2.dp),
            onClick = {
                verificarEmailFirestore(email) { existe ->
                    if (existe) {
                        alterarSenha(email, senhaAtual, novaSenha,
                            onSuccess = {
                                Toast.makeText(activity, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show()
                                Intent(activity, MainActivity::class.java).setFlags(
                                    FLAG_ACTIVITY_SINGLE_TOP
                                )
                            },
                            onFailure = { erro ->
                                Toast.makeText(activity, "Erro ao alterar senha: ${erro.message}", Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        Toast.makeText(activity, "Erro! E-mail não encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Aqua80)
        ) {
            Text("CONFIRMAR", fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = {
                email = ""
                senhaAtual = ""
                novaSenha = ""
                confirmarSenha = ""
                activity?.startActivity(Intent(activity, MainActivity::class.java).setFlags(FLAG_ACTIVITY_SINGLE_TOP))
            },
            modifier = modifier.width(315.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Red,contentColor = GrayD)
        ) {
            Text("CANCELAR", fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
        }
    }
}
