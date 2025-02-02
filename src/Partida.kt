class Partida(val jugadores: Map<String, Jugador>) {

    val COMBINACIONES_GANADORAS = listOf(
        listOf(Pair(1, 1), Pair(1, 2), Pair(1, 3)),
        listOf(Pair(2, 1), Pair(2, 2), Pair(2, 3)),
        listOf(Pair(3, 1), Pair(3, 2), Pair(3, 3)),
        listOf(Pair(1, 1), Pair(2, 1), Pair(3, 1)),
        listOf(Pair(1, 2), Pair(2, 2), Pair(3, 2)),
        listOf(Pair(1, 3), Pair(2, 3), Pair(3, 3)),
        listOf(Pair(1, 1), Pair(2, 2), Pair(3, 3)),
        listOf(Pair(3, 1), Pair(2, 2), Pair(1, 3))
    )

    var ganador: Jugador? = null
    var turno: Int = 0

    fun jugar() {
        while (ganador == null) {
            val jugadorActual = if (turno % 2 == 0) jugadores["jugador1"] else jugadores["jugador2"]
            if (jugadorActual != null) {
                println("TURNO DE ${jugadorActual.nombre.uppercase()}")
                jugadorActual.colocarFicha(this)
                turno ++
                ganador = jugadorActual.comprobarGanador(COMBINACIONES_GANADORAS)
            }
            mostrarEstadoPartida()
        }

        print("¡${ganador?.nombre?.uppercase()} GANA LA PARTIDA!")
        jugadores.values.forEach { jugador -> jugador.fichas = mutableSetOf() }

    }

    fun comprobarMovimientoValido(posicion: Pair<Int, Int>): Boolean {
        for (jugador in jugadores.values) {
            for (ficha in jugador.fichas) {
                if (ficha.posicion == posicion) {
                    return false
                }
            }
        }
        return true
    }

    fun mostrarEstadoPartida() {
        val estado = Array(3) { Array(3) {'-'} }

        for ((nJugador, jugador) in jugadores) {
            for (ficha in jugador.fichas) {
                val (fila, columna) = ficha.posicion
                estado[fila - 1][columna - 1] = if (nJugador == "jugador1") 'X' else 'O'
            }
        }

        for (fila in estado) {
            println("${fila.joinToString("    ")}\n")
        }
    }

}