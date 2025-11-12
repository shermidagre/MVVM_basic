## Introducción

El objetivo de esta app es describir las diferentes clases y como se interrelacionan para el [modelo MVVM](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=es-419)

Una buena guía es [esta](https://developer.android.com/codelabs/basic-android-kotlin-training-livedata?hl=es-419#0).

## Escenario
Tenemos nuestra aplicación diseñada y codificada y queremos transformarla a la arquitectura MVVC, separar el manejo de datos de la activity principal.

Además utilizar el patrón de diseño [Observer](https://es.wikipedia.org/wiki/Observer_(patr%C3%B3n_de_dise%C3%B1o))

En este caso, el único dato que vamos a manejar son enteros aleatorios. 

## Corrutinas
En esta rama vamos a usar corrutinas:

- En el ViewModel con la función `estadosAuxiliares` utilizando `viewModelScope.launch { }`
- En la IU con `LaunchedEffect(_activo)` en el botón start
