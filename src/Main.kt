fun configurarJugadores(): Map<String, Jugador> {
    var jugadores: MutableMap<String, Jugador> = mutableMapOf()

    println("CONFIGURANDO JUGADORES:\n")
    for (i in 1..2) {
        print("Introduce el nombre del jugador #${i}: ")
        jugadores["jugador$i"] = Jugador(readln())
    }

    return jugadores.toMap()
}

fun mostrarMenu() {
    print("""
         
         BIENVENIDO AL TRES EN RAYA
          
          1. Jugar.
          2. Configurar jugadores.
          3. Salir.
          
          >> 
          """.trimIndent())
}

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