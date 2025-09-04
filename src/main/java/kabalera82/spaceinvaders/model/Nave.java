package kabalera82.spaceinvaders.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import kabalera82.spaceinvaders.interfaces.IMovimiento;

/**
 * Clase que representa la nave del jugador.
 *
 * <p>La nave es una {@link Entidad} que implementa {@link IMovimiento} y se 
 * controla mediante el teclado. Puede desplazarse en pasos fijos a izquierda 
 * o derecha dentro de los límites del mundo.</p>
 */
public class Nave extends Entidad implements IMovimiento {

    /** Imagen que representa la nave en pantalla. */
    private final Image img;

    /** Número de píxeles que la nave se mueve en cada paso. */
    private double pasoPx = 16;

    /**
     * Constructor de la nave del jugador.
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     * @param ancho anchura de la nave en píxeles.
     * @param alto altura de la nave en píxeles.
     * @param mundoAncho anchura total del mundo (límite horizontal).
     * @param mundoAlto altura total del mundo (límite vertical).
     * @param rutaRecurso ruta de la imagen de la nave en resources.
     */
    public Nave(double x, double y, double ancho, double alto,
                double mundoAncho, double mundoAlto, String rutaRecurso) {
        // Inicializa los atributos heredados de Entidad
        super(x, y, ancho, alto, mundoAncho, mundoAlto);

        // Carga la imagen de la nave desde resources
        img = new Image(getClass().getResourceAsStream(rutaRecurso));
    }

    /**
     * Mueve la nave un "paso" hacia la izquierda o derecha.
     *
     * <p>La dirección se pasa como parámetro:
     * <ul>
     *   <li>-1 = izquierda</li>
     *   <li>+1 = derecha</li>
     * </ul>
     *
     * @param dir dirección del movimiento (-1 izquierda, 1 derecha).
     */
    @Override
    public void moverPaso(int dir) {
        // Desplaza horizontalmente usando el paso definido
        posicionHorizontal += dir * pasoPx;

        // Evita que la nave salga de los límites del mundo
        clamp();
    }

    /**
     * Actualiza el estado de la nave.
     *
     * <p>En este caso no hace nada porque el movimiento se controla por pasos
     * (teclas), pero se deja preparado por si se añade lógica en el futuro.</p>
     *
     * @param dt tiempo transcurrido en segundos (no utilizado aquí).
     */
    @Override
    public void actualizar(double dt) {
        // Vacío porque la nave no se mueve automáticamente
    }

    /**
     * Dibuja la nave en el canvas.
     *
     * @param g el {@link GraphicsContext} donde se pintará la nave.
     */
    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(img, posicionHorizontal, posicionVertical, ancho, alto);
    }

    /**
     * Cambia el tamaño del paso de movimiento en píxeles.
     *
     * <p>Sirve para ajustar la velocidad de la nave (a mayor paso, 
     * más rápido se desplaza).</p>
     *
     * @param px cantidad de píxeles por paso.
     */
    public void setPasoPx(double px) {
        this.pasoPx = px;
    }
}
