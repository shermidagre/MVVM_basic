## Introducción

El objetivo de esta app es describir las diferentes clases y como se interrelacionan para el [modelo MVVM](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=es-419)



## Escenario
Tenemos nuestra aplicación diseñada y codificada y queremos transformarla a la arquitectura MVVC, separar el manejo de datos de la activity principal.

Además utilizar el patrón de diseño [Observer](https://es.wikipedia.org/wiki/Observer_(patr%C3%B3n_de_dise%C3%B1o))

En este caso, el único dato que vamos a manejar son enteros aleatorios. 

## Estados
Utilizamos el patrón observer para manejar los estados de la app.

Los definimos en el ViewModel y los observamos desde la IU

[Guía](https://developer.android.com/topic/libraries/architecture/livedata?hl=es-419#kotlin) de observer(LiveData) en Android