package kabalera82.spaceinvaders.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Clase abstracta que representa una entidad genérica dentro del mundo del juego.
 * 
 * <p>Una entidad es cualquier objeto que tiene:
 * <ul>
 *   <li>Una posición en coordenadas (x, y).</li>
 *   <li>Un tamaño (ancho y alto).</li>
 *   <li>Un "mundo" o espacio en el que se mueve (con un ancho y alto máximos).</li>
 *   <li>Capacidad de dibujarse en un {@link GraphicsContext} (canvas de JavaFX).</li>
 * </ul>
 *
 * <p>Al ser abstracta, no puede instanciarse directamente: debe ser extendida por 
 * clases concretas como {@code Nave}, {@code Alien}, {@code Proyectil}, etc.
 */
public abstract class Entidad {
    
    /** Posición horizontal (eje X) en píxeles dentro del mundo. */
    protected double posicionHorizontal;
    
    /** Posición vertical (eje Y) en píxeles dentro del mundo. */
    protected double posicionVertical;
    
    /** Anchura de la entidad en píxeles. */
    protected final double ancho;
    
    /** Altura de la entidad en píxeles. */
    protected final double alto;
    
    /** Anchura total del mundo (límite horizontal). */
    protected final double mundoAncho;
    
    /** Altura total del mundo (límite vertical). */
    protected final double mundoAlto;

    /**
     * Constructor para inicializar una entidad.
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     * @param ancho anchura en píxeles de la entidad.
     * @param alto altura en píxeles de la entidad.
     * @param mundoAncho anchura total del mundo de juego.
     * @param mundoAlto altura total del mundo de juego.
     */
    protected Entidad(double x, double y, double ancho, double alto,
                      double mundoAncho, double mundoAlto) {
        // Guardamos la posición inicial (coordenadas en el canvas)
        this.posicionHorizontal = x;
        this.posicionVertical = y;
        
        // Dimensiones del objeto (ej. nave = 64x32 píxeles)
        this.ancho = ancho;
        this.alto = alto;
        
        // Dimensiones máximas del "mundo" donde vive la entidad
        this.mundoAncho = mundoAncho;
        this.mundoAlto = mundoAlto;
    }

    /**
     * Restringe (clamp) la posición de la entidad para que no se salga de los
     * límites del mundo.
     * 
     * <p>Ejemplo: si la nave intenta moverse más allá de la pantalla,
     * esta función la mantiene dentro.</p>
     */
    protected void clamp() {
        // Si la entidad está a la izquierda de la pantalla, la fijamos en X=0
        if (posicionHorizontal < 0) posicionHorizontal = 0;
        
        // Calculamos la coordenada máxima posible en X (derecha)
        double maxX = mundoAncho - ancho;
        if (posicionHorizontal > maxX) posicionHorizontal = maxX;
        
        // Si está por encima de Y=0, la fijamos en Y=0
        if (posicionVertical < 0) posicionVertical = 0;
        
        // Calculamos la coordenada máxima posible en Y (parte inferior)
        double maxY = mundoAlto - alto;
        if (posicionVertical > maxY) posicionVertical = maxY;
    }

    /**
     * Devuelve el rectángulo de colisión de la entidad.
     *
     * <p>Este rectángulo se usa para comprobar colisiones con otras entidades
     * (ej. bala contra alien).</p>
     *
     * @return objeto {@link Rectangle2D} que representa los límites de la entidad.
     */
    public Rectangle2D getBounds() {
        return new Rectangle2D(posicionHorizontal, posicionVertical, ancho, alto);
    }

    /**
     * Método abstracto que debe implementar cada entidad concreta para
     * dibujarse a sí misma.
     *
     * @param g el {@link GraphicsContext} del {@link javafx.scene.canvas.Canvas}
     *          donde se pintará la entidad.
     */
    public abstract void draw(GraphicsContext g);
}
