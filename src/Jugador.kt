class Jugador(val nombre: String) {

    var fichas: MutableSet<Ficha> = mutableSetOf()
    fun colocarFicha(partida: Partida) {
        var posicion: Pair<Int, Int>? = null
        while (posicion == null || !esPosicionValida(posicion) || !partida.comprobarMovimientoValido(posicion)) {
            print("\nIntroduce la posición ('FILA,COLUMNA'): ")
            val entrada = readln()
            posicion = entrada.split(',').toIntPair()
            if (posicion == null || !esPosicionValida(posicion)) {
                println("ERROR: La posición debe estar entre 1 y 3 para fila y columna. Inténtalo de nuevo.")
            }
            else if (!partida.comprobarMovimientoValido(posicion)) {
                println("ERROR: Ya hay una ficha en esa posición.")
            }
        }
        println("${this.nombre} coloca ficha en: $posicion\n")
        fichas.add(Ficha(this, posicion))
    }

    fun List<String>.toIntPair(): Pair<Int, Int>? {
        if (this.size == 2) {
            return try {
                Pair(this[0].toInt(), this[1].toInt())
            } catch (e: NumberFormatException) {
                null
            }
        } else {
            return null
        }
    }

    fun esPosicionValida(posicion: Pair<Int, Int>): Boolean {
        val (fila, columna) = posicion
        return fila in 1..3 && columna in 1..3
    }

    fun comprobarGanador(combinacionesGanadoras: List<List<Pair<Int, Int>>>): Jugador? {
        for (condicionVictoria in combinacionesGanadoras) {
            if (condicionVictoria.all { posicion -> fichas.any { it.posicion == posicion } }) {
                return this
            }
        }
        return null
    }
}