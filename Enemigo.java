/**
 * Entidad controlada por el juego que interactúa de forma autónoma con el jugador.
 * Implementa la Funcionalidad Avanzada 2: Comportamiento de NPC según distancia.
 */
public class Enemigo extends EntidadVideojuego {
    /** Referencia al jugador para calcular distancias */
    private Jugador objetivo;
    /** Máquina de estados simple del NPC (PATRULLANDO, PERSIGUIENDO, ATACANDO) */
    private String estadoNPC;

    /**
     * Constructor del Enemigo.
     * @param nombre   Nombre identificativo del fantasma/enemigo.
     * @param x        Coordenada X inicial de aparición.
     * @param y        Coordenada Y inicial de aparición.
     * @param objetivo El jugador al que debe observar o perseguir.
     */
    public Enemigo(String nombre, int x, int y, Jugador objetivo) {
        super(nombre, "ENEMIGO", x, y, 10, 10, 1, "fantasma_rojo.png");
        this.objetivo = objetivo;
        this.estadoNPC = "PATRULLANDO";
    }

    /**
     * Sobreescribe el método de EntidadVideojuego.
     * Calcula la distancia euclidiana hacia el jugador y cambia de estado o se mueve en consecuencia.
     */
    @Override
    public void actualizar() {
        if (objetivo == null) return;

        // Cálculo de distancia euclidiana entre centros simulados
        double distancia = Math.sqrt(Math.pow(this.x - objetivo.getX(), 2) + Math.pow(this.y - objetivo.getY(), 2));

        if (distancia < 20.0) {
            this.estadoNPC = "ATACANDO";
            System.out.println(" > " + nombre + " [NPC]: ¡Está muy cerca! Estado -> ATACANDO.");
        } else if (distancia < 60.0) {
            this.estadoNPC = "PERSIGUIENDO";
            System.out.println(" > " + nombre + " [NPC]: ¡Te veo! Estado -> PERSIGUIENDO.");
            
            // Lógica de persecución rudimentaria (moverse hacia el jugador)
            if (this.x < objetivo.getX()) this.x += 2;
            if (this.x > objetivo.getX()) this.x -= 2;
            if (this.y < objetivo.getY()) this.y += 2;
            if (this.y > objetivo.getY()) this.y -= 2;
        } else {
            this.estadoNPC = "PATRULLANDO";
            // Lógica de patrulla (movimiento errático en el eje X)
            this.x += (Math.random() > 0.5 ? 1 : -1) * 2;
            System.out.println(" > " + nombre + " [NPC]: Todo tranquilo. Estado -> PATRULLANDO.");
        }
    }
}