/**
 * Función de extensión para convertir una lista de cadenas en un par de enteros (Pair).
 *
 * Esta función recibe una lista de dos elementos que representan coordenadas (fila y columna),
 * y convierte estos elementos a un par de enteros (Pair<Int, Int>). Si la lista no tiene
 * exactamente dos elementos o si los elementos no son enteros válidos, retorna null.
 *
 * @return Un `Pair<Int, Int>` con las coordenadas o `null` si la entrada es inválida.
 */
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

/**
 * Configura los jugadores del juego.
 *
 * Solicita al usuario ingresar el nombre de dos jugadores y los almacena en un mapa.
 * El mapa tiene claves "jugador1" y "jugador2", y los valores son objetos `Jugador` con el nombre correspondiente.
 *
 * @return Un mapa inmutable de jugadores, donde las claves son los nombres de los jugadores y los valores son los objetos `Jugador`.
 */
fun configurarJugadores(): Map<String, Jugador> {
    var jugadores: MutableMap<String, Jugador> = mutableMapOf()

    println("CONFIGURANDO JUGADORES:\n")
    for (i in 1..2) {
        print("Introduce el nombre del jugador #${i}: ")
        jugadores["jugador$i"] = Jugador(readln())
    }
    return jugadores.toMap()
}

/**
 * Muestra el menú principal del juego.
 *
 * Esta función imprime en la consola las opciones disponibles para el usuario:
 * 1. Jugar
 * 2. Configurar jugadores
 * 3. Salir
 */
fun mostrarMenu() {
    print("""
         
         BIENVENIDO AL TRES EN RAYA
          
          1. Jugar.
          2. Configurar jugadores.
          3. Salir.
          
          >> 
          """.trimIndent())
}

/**
 * Función principal que ejecuta el flujo del juego.
 *
 * Esta función maneja la lógica principal del juego. Permite al usuario elegir entre:
 * - Jugar una partida (si los jugadores han sido configurados previamente).
 * - Configurar los jugadores.
 * - Salir del juego.
 *
 * El flujo del juego se ejecuta en un bucle que permite al usuario elegir opciones hasta que decida salir.
 */
fun main() {

    var jugadores: Map<String, Jugador> = mapOf()
    var jugadoresConfigurados = false
    var opcion = ""

    while(opcion != "3") {
        mostrarMenu()
        opcion = readln()
        when (opcion) {
            "1" -> {
                if (jugadoresConfigurados) {
                    val partida = Partida(jugadores)
                    partida.jugar()
                } else {
                    println("ERROR: Antes de jugar debes configurar los jugadores.")
                }
            }
            "2" -> {
                jugadores = configurarJugadores()
                jugadoresConfigurados = true
            }
            "3" -> break
            else -> println("ERROR: Opción inválida.")
        }
    }
}