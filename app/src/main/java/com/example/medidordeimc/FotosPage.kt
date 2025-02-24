package com.example.medidordeimc

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.medidordeimc.db.fb.FBDatabase
import com.example.medidordeimc.ui.theme.Aqua80
import com.example.medidordeimc.ui.theme.Black
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.GrayL
import com.example.medidordeimc.ui.theme.White
import com.google.firebase.storage.FirebaseStorage

@Composable
fun FotosPage(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val activity = LocalContext.current as? Activity
    val fbDB = remember { FBDatabase() }
    val viewModel: MainViewModel = viewModel(factory = MainViewModelFactory(fbDB))
    val imcList = viewModel.imcs

    var next by remember { mutableIntStateOf(0) }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Atualiza a referência de imagem sempre que `next` mudar
    val storage = FirebaseStorage.getInstance()
    val storageRef = remember(next, imcList) {
        if (imcList.isNotEmpty()) {
            storage.getReference("images/${imcList[next].fotoname}.jpg")
        } else {
            null
        }
    }

    // Garante que `next` esteja dentro dos limites da lista
    LaunchedEffect(imcList, next) {
        if (imcList.isNotEmpty()) {
            next = next.coerceIn(0, imcList.lastIndex) // Impede valores inválidos
        }
        if (storageRef != null) {
            isLoading = true
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                imageUrl = uri.toString()
                isLoading = false
            }.addOnFailureListener {
                isLoading = false
            }
        } else {
            isLoading = false
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(16.dp, 55.dp)
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
            text = imcList.getOrNull(next)?.datet ?: "Nenhuma data disponível",
            fontSize = 22.sp,
            color = Black,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = modifier
                .offset(12.dp, (-15).dp)
                .align(Alignment.CenterHorizontally)
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Imagem do usuário",
                    modifier = Modifier
                        .height(324.dp)
                        .width(146.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .border(2.dp, GrayD, RoundedCornerShape(15.dp))
                        .background(GrayL),
                    contentScale = ContentScale.Crop
                )
            } ?: Text(
                "Nenhuma imagem encontrada",
                color = Color.Gray,
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = modifier.width(315.dp),
            onClick = {
                if (next < imcList.lastIndex) {
                    next++  // Vai para a próxima imagem
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
                "PRÓXIMA FOTO",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = {
                if (next > 0) {
                    next--  // Volta para a imagem anterior
                }
            },
            modifier = modifier.width(315.dp),
            colors = ButtonColors(
                containerColor = Aqua80,
                contentColor = GrayD,
                disabledContainerColor = GrayD,
                disabledContentColor = White,
            ),
        ) {
            Text(
                "FOTO ANTERIOR",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
