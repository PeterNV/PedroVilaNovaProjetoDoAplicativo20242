package com.example.medidordeimc

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.medidordeimc.ui.theme.White
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomePage(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val name = viewModel.user?.name?:"[não logado]"
    val imcList = viewModel.imcs
    var i by rememberSaveable { mutableIntStateOf(0) }
    var RegistroStatus by rememberSaveable { mutableStateOf("") }
    var permitirRegistro by rememberSaveable { mutableStateOf(false) }
    val verificarData by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity

    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,

        modifier = modifier.padding(16.dp,155.dp).fillMaxSize().shadow(
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
            modifier = modifier.offset(0.dp, (-35).dp)
        )
        Text(

            text = name,
            fontSize = 25.sp,
            color = GrayD,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = modifier.offset(0.dp, (-5).dp)
        )


        LaunchedEffect(Unit) {
            i = 0 // Reseta o índice ao entrar na tela
            while (true) {
                delay(3000) // Aguarda 3 segundos

                if (imcList.isNotEmpty()) {
                    i = (i + 1) % imcList.size // Avança e volta para o primeiro quando chega ao fim
                } else {
                    i = 0 // Se a lista estiver vazia, garante que i seja sempre zero
                }
            }
        }

        Text(
            text = if (imcList.isNotEmpty()) "(IMC: %.2f)".format(imcList[i].imc) else "Lista vazia!",
            fontSize = 18.sp,
            color = GrayD,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = modifier.offset(0.dp, 5.dp)
        )

        Text(
            text = if (imcList.isNotEmpty()) "(DATA: ${imcList[i].datet})" else "Lista vazia!",
            fontSize = 18.sp,
            color = GrayD,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = modifier.offset(0.dp, 15.dp)
        )
        RegistroStatus = "REGISTRAR IMC"
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val currentDate = LocalDate.now().format(formatter)

// Obtém a última data registrada no banco de dados
        val lastDate = imcList.map { it.datet }
            .maxOrNull()  // Obtém a data mais recente

// Verifica se a última data é diferente da data de hoje
        if (imcList.isEmpty() || lastDate == null || lastDate != currentDate) {
            permitirRegistro = true
            RegistroStatus = "REGISTRAR IMC"
        } else {
            permitirRegistro = false
            RegistroStatus = "REGISTRO DE HOJE JÁ FOI FEITO"
        }

        Button(

            enabled = permitirRegistro == true || imcList.isEmpty(),
            modifier = modifier.width(315.dp).offset(0.dp, 45.dp),
            onClick = {
                try {

                    val intent = Intent(activity, CadastroDeIMC1::class.java)
                    intent.flags = FLAG_ACTIVITY_SINGLE_TOP
                    activity?.startActivity(intent)

                } catch (e: Exception) {
                    e.printStackTrace() // Captura exceções para depuração
                    Toast.makeText(activity, "Erro ao iniciar atividade", Toast.LENGTH_SHORT).show()
                }

            },
            colors = ButtonColors(
                containerColor = Aqua80,
                contentColor = GrayD,
                disabledContainerColor = GrayD,
                disabledContentColor = White,
            ),

            ) {

            Text(
                RegistroStatus,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        }
    }
}