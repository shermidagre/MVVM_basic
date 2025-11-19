package com.dam.mvvm_basic

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MyViewModel(): ViewModel() {

    // etiqueta para logcat
    private val TAG_LOG = "miDebug"

    // estados del juego
    // usamos LiveData para que la IU se actualice
    // patron de diseño observer
    val estadoLiveData: MutableLiveData<Estados?> = MutableLiveData(Estados.INICIO)

    val estadosBoton: MutableLiveData<EstadosBotonAvanzar?> =
        MutableLiveData(EstadosBotonAvanzar.E1)


    // este va a ser nuestra lista para la secuencia random
    // usamos mutable, ya que la queremos modificar
    var _numbers = mutableStateOf(0)

    // inicializamos variables cuando instanciamos
    init {
        // estado inicial
        Log.d(TAG_LOG, "Inicializamos ViewModel - Estado: ${estadoLiveData.value}")
    }

    // contador para la barra de progreso
    var contador = MutableStateFlow<Int>(0)



    /**
     * crear entero random
     */
    fun crearRandom() {
        // cambiamos estado, por lo tanto la IU se actualiza
        estadoLiveData.value = Estados.GENERANDO
        _numbers.value = (0..3).random()
        Log.d(TAG_LOG, "creamos random ${_numbers.value} - Estado: ${estadoLiveData.value}")
        actualizarNumero(_numbers.value)
    }
    fun crearRandom2() {
        // cambiamos estado, por lo tanto la IU se actualiza
        estadoLiveData.value = Estados.CARGANDO
        _numbers.value = (0..100).random()
        Log.d(TAG_LOG, "creamos random ${_numbers.value} - Estado: ${estadoLiveData.value}")
        actualizarNumero2(_numbers.value)
        if (_numbers.value >= 100) {
            estadoLiveData.value = Estados.FINALIZANDO
        }
        else {
            estadoLiveData.value = Estados.GENERANDO
        }
        simularDescarga()

    }

    fun actualizarNumero(numero: Int) {
        Log.d(TAG_LOG, "actualizamos numero en Datos - Estado: ${estadoLiveData.value}")
        Datos.numero = numero
        // cambiamos estado, por lo tanto la IU se actualiza
        estadoLiveData.value = Estados.ADIVINANDO
    }

    fun actualizarNumero2(numero: Int) {
        Log.d(TAG_LOG, "actualizamos numero en Datos - Estado: ${estadoLiveData.value}")
        Datos.numero = numero
        estadoLiveData.value = Estados.ADIVINANDO
        estadoLiveData.value = Estados.FINALIZANDO
    }

    private fun crearTextoE1() {
        Log.d(TAG_LOG, "Estado E1: crear texto")
    }

    private fun cuentaAtrasE2() {
        Log.d(TAG_LOG, "Estado en E2: cuenta atrás")
    }

    private fun sonidoE3() {
        Log.d(TAG_LOG, "Estado en E3: sonido")
    }

    fun avanzarEstadoBotonAvanzar() {
        val estadoActual = estadosBoton.value

        when (estadoActual) {
            EstadosBotonAvanzar.E1 -> crearTextoE1()
            EstadosBotonAvanzar.E2 -> cuentaAtrasE2()
            EstadosBotonAvanzar.E3 -> sonidoE3()
            else -> {}
        }

        val siguienteEstado = when (estadoActual) {
            EstadosBotonAvanzar.E1 -> EstadosBotonAvanzar.E2
            EstadosBotonAvanzar.E2 -> EstadosBotonAvanzar.E3
            EstadosBotonAvanzar.E3 -> EstadosBotonAvanzar.E1
            else -> null
        }

        estadosBoton.value = siguienteEstado
        Log.d(TAG_LOG, "Boton Avanzar - Estado Actualizado: ${siguienteEstado}")
    }




    /**
     * comprobar si el boton pulsado es el correcto
     * @param ordinal: Int numero de boton pulsado
     * @return Boolean si coincide TRUE, si no FALSE
     */
    fun comprobar(ordinal: Int): Boolean {

        // mientras comprobamos, lanzamos estados auxiliares en paralelo
        estadosAuxiliares()

        Log.d(TAG_LOG, "comprobamos - Estado: ${estadoLiveData.value}")
        return if (ordinal == Datos.numero) {
            Log.d(TAG_LOG, "es correcto")
            estadoLiveData.value = Estados.INICIO
            Log.d(TAG_LOG, "GANAMOS - Estado: ${estadoLiveData.value}")
            true
        } else {
            Log.d(TAG_LOG, "no es correcto")
            estadoLiveData.value = Estados.ADIVINANDO
            Log.d(TAG_LOG, "otro intento - Estado: ${estadoLiveData.value}")
            false
        }
    }

    /**
     * Corutina que lanza estados auxiliares
     */
    fun estadosAuxiliares() {
        viewModelScope.launch {
            // guardamos el estado auxiliar
            var estadoAux = EstadosAuxiliares.AUX1

            // hacemos un cambio a tres estados auxiliares
            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            delay(1500)
            estadoAux = EstadosAuxiliares.AUX2
            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            delay(1500)
            estadoAux = EstadosAuxiliares.AUX3
            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            delay(1500)
        }
    }
    fun simularDescarga() {
        viewModelScope.launch {

            while (contador.value < 100) {
                if (_numbers.value <= 20) {
                    delay(20)
                    contador.value += 1
                } else if (_numbers.value <= 40) {
                    delay(40)
                    contador.value += 1
                } else if (_numbers.value <= 60) {
                    delay(60)
                    contador.value += 1
                } else if (_numbers.value <= 80) {
                    delay(80)
                    contador.value += 1
                } else if (_numbers.value < 100) {
                    delay(100)
                    contador.value += 1
                }
                if (contador.value >= 100) {
                    estadoLiveData.value = Estados.FINALIZANDO
                }
                Log.d(TAG_LOG, "iteraccion contador: ${contador.value}")
            }
        }
    }
}