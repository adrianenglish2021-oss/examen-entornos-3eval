/**
 * Clase base abstracta que representa cualquier elemento visual dentro del juego.
 * Contiene las propiedades físicas (coordenadas, dimensiones), 
 * el estado (vida) y los metadatos visuales (imagen/frame).
 */
public class EntidadVideojuego {
    /** Posición en el eje X */
    protected int x;
    /** Posición en el eje Y */
    protected int y;
    /** Ancho de la caja de colisión */
    protected int w;
    /** Alto de la caja de colisión */
    protected int h;
    /** Tipo de entidad (ej. JUGADOR, ENEMIGO, PREMIO) */
    protected String tipo;
    /** Nombre identificativo de la entidad */
    protected String nombre;
    /** Puntos de salud o energía restantes */
    protected int vida;
    /** Nombre del recurso gráfico actual para la simulación visual */
    protected String imagenFrame; 

    /**
     * Constructor principal de la entidad.
     * @param nombre      Nombre de la entidad.
     * @param tipo        Clasificación de la entidad.
     * @param x           Coordenada X inicial.
     * @param y           Coordenada Y inicial.
     * @param w           Ancho lógico de la entidad.
     * @param h           Alto lógico de la entidad.
     * @param vida        Puntos de vida iniciales.
     * @param imagenFrame Archivo o nombre del frame de animación.
     */
    public EntidadVideojuego(String nombre, String tipo, int x, int y, int w, int h, int vida, String imagenFrame) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.vida = vida;
        this.imagenFrame = imagenFrame;
    }

    /**
     * Método virtual que simula la lógica de comportamiento en cada ciclo del juego.
     * Sobreescrito por clases hijas activas (como Enemigo).
     */
    public void actualizar() {
        // Por defecto, las entidades estáticas (como premios) no hacen nada activo
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getW() { return w; }
    public int getH() { return h; }
    public String getTipo() { return tipo; }
    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }

    /**
     * Representación en texto del estado de la entidad.
     * @return String con los datos principales de la entidad formateados.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s | Pos: (%d,%d) | Vida: %d | Sprite: %s", tipo, nombre, x, y, vida, imagenFrame);
    }
}