import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase central del sistema. Gestiona el Bucle de Juego (Game Loop),
 * el flujo de la partida y las interacciones entre entidades.
 * Implementa la Funcionalidad Avanzada 1: Detector matemático de Colisiones AABB.
 */
public class MotorJuego {
    /** Posibles fases del sistema */
    public enum EstadoJuego { MENU, JUGANDO, PAUSA, GAME_OVER, VICTORIA }
    
    private EstadoJuego estadoActual;
    private List<EntidadVideojuego> entidades;
    private Jugador jugadorPrincipal;
    private int puntuacion;

    /**
     * Constructor del Motor. Inicia en el estado MENÚ y prepara la colección.
     */
    public MotorJuego() {
        this.estadoActual = EstadoJuego.MENU;
        this.entidades = new ArrayList<>();
        this.puntuacion = 0;
    }

    // --- Control de Estados ---
    public void iniciarPartida() {
        if (jugadorPrincipal == null) {
            System.out.println("Error: No se puede iniciar sin un jugador.");
            return;
        }
        estadoActual = EstadoJuego.JUGANDO;
        System.out.println("\n=======================================");
        System.out.println("          PARTIDA INICIADA             ");
        System.out.println("=======================================");
    }
    
    public void pausar() { estadoActual = EstadoJuego.PAUSA; System.out.println("\n[ PARTIDA PAUSADA ]"); }
    public void reanudar() { estadoActual = EstadoJuego.JUGANDO; System.out.println("\n[ PARTIDA REANUDADA ]"); }
    public void forzarGameOver() { estadoActual = EstadoJuego.GAME_OVER; System.out.println("\n*** FIN DEL JUEGO (GAME OVER) ***"); }

    // --- Gestión de Entidades ---
    public void setJugador(Jugador j) {
        this.jugadorPrincipal = j;
        añadirEntidad(j);
    }
    public Jugador getJugador() { return jugadorPrincipal; }
    public EstadoJuego getEstado() { return estadoActual; }

    public void añadirEntidad(EntidadVideojuego e) {
        try {
            if (e != null) { 
                entidades.add(e); 
            }
        } catch (Exception ex) {
            System.out.println("Error al añadir entidad: " + ex.getMessage());
        }
    }

    /**
     * Bucle lógico central. Llama al método actualizar() de todos los objetos en escena,
     * comprueba eventos físicos, evalúa la condición de partida y muestra el HUD.
     */
    public void actualizar() {
        if (estadoActual != EstadoJuego.JUGANDO) return;

        System.out.println("\n--- Procesando Turno ---");
        
        // 1. Actualizar Entidades (Enemigos se mueven, etc)
        for (EntidadVideojuego e : entidades) {
            e.actualizar();
        }

        // 2. Comprobar físicas
        comprobarColisiones();

        // 3. Evaluar condiciones de victoria o derrota
        if (jugadorPrincipal.getVida() <= 0) {
            forzarGameOver();
            return;
        }

        if (puntuacion >= 200) {
            estadoActual = EstadoJuego.VICTORIA;
            System.out.println("\n*** ¡HAS GANADO! RECOLECTASTE TODOS LOS PREMIOS ***");
            return;
        }

        // 4. Mostrar HUD interactivo
        mostrarPantallaHUD();
    }

    /**
     * Muestra la información visual por consola para que el usuario pueda tomar decisiones.
     */
    private void mostrarPantallaHUD() {
        System.out.println("\n=== RADAR DEL JUEGO ===");
        for (EntidadVideojuego e : entidades) {
            System.out.printf(" - %s está en (%d, %d)\n", e.getNombre(), e.getX(), e.getY());
        }
        System.out.println("-----------------------");
        System.out.printf("  VIDAS: %d  |  PUNTOS: %d\n", jugadorPrincipal.getVida(), puntuacion);
        System.out.println("=======================");
    }

    /**
     * Funcionalidad Avanzada 1: Detección de colisiones matemáticas tipo AABB
     * (Axis-Aligned Bounding Box). Revisa si los "cuadrados" de dos entidades se solapan.
     */
    private void comprobarColisiones() {
        Iterator<EntidadVideojuego> iterador = entidades.iterator();
        List<EntidadVideojuego> entidadesAEliminar = new ArrayList<>();

        while (iterador.hasNext()) {
            EntidadVideojuego entidad = iterador.next();
            if (entidad == jugadorPrincipal) continue;

            // FÓRMULA AABB
            boolean colisionX = jugadorPrincipal.getX() < entidad.getX() + entidad.getW() &&
                                jugadorPrincipal.getX() + jugadorPrincipal.getW() > entidad.getX();
            boolean colisionY = jugadorPrincipal.getY() < entidad.getY() + entidad.getH() &&
                                jugadorPrincipal.getY() + jugadorPrincipal.getH() > entidad.getY();

            if (colisionX && colisionY) {
                System.out.println("\n>>> ¡COLISIÓN DETECTADA con " + entidad.getNombre() + "! <<<");
                
                if (entidad.getTipo().equals("ENEMIGO")) {
                    jugadorPrincipal.setVida(jugadorPrincipal.getVida() - 1);
                    System.out.println(">>> El jugador recibe daño. Pierdes 1 vida.");
                    // Empuje de invulnerabilidad
                    jugadorPrincipal.desplazarEntidad("ARRIBA"); 
                } else if (entidad.getTipo().equals("PREMIO")) {
                    puntuacion += 100;
                    System.out.println(">>> ¡Premio recogido! +100 Puntos.");
                    entidadesAEliminar.add(entidad);
                }
            }
        }
        entidades.removeAll(entidadesAEliminar);
    }
}