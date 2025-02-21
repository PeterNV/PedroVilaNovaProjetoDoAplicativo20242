package com.example.medidordeimc

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medidordeimc.db.fb.FBDatabase
import com.example.medidordeimc.ui.theme.Aqua80
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.White

@Composable
fun GrafPage (modifier: Modifier = Modifier, viewModel: MainViewModel) {


    val fbDB = remember { FBDatabase() }
    val viewModel : MainViewModel = viewModel(
        factory = MainViewModelFactory(fbDB)
    )
    val imcList = viewModel.imcs


    Column(

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

        /*
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(imcList) { imc ->

                Text(
                    for(){

                    }
                    text = "$imc.",
                    fontSize = 40.sp,
                    color = GrayD,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    modifier = modifier.offset(0.dp, (-40).dp)
                )
            }
        }
          Text(

                    text = "${imcList[1]}",
            fontSize = 40.sp,
            color = GrayD,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = modifier.offset(0.dp, (-40).dp)
        )
        */

        /*

        var i by rememberSaveable { mutableIntStateOf(0) }
        i = 0

        for (imc in imcList){
            Text(

                text = if (imcList.isNotEmpty()) "${imc.imc}" else "Lista vazia!"

                ,
                fontSize = 40.sp,
                color = GrayD,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                modifier = modifier.offset(0.dp, (-40).dp)
            )
        }
        var i by rememberSaveable { mutableIntStateOf(0) }

        LaunchedEffect(i) {
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
            text = if (imcList.isNotEmpty()) "IMC: ${imcList[i].imc}" else "Lista vazia!",
            fontSize = 40.sp,
            color = GrayD,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = modifier.offset(0.dp, (-40).dp)
        )
*/
/*
        // Função para pegar o nome do mês
        fun getMonthName(month: Int): String {
            val months = listOf(
                "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
            )
            return months[month - 1]
        }

        // Transformar dados para formato que o gráfico entende
        val entriesMax = mutableListOf<Entry>()
        val entriesMin = mutableListOf<Entry>()
        val months = mutableListOf<String>()

        // Processando os dados de IMC
        val monthlyData = imcList.groupBy { imc -> imc.datet.substring(5, 7).toInt() }
        for ((month, records) in monthlyData) {
            val monthName = getMonthName(month)
            val maxImc = records.maxOf { it.imc }
            val minImc = records.minOf { it.imc }

            entriesMax.add(BarEntry(month.toFloat(), maxImc))
            entriesMin.add(BarEntry(month.toFloat(), minImc))
            months.add(monthName)
        }

        // Configurar o gráfico
        val barChart = BarChart(
            chartData = ChartData(
                entries = listOf(entriesMax, entriesMin),
                labels = months
            ),
            xAxis = XAxis().apply {
                labels = months
                labelPosition = AxisPosition.BOTTOM
            },
            yAxis = YAxis().apply {
                labelPosition = AxisPosition.LEFT
            }
        )

        // Layout do gráfico
        Column(modifier = modifier.padding(16.dp).fillMaxSize()) {
            Text(
                text = "IMC por Mês",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Adicionando o gráfico
            barChart.render(modifier = Modifier.fillMaxWidth().height(300.dp))
        }

*/


        Button(


            modifier = modifier.width(315.dp).offset(0.dp, 15.dp),
            onClick = {


            },
            colors = ButtonColors(
                containerColor = Aqua80,
                contentColor = GrayD,
                disabledContainerColor = GrayD,
                disabledContentColor = White,
            ),

            ) {
            Text(
                "PRÓXIMO GRÁFICO",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            onClick = {


            },
            modifier = modifier.width(315.dp).offset(0.dp,15.dp),
            colors= ButtonColors(
                containerColor = Aqua80,
                contentColor = GrayD,
                disabledContainerColor = GrayD,
                disabledContentColor = White,
            ),

            ) {
            Text("GRÁFICO ANTERIOR",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold)
        }



    }
}

