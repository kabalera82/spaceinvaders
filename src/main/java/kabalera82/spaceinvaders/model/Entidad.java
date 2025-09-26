package kabalera82.spaceinvaders.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Clase abstracta que representa una entidad genérica dentro del mundo del juego.
 *
 * <p>Define los atributos y comportamientos comunes de cualquier objeto con
 * posición y tamaño en el espacio del juego. Ejemplos de entidades son
 * {@code Nave}, {@code Alien} o {@code Proyectil}.</p>
 *
 * <h2>Características principales</h2>
 * <ul>
 *   <li>Posición (coordenadas X e Y) en píxeles.</li>
 *   <li>Tamaño definido por ancho y alto.</li>
 *   <li>Límites máximos del mundo (ancho y alto del área de juego).</li>
 *   <li>Métodos para ajustar la posición y obtener colisiones.</li>
 *   <li>Capacidad de dibujarse en un {@link GraphicsContext}.</li>
 * </ul>
 *
 * <p>Al ser abstracta, debe ser extendida por clases concretas que implementen
 * el método {@link #draw(GraphicsContext)}.</p>
 *
 * @author  Kabalera82
 * @version 1.0
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
     * Construye una nueva entidad con posición, tamaño y límites de mundo definidos.
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
        this.posicionHorizontal = x;
        this.posicionVertical = y;
        this.ancho = ancho;
        this.alto = alto;
        this.mundoAncho = mundoAncho;
        this.mundoAlto = mundoAlto;
    }

    /**
     * Restringe la posición de la entidad para que no sobrepase los
     * límites del mundo.
     *
     * <p>Se utiliza, por ejemplo, para evitar que la nave salga de
     * los bordes de la pantalla.</p>
     */
    protected void clamp() {
        if (posicionHorizontal < 0) {
            posicionHorizontal = 0;
        }
        double maxX = mundoAncho - ancho;
        if (posicionHorizontal > maxX) {
            posicionHorizontal = maxX;
        }
        if (posicionVertical < 0) {
            posicionVertical = 0;
        }
        double maxY = mundoAlto - alto;
        if (posicionVertical > maxY) {
            posicionVertical = maxY;
        }
    }

    /**
     * Devuelve el rectángulo de colisión de la entidad.
     *
     * <p>El rectángulo se utiliza para comprobar intersecciones con
     * otras entidades, como proyectiles o enemigos.</p>
     *
     * @return un {@link Rectangle2D} con los límites de la entidad.
     */
    public Rectangle2D getBounds() {
        return new Rectangle2D(posicionHorizontal, posicionVertical, ancho, alto);
    }

    /**
     * Dibuja la entidad en el contexto gráfico indicado.
     *
     * <p>Cada subclase concreta debe proporcionar su propia implementación
     * de este método para representar la entidad en pantalla.</p>
     *
     * @param g el {@link GraphicsContext} del {@link javafx.scene.canvas.Canvas}
     *          donde se pintará la entidad.
     */
    public abstract void draw(GraphicsContext g);
}
