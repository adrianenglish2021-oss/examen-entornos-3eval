/**
 * Intermediario que recoge los comandos de la consola interactiva 
 * y los traduce a órdenes concretas para el Motor de Juego o el Jugador.
 */
public class GestorEntradas {
    
    /**
     * Recibe y parsea un comando de texto.
     * @param comando Cadena introducida por el usuario a través del Scanner.
     * @param motor   El contexto (MotorJuego) al que aplicar el comando.
     * @return booleano indicando si el juego debe continuar (true) o si el usuario quiere salir (false).
     */
    public boolean procesarComando(String comando, MotorJuego motor) {
        if (comando == null || comando.trim().isEmpty()) return true;

        String cmd = comando.toUpperCase().trim();

        if (cmd.equals("SALIR")) {
            System.out.println("Saliendo del juego...");
            return false;
        }

        try {
            switch (cmd) {
                case "INICIAR": motor.iniciarPartida(); break;
                case "PAUSAR": motor.pausar(); break;
                case "REANUDAR": motor.reanudar(); break;
                case "ARRIBA":
                case "ABAJO":
                case "IZQUIERDA":
                case "DERECHA":
                    if (motor.getEstado() == MotorJuego.EstadoJuego.JUGANDO && motor.getJugador() != null) {
                        motor.getJugador().desplazarEntidad(cmd);
                    } else {
                        System.out.println("Debes INICIAR la partida para moverte o REANUDARLA.");
                    }
                    break;
                case "ACCION":
                    if (motor.getEstado() == MotorJuego.EstadoJuego.JUGANDO && motor.getJugador() != null) {
                        motor.getJugador().pulsarBotonAccion();
                    }
                    break;
                default:
                    System.out.println("Comando no reconocido. Usa: INICIAR, ARRIBA, ABAJO, IZQUIERDA, DERECHA, ACCION, PAUSAR o SALIR.");
            }
        } catch (Exception e) {
            System.out.println("Error procesando entrada: " + e.getMessage());
        }

        return true; // El juego continúa por defecto
    }
}