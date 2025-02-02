/**
 * Clase que representa una partida de Tres en Raya.
 *
 * Esta clase gestiona la lógica de la partida, controlando los turnos de los jugadores,
 * las combinaciones ganadoras, la validación de movimientos, y el estado de la partida.
 * Los jugadores interactúan con el tablero mediante la colocación de fichas y la partida
 * avanza hasta que hay un ganador o se llega a un empate.
 *
 * @param jugadores Un mapa que contiene a los dos jugadores, con las claves "jugador1" y "jugador2".
 */
class Partida(val jugadores: Map<String, Jugador>) {

    /**
    * * Las combinaciones de posiciones que resultan en una victoria en el juego.
    */
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

    /**
    * Inicia y gestiona el flujo de la partida, alternando entre los jugadores hasta que haya un ganador
    * o se declare empate.
    */
    fun jugar() {
        while (true) {
            val jugadorActual = if (turno % 2 == 0) jugadores["jugador1"] else jugadores["jugador2"]
            if (jugadorActual != null) {
                println("TURNO DE ${jugadorActual.nombre.uppercase()}\n")
                mostrarEstadoPartida()
                jugadorActual.colocarFicha(solicitarPosicion(jugadorActual))
                turno ++
                ganador = jugadorActual.comprobarGanador(COMBINACIONES_GANADORAS)
                if (ganador != null) {
                    println("¡${ganador?.nombre?.uppercase()} GANA LA PARTIDA!\n")
                    break
                }
                else if (comprobarResultadoTablas()) {
                    println("RESULTADO: ¡TABLAS!\n")
                    break
                }
            }
        }

        mostrarEstadoPartida()
        jugadores.values.forEach { jugador -> jugador.fichas = mutableSetOf() }
    }

    /**
    * Solicita al jugador que ingrese la posición donde desea colocar su ficha.
    *
    * Realiza validaciones sobre la posición ingresada, asegurándose de que esté en el formato correcto
    * y que no haya ninguna ficha ocupando la posición seleccionada.
    *
    * @param jugador El jugador que está realizando el movimiento.
    * @return La posición (fila, columna) donde el jugador desea colocar su ficha.
    */
    fun solicitarPosicion(jugador: Jugador): Pair<Int, Int> {
        var posicion: Pair<Int, Int>? = null
        while (true) {
            print("\n${jugador.nombre}, introduce la posición ('FILA,COLUMNA'): ")
            val entrada = readln()
            posicion = entrada.split(',').toIntPair()

            when {
                posicion == null -> println("ERROR: Formato incorrecto. Debe ser '1,2'.")
                !jugador.esPosicionValida(posicion) -> println("ERROR: La posición debe estar entre 1 y 3.")
                !comprobarMovimientoValido(posicion) -> println("ERROR: Ya hay una ficha en esa posición.")
                else -> return posicion
            }
        }
    }

    /**
    * Verifica si la posición solicitada es válida para realizar el movimiento.
    *
    * Un movimiento es válido si no hay ninguna ficha ocupando esa posición.
    *
    * @param posicion La posición que se desea verificar.
    * @return `true` si la posición está libre, `false` si ya está ocupada.
    */
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

    /**
    * Muestra el estado actual del tablero.
    *
    * El tablero se representa en forma de una matriz 3x3, donde las fichas de los jugadores
    * se representan con 'X' (jugador1) y 'O' (jugador2). Las posiciones vacías se muestran con '-'.
    */
    fun mostrarEstadoPartida() {
        val estado = Array(3) { Array(3) {'-'} }

        for ((nJugador, jugador) in jugadores) {
            for (ficha in jugador.fichas) {
                val (fila, columna) = ficha.posicion
                estado[fila - 1][columna - 1] = if (nJugador == "jugador1") 'X' else 'O'
            }
        }
        println(estado.joinToString("\n\n") { it.joinToString("     ") } + "\n")
    }

    /**
    * Verifica si la partida ha terminado en tablas (empate).
    *
    * Un empate ocurre cuando todas las casillas del tablero están ocupadas y no hay un ganador.
    *
    * @return `true` si el tablero está lleno y no hay ganador, `false` en caso contrario.
    */
    fun comprobarResultadoTablas(): Boolean {
        val totalFichas: MutableSet<Ficha> = mutableSetOf()
        for (jugador in jugadores.values) {
            totalFichas += jugador.fichas
        }

        return totalFichas.size == 9
    }
}