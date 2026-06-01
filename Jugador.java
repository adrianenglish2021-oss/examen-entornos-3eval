/**
 * Representa al personaje principal controlado por el usuario.
 * Hereda de EntidadVideojuego e implementa la simulación de controles táctiles.
 */
public class Jugador extends EntidadVideojuego {

    /**
     * Constructor del jugador. 
     * Inicializa el tamaño base y otorga 3 vidas por defecto.
     * @param nombre Identificador del jugador (ej. "Pac-Man").
     * @param x      Coordenada X inicial de aparición.
     * @param y      Coordenada Y inicial de aparición.
     */
    public Jugador(String nombre, int x, int y) {
        super(nombre, "JUGADOR", x, y, 10, 10, 3, "pacman_abierto.png");
    }

    /**
     * Funcionalidad obligatoria: Simulación de input táctil de desplazamiento.
     * Modifica las coordenadas del jugador en base a una dirección dada y altera su sprite.
     * @param direccion Cadena de texto indicando el sentido ("ARRIBA", "ABAJO", "IZQUIERDA", "DERECHA").
     */
    public void desplazarEntidad(String direccion) {
        int velocidad = 5;
        switch (direccion.toUpperCase()) {
            case "ARRIBA": this.y -= velocidad; break;
            case "ABAJO": this.y += velocidad; break;
            case "IZQUIERDA": this.x -= velocidad; break;
            case "DERECHA": this.x += velocidad; break;
            default: System.out.println("Dirección no reconocida por el jugador."); return;
        }
        
        // Simulación de cambio de frame de animación al moverse
        this.imagenFrame = this.imagenFrame.equals("pacman_abierto.png") ? "pacman_cerrado.png" : "pacman_abierto.png";
    }

    /**
     * Funcionalidad obligatoria: Simulación de input táctil para acciones especiales.
     * Ejecuta una habilidad (en este caso, un "dash" o acelerón hacia adelante).
     */
    public void pulsarBotonAccion() {
        System.out.println("\n*** ¡" + nombre + " ha usado la habilidad Dash (Salto Rápido)! ***");
        this.x += 10; 
    }
}