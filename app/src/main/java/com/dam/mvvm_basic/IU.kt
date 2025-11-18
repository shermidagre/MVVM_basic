package com.dam.mvvm_basic

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * Interfaz de usuario
 * Modificado desde Code
 */

@Composable
fun IU(miViewModel: MyViewModel) {
    val numeroGenerado by miViewModel._numbers
    val estado by miViewModel.estadoLiveData.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = "Número generado: ${numeroGenerado}")
        Text(text = "Estado: ${estado?.name ?: "INICIO"}")
        mostrarPorcentaje(miViewModel)

        Column {
            Row {
                Boton(miViewModel, Colores.CLASE_ROJO)
                Boton(miViewModel, Colores.CLASE_VERDE)
            }
            Row {
                Boton(miViewModel, Colores.CLASE_AZUL)
                Boton(miViewModel, Colores.CLASE_AMARILLO)
            }
        }

        BotonConEfecto(
            enum_color = Colores.CLASE_START,
            activo = estado?.start_activo ?: true,
            onClick = {
                Log.d("miDebug", "Dentro del Start - Estado: ${estado?.name}")
                miViewModel.crearRandom()
            }
        )

        BotonConEfecto(
            enum_color = Colores.CLASE_DESCARGAR,
            activo = estado?.start_activo ?: true,
            onClick = {
                Log.d("miDebug", "Dentro del Descargar - Estado: ${estado?.name}")
                miViewModel.crearRandom2()
            }
        )
    }
}

@Composable
fun Boton(miViewModel: MyViewModel, enum_color: Colores) {

    // para que sea mas facil la etiqueta del log
    val TAG_LOG = "miDebug"

    val estado by miViewModel.estadoLiveData.observeAsState()
    val activo = estado?.boton_activo ?: false


    // separador entre botones
    Spacer(modifier = Modifier.size(10.dp))

    Button(
        enabled = activo,
        // utilizamos el color del enum
        colors =  ButtonDefaults.buttonColors(enum_color.color),
        onClick = {
            Log.d(TAG_LOG, "Dentro del boton: ${enum_color.ordinal}")
            miViewModel.comprobar(enum_color.ordinal)
        },
        modifier = Modifier
            .size((80).dp, (40).dp)
    ) {
        // utilizamos el texto del enum
        Text(text = enum_color.txt, fontSize = 10.sp)
    }
}

@Composable
fun BotonConEfecto(enum_color: Colores, activo: Boolean, onClick: () -> Unit) {
    var color by remember(enum_color) { mutableStateOf(enum_color.color) }

    LaunchedEffect(key1 = activo, key2 = enum_color) {
        if (activo) {
            while (true) {
                color = enum_color.color_suave
                delay(100)
                color = enum_color.color
                delay(500)
            }
        } else {
            // Reset to original color when not active
            color = enum_color.color
        }
    }

    Spacer(modifier = Modifier.size(40.dp))
    Button(
        enabled = activo,
        colors = ButtonDefaults.buttonColors(color),
        onClick = onClick,
        modifier = Modifier.size(100.dp, 40.dp)
    ) {
        Text(text = enum_color.txt, fontSize = 10.sp)
    }
}

@Composable
fun mostrarPorcentaje(myViewModel: MyViewModel){
    val numero = myViewModel.contador.collectAsState().value
    Text(
        text = "Porcentaje: $numero",
        fontSize = 20.sp,
    )
}

/**
 * Preview de la interfaz de usuario
 */

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun IUPreview() {
    IU(MyViewModel())
}