/**
* Clase que representa a un jugador en el juego Tres en Raya.
*
* Un jugador tiene un nombre y un conjunto mutable de fichas que ha colocado en el tablero.
* Cada jugador puede colocar fichas en posiciones específicas del tablero y verificar si ha ganado la partida.
*
* @param nombre El nombre del jugador.
*/
class Jugador(val nombre: String) {

    var fichas: MutableSet<Ficha> = mutableSetOf()

    /**
    * Coloca una ficha en la posición especificada por el jugador en el tablero.
    *
    * Esta función agrega una nueva ficha al conjunto de fichas del jugador en la posición proporcionada.
    *
    * @param posicion La posición (fila, columna) donde el jugador desea colocar la ficha.
    */
    fun colocarFicha(posicion: Pair<Int, Int>) {
        fichas.add(Ficha(this, posicion))
    }

    /**
    * Verifica si una posición dada es válida en el tablero de Tres en Raya.
    *
    * La posición es válida si está dentro del rango de filas y columnas permitidas (1 a 3).
    *
    * @param posicion La posición que se desea verificar.
    * @return `true` si la posición está dentro del rango válido (1-3), `false` en caso contrario.
    */
    fun esPosicionValida(posicion: Pair<Int, Int>): Boolean {
        val (fila, columna) = posicion
        return fila in 1..3 && columna in 1..3
    }

    /**
    * Comprueba si el jugador ha ganado la partida en base a las combinaciones ganadoras.
    *
    * La función recorre las combinaciones ganadoras y verifica si todas las posiciones de cada
    * combinación están ocupadas por fichas del jugador.
    *
    * @param combinacionesGanadoras Lista de listas de posiciones que representan las combinaciones
    *                                ganadoras en el tablero.
    * @return El jugador si ha ganado, o `null` si no ha ganado aún.
    */
    fun comprobarGanador(combinacionesGanadoras: List<List<Pair<Int, Int>>>): Jugador? {
        for (condicionVictoria in combinacionesGanadoras) {
            if (condicionVictoria.all { posicion -> fichas.any { it.posicion == posicion } }) {
                return this
            }
        }
        return null
    }

    /* OTRA OPCIÓN MÁS ÓPTIMA:

    /**
    * Comprueba si el jugador ha ganado la partida utilizando un conjunto de posiciones.
    *
    * Esta versión es más eficiente porque usa un conjunto de posiciones, lo que reduce
    * la complejidad de la búsqueda al no tener que recorrer todas las fichas repetidamente.
    *
    * @param combinacionesGanadoras Lista de listas de posiciones que representan las combinaciones
    *                                ganadoras en el tablero.
    * @return El jugador si ha ganado, o `null` si no ha ganado aún.
    */
    fun comprobarGanador(combinacionesGanadoras: List<List<Pair<Int, Int>>>): Jugador? {
    val posicionesSet = fichas.map { it.posicion }.toSet()

    return if (combinacionesGanadoras.any { it.all { pos -> pos in posicionesSet } }) this else null
    }

    */
}