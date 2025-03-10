package com.example.medidordeimc


import android.R.attr.startX
import android.R.attr.startY
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medidordeimc.db.fb.FBDatabase
import com.example.medidordeimc.ui.theme.Aqua80
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.White

private val paint = Paint()

@Composable
fun ScatterPlot(
    modifier: Modifier = Modifier,
    xValues: List<String>,
    yValues: List<Int>,
    points: List<Float>,
    interval: Int,
    dist: Int
) {
    val fbDB = remember { FBDatabase() }
    val viewModel : MainViewModel = viewModel(
        factory = MainViewModelFactory(fbDB)
    )


    Box(modifier.size(300.dp), contentAlignment = Alignment.Center) {
        val textPaint = Paint().apply {
            textSize = 20f
            color = Color.Black.toArgb()
        }

        Canvas(
            modifier = Modifier.fillMaxSize()

        ) {



            drawLine(
                start = Offset(x = 12F, y = -50F),  // Ponto inicial (topo)
                end = Offset(x = 12F, y = 850F),   // Ponto final (base)
                color = Color.Black,
                strokeWidth = 3F // Ajustei a espessura para um valor mais razoável
            )
            drawLine(
                start = Offset(x = 12F, y = 850F),  // Ponto inicial (topo)
                end = Offset(x = 850F, y = 850F),   // Ponto final (base)
                color = Color.Black,
                strokeWidth = 3F // Ajustei a espessura para um valor mais razoável
            )
            val maxY = yValues.maxOrNull() ?: 1
            val xSpacing = size.width / (xValues.size + 1)
            val ySpacing = size.height / (maxY -4)

            xValues.forEachIndexed { index, label ->
                drawContext.canvas.nativeCanvas.drawText(
                    label,
                    xSpacing * (index + 1),
                    size.height,
                    textPaint
                )
            }

            for (index in 0..maxY step interval) {
                drawContext.canvas.nativeCanvas.drawText(
                    index.toString(),
                    25f,
                    size.height - (ySpacing * index),
                    textPaint
                )
            }

            points.forEachIndexed { index, value ->
                val x = xSpacing+dist
                val y = size.height - (ySpacing * value)

                drawCircle(
                    color = Color.Red,
                    radius = 6f,
                    center = Offset(x, y)
                )
            }
        }
    }
}

@Composable
fun GrafPage(modifier: Modifier = Modifier) {
    val fbDB = remember { FBDatabase() }
    val viewModel: MainViewModel = viewModel(factory = MainViewModelFactory(fbDB))
    val imcList = viewModel.imcs
    var next by remember { mutableIntStateOf(0) }

    val months = listOf(" JAN ", " FEV ", " MAR ", " ABR ", " MAI ", " JUN ", " JUL ", " AGO ", " SET ", " OUT ", " NOV ", " DEZ ")

    var dist = 0
    var data = ""


    if (imcList.isNotEmpty()) {
        val dataList = imcList[0].datet

        if (dataList.length >= 5) {

            data = imcList[next].datet
            val monthPart =  data.substring(3, 5)
            dist = when (monthPart) {
                "01" -> 40
                "02" -> 80
                "03" -> 120
                "04" -> 160
                "05" -> 200
                "06" -> 240
                "07" -> 280
                "08" -> 320
                "09" -> 360
                "10" -> 400
                "11" -> 440
                "12" -> 480
                else -> 0
            }
        }
    }


// Exibe um indicador de carregamento caso a lista esteja vazia
    if (imcList.isEmpty()) {
        Text("Carregando dados...", modifier = Modifier.padding(16.dp))
        return
    }
    LaunchedEffect(imcList, next) {
        if (imcList.isNotEmpty()) {
            next = next.coerceIn(0, imcList.lastIndex) // Impede valores inválidos
        }

    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(10.dp, 10.dp)
            .fillMaxSize()
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false,
                ambientColor = GrayD,
                spotColor = GrayD
            )
            .border(2.dp, White, shape = RoundedCornerShape(25.dp))
            .background(White, shape = RoundedCornerShape(25.dp))
    ) {
        Text(
            text = data,
            fontSize = 20.sp,
            color = GrayD,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = modifier.offset(0.dp, (-10).dp)
        )

            ScatterPlot(
                xValues = months,
                yValues = listOf(0, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15).map { it * 12 },
                interval = 10,
                modifier = Modifier
                    .size(475.dp)
                    .width(525.dp),
                points = listOf(imcList[next].imc),
                dist = dist
            )


        Button(
            modifier = modifier
                .width(315.dp)
                .offset(0.dp, 15.dp),
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
            )
        ) {
            Text(
                "PRÓXIMO GRÁFICO",
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
            modifier = modifier
                .width(315.dp)
                .offset(0.dp, 15.dp),
            colors = ButtonColors(
                containerColor = Aqua80,
                contentColor = GrayD,
                disabledContainerColor = GrayD,
                disabledContentColor = White,
            )
        ) {
            Text("GRÁFICO ANTERIOR",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold)
        }
    }
}
