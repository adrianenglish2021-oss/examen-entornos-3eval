import java.util.Scanner;

/**
 * Clase principal ejecutora (Application Entry Point).
 * Utiliza un objeto Scanner para pedir entradas al usuario interactivamente 
 * y mantener vivo el Bucle de Juego hasta que el jugador decida salir o pierda.
 */
public class Main {
    
    /**
     * Método de arranque principal.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MotorJuego motor = new MotorJuego();
        GestorEntradas gestor = new GestorEntradas();

        System.out.println("=======================================");
        System.out.println("      BIENVENIDO A CONSOLE-MAN         ");
        System.out.println("=======================================");
        System.out.println("Comandos disponibles:");
        System.out.println(" - INICIAR, PAUSAR, REANUDAR, SALIR");
        System.out.println(" - ARRIBA, ABAJO, IZQUIERDA, DERECHA");
        System.out.println(" - ACCION (Para hacer un salto rápido)");
        System.out.println("Objetivo: Recoge 2 puntos (200 pts) antes de morir.");
        System.out.println("=======================================\n");

        // 1. Preparación de Entidades (Nivel)
        Jugador pacman = new Jugador("Pac-Man", 50, 50);
        motor.setJugador(pacman);
        
        motor.añadirEntidad(new Enemigo("Blinky", 65, 50, pacman));
        motor.añadirEntidad(new EntidadVideojuego("Manzana", "PREMIO", 50, 45, 5, 5, 1, "manzana.png"));
        motor.añadirEntidad(new EntidadVideojuego("Cereza", "PREMIO", 70, 50, 5, 5, 1, "cereza.png"));

        // 2. Bucle de Juego Interactivo (Game Loop)
        boolean jugando = true;

        while (jugando) {
            System.out.print("\n> Introduce comando: ");
            String input = scanner.nextLine();

            // Procesar la entrada del usuario
            jugando = gestor.procesarComando(input, motor);

            // Si el usuario no ha puesto SALIR, ejecutamos la lógica del frame
            if (jugando) {
                motor.actualizar();
            }

            // Si el juego termina (victoria o derrota), detenemos el bucle
            if (motor.getEstado() == MotorJuego.EstadoJuego.GAME_OVER || 
                motor.getEstado() == MotorJuego.EstadoJuego.VICTORIA) {
                jugando = false;
            }
        }

        System.out.println("Gracias por jugar a Console-Man Simulator.");
        scanner.close();
    }
}