package com.example.medidordeimc

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
//import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Checkbox
//import androidx.compose.material3.CheckboxColors
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExposedDropdownMenuBox
//import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medidordeimc.db.fb.FBDatabase
import com.example.medidordeimc.model.IMC
import com.example.medidordeimc.ui.theme.Aqua80
import com.example.medidordeimc.ui.theme.Black
import com.example.medidordeimc.ui.theme.GrayD
import com.example.medidordeimc.ui.theme.GrayL
import com.example.medidordeimc.ui.theme.MedidorDeIMCTheme
import com.example.medidordeimc.ui.theme.Red
import com.example.medidordeimc.ui.theme.White
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

fun bitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun uploadBase64Image(base64String: String, fileName: String, onComplete: (Boolean, String?) -> Unit) {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.getReferenceFromUrl("gs://absolute-bot-445220-a5.firebasestorage.app")
    val imageRef = storageRef.child("images/$fileName")

    try {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageData = baos.toByteArray()

        val uploadTask = imageRef.putBytes(imageData)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                onComplete(true, uri.toString())
            }.addOnFailureListener {
                onComplete(false, null)
            }
        }.addOnFailureListener {
            onComplete(false, null)
        }
    } catch (e: Exception) {
        onComplete(false, null)
    }
}

class CadastroDeIMC1 : ComponentActivity() {

    private var foto: String = ""

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedidorDeIMCTheme {
                var fotoState by rememberSaveable { mutableStateOf("") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TopAppBar(
                        modifier = Modifier.shadow(
                            elevation = 5.dp,
                            clip = false
                        ),
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = White,
                            titleContentColor = Black,
                        ),
                        title = {
                            Text(
                                "Cadastro de IMC",
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.offset(105.dp, 2.dp)
                            )
                        }
                    )
                    CIMC1(
                        modifier = Modifier.padding(innerPadding),
                        verificarPermissaoECapturarFoto = { verificarPermissaoECapturarFoto() },
                        foto = fotoState,
                        setFoto = { fotoState = it }
                    )
                }
            }
        }
    }

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                abrirCamera()
            } else {
                Toast.makeText(this, "Permissão da câmera negada", Toast.LENGTH_SHORT).show()
            }
        }

    private fun verificarPermissaoECapturarFoto() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            abrirCamera()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                imageBitmap?.let { bitmap ->
                    val encodedImage = bitmapToBase64(bitmap)
                    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mm-ss")
                    val currentDate = LocalDateTime.now().format(formatter)

                    uploadBase64Image(encodedImage, "$currentDate.jpg") { success, url ->
                        if (success) {
                            foto = url ?: ""
                            Toast.makeText(this, "Imagem enviada com sucesso!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Falha no upload da imagem", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    fun abrirCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }
}

//@Preview(showBackground = true)
@Composable
fun CIMC1(modifier: Modifier = Modifier,verificarPermissaoECapturarFoto: () -> Unit,foto: String,
          setFoto: (String) -> Unit) {
    val fbDB = remember { FBDatabase() }
    val viewModel : MainViewModel = viewModel(
        factory = MainViewModelFactory(fbDB)
    )

    val db = FBDatabase()


    var altura by rememberSaveable { mutableStateOf("") }
    var peso by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity

    Column(
        //modifier = modifier.padding(19.dp,145.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,

        modifier = modifier
            .padding(19.dp, 125.dp)
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
                //Toast.makeText(activity, "Login OK!", Toast.LENGTH_LONG).show()

                verificarPermissaoECapturarFoto()
            },

            colors= ButtonColors(
                containerColor = Color.Transparent,
                contentColor = White,
                disabledContainerColor = GrayL,
                disabledContentColor = White,
            ),



        ){ Text(text = "ESCOLHA UMA FOTO",
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
        Text(
            text = "ESCOLHA O INTERVALO PARA VERIFICAR NOVMANTE O IMC",
            fontSize = 20.sp,
            color = GrayL,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = modifier.offset(0.dp, 2.dp)
        )
        var isSelectedI1 by rememberSaveable { mutableStateOf(false) }
        var isSelectedI2 by rememberSaveable { mutableStateOf(false) }
        var isSelectedI3 by rememberSaveable { mutableStateOf(false) }
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

            enabled = peso.isNotEmpty()
            ,
            modifier = modifier
                .width(315.dp)
                .offset(0.dp, 2.dp),
            onClick = {
                altura = (viewModel.user?.altura?:1).toString()
                val alturaFinal = altura.toFloat()*altura.toFloat()
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val currentDate = LocalDateTime.now().format(formatter)
                if (alturaFinal != null) {
                    db.addImc(IMC(imc = ((peso.toFloat())/alturaFinal), datet = currentDate, peso = peso.toFloat()))
                }
                /*
                activity?.startActivity(
                    Intent(activity, IMCFinal::class.java).setFlags(
                        FLAG_ACTIVITY_SINGLE_TOP
                    )
                )
                 */
                val intent = Intent(activity, IMCFinal::class.java).apply {
                    putExtra("peso", peso)
                }
                activity?.startActivity(intent)
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
            onClick = { ; altura = ""; peso = ""
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

