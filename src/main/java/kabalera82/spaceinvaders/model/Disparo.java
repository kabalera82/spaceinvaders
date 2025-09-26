package kabalera82.spaceinvaders.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import kabalera82.spaceinvaders.interfaces.IMovimiento;

/**
 * Representa un proyectil disparado dentro del juego.
 *
 * <p>Un disparo es una {@link Entidad} que implementa la interfaz
 * {@link IMovimiento}. Su movimiento es vertical y controlado por
 * la velocidad definida al instanciarlo. Por defecto se usa
 * una velocidad negativa para que se desplace hacia arriba.</p>
 *
 * <h2>Características</h2>
 * <ul>
 *   <li>Se mueve automáticamente en el eje vertical.</li>
 *   <li>Tiene un color personalizable (blanco por defecto).</li>
 *   <li>Puede comprobar si ha salido fuera de la pantalla.</li>
 * </ul>
 *
 * @author  Kabalera82
 * @version 1.0
 * @see kabalera82.spaceinvaders.model.Entidad
 * @see kabalera82.spaceinvaders.interfaces.IMovimiento
 */
public class Disparo extends Entidad implements IMovimiento {

    /** Velocidad del proyectil en píxeles por segundo (negativa = hacia arriba). */
    private final double velocidadPxS;

    /** Color con el que se representa el proyectil. */
    private final Color color;

    /**
     * Construye un nuevo proyectil con posición, dimensiones, límites de mundo,
     * velocidad y color definidos.
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     * @param ancho anchura del proyectil en píxeles.
     * @param alto altura del proyectil en píxeles.
     * @param mundoAncho anchura total del mundo (límite horizontal).
     * @param mundoAlto altura total del mundo (límite vertical).
     * @param velocidadPxS velocidad en píxeles por segundo
     *                     (negativa para moverse hacia arriba).
     * @param color color del proyectil; si es {@code null}, se usa blanco.
     */
    public Disparo(double x, double y, double ancho, double alto,
                   double mundoAncho, double mundoAlto,
                   double velocidadPxS, Color color) {
        super(x, y, ancho, alto, mundoAncho, mundoAlto);
        this.velocidadPxS = velocidadPxS;
        this.color = color == null ? Color.WHITE : color;
    }

    /**
     * Actualiza la posición vertical del proyectil en función del tiempo transcurrido.
     *
     * @param dt tiempo en segundos desde la última actualización.
     */
    @Override
    public void actualizar(double dt) {
        posicionVertical += velocidadPxS * dt;
        // No se aplica clamp() para permitir que el disparo desaparezca
        // al salir del área de juego.
    }

    /**
     * Indica si el disparo ha salido fuera de los límites verticales del mundo.
     *
     * @return {@code true} si está por encima o por debajo de la pantalla;
     *         {@code false} en caso contrario.
     */
    public boolean fueraDePantalla() {
        return (posicionVertical + alto) < 0 || posicionVertical > mundoAlto;
    }

    /**
     * Dibuja el proyectil en el contexto gráfico indicado.
     *
     * @param g el {@link GraphicsContext} sobre el que se renderiza el proyectil.
     */
    @Override
    public void draw(GraphicsContext g) {
        g.setFill(color);
        g.fillRect(posicionHorizontal, posicionVertical, ancho, alto);
    }

    /**
     * Método de la interfaz {@link IMovimiento} no utilizado en esta clase.
     *
     * <p>Los disparos se desplazan automáticamente con {@link #actualizar(double)},
     * por lo que no requieren movimiento por pasos.</p>
     *
     * @param dir dirección del movimiento (sin efecto en esta implementación).
     */
    @Override
    public void moverPaso(int dir) {
        // No se usa en disparos.
    }
}
