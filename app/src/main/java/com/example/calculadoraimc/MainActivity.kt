package com.example.calculadoraimc

import android.R.attr.fontWeight
import android.R.attr.visible
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculadoraIMCScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculadoraIMCScreen(modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var altura by remember {
        mutableStateOf("")
    }

    var peso by remember {
        mutableStateOf("")
    }

    var isCardVisible by remember {
        mutableStateOf(false)
    }

    var resultadoIMC by remember {
        mutableStateOf(0.0)
    }

    var statusIMC by remember {
        mutableStateOf("")
    }

    var corCard by remember {
        mutableStateOf(Color.White)
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
//        --- header ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(color = colorResource(R.color.cor_app)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.bmi),
                contentDescription = "Logo App",
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(60.dp)
            )
            Text(
                text = "Calculadora IMC",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

//        --- form ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(370.dp)
                    .offset(y = (-30).dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF9F6F6)
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Seus dados",
                        color = colorResource(id = R.color.cor_app),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(vertical = 17.dp)
                    )
                    OutlinedTextField(
                        value = altura,
                        singleLine = true,
                        onValueChange = { newAltura ->
                            val filtrado = newAltura.filter { it.isDigit() }
                            if (filtrado.length <= 3) altura = newAltura
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RoundedCornerShape(16.dp),
                        placeholder = {
                            Text("Altura")
                        },
                        label = {
                            Text("Altura")
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(R.color.cor_app),
                            focusedBorderColor = colorResource(R.color.cor_app)
                        ),
                        trailingIcon = {
                            Text(
                                text = "CM",
                                color = Color.Gray,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            )
                        }
                    )
                    OutlinedTextField(
                        value = peso,
                        singleLine = true,
                        onValueChange = { newPeso ->
                            val normalizado = newPeso.replace(",", ".")

                            if (normalizado.count { it == '.' } <= 1 && normalizado.all { it.isDigit() || it == '.' }) {
                               val partes = normalizado.split(".")
                               val casasDecimais = if (partes.size > 1) partes[1].length else 0
                               if (casasDecimais <= 2) {
                                   peso = newPeso
                               }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RoundedCornerShape(16.dp),
                        placeholder = {
                            Text("Peso")
                        },
                        label = {
                            Text("Peso")
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(R.color.cor_app),
                            focusedBorderColor = colorResource(R.color.cor_app)
                        ),
                        trailingIcon = {
                            Text(
                                text = "KG",
                                color = Color.Gray,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            )
                        }
                    )
                    Button(
                        onClick = {
                            keyboardController?.hide()

                            val alturaInt = altura.toIntOrNull()
                            val pesoDouble = peso.replace(",", ".").toDoubleOrNull()

                            if (alturaInt != null && pesoDouble != null && alturaInt > 0 && pesoDouble > 0) {
                                resultadoIMC = calcularIMC(alturaInt, pesoDouble)
                                corCard = definirCorCard(resultadoIMC)
                                statusIMC = definirStatusIMC(resultadoIMC)
                                isCardVisible = true
                            }
                        },
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.cor_app),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "CALCULAR",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    AnimatedVisibility(
                        visible = isCardVisible
                    ) {
                        Button(
                            onClick = {
                                altura = ""
                                peso = ""
                                isCardVisible = false
                            },
                            shape = RoundedCornerShape(32.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 15.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.LightGray,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "LIMPAR",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }

//            --- card resultado ---
            AnimatedVisibility(
                visible = isCardVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = corCard
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "%.2f".format(resultadoIMC),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(
                            modifier = Modifier
                                .width(25.dp)
                        )
                        Text(
                            text = statusIMC,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

fun calcularIMC(altura: Int, peso: Double): Double {
    val alturaMetros = altura / 100.0
    return peso / (alturaMetros * alturaMetros)
}

fun definirCorCard(imc: Double): Color {
    return if (imc < 18.5 || imc >= 30) {
        Color(0xFFFF2C2C)
    } else if (imc < 25){
        Color(0xFF008000)
    } else {
        Color(0xFFF3A024)
    }
}

fun definirStatusIMC(imc: Double): String {
    return if (imc < 18.5) {
        "ABAIXO DO PESO"
    } else if (imc < 25) {
        "PESO IDEAL"
    } else if (imc < 30) {
        "LEVEMENTE ACIMA DO PESO"
    } else if (imc < 35) {
        "OBESIDADE GRAU I"
    } else if (imc < 40){
        "OBESIDADE GRAU II"
    } else {
        "OBESIDADE GRAU III"
    }
}