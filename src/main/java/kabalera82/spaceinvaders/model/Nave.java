package kabalera82.spaceinvaders.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import kabalera82.spaceinvaders.interfaces.IMovimiento;

/**
 * Representa la nave controlada por el jugador.
 *
 * <p>Es una subclase de {@link Entidad} que implementa la interfaz
 * {@link IMovimiento}. Su desplazamiento se realiza en pasos fijos hacia
 * izquierda o derecha mediante la interacción con el teclado, siempre dentro
 * de los límites del mundo.</p>
 *
 * <h2>Responsabilidades</h2>
 * <ul>
 *   <li>Almacenar y renderizar la imagen de la nave.</li>
 *   <li>Responder a movimientos discretos en el eje horizontal.</li>
 *   <li>Garantizar que la nave no sobrepase los límites del mundo.</li>
 * </ul>
 *
 * @author  Kabalera82
 * @version 1.0
 * @see kabalera82.spaceinvaders.model.Entidad
 * @see kabalera82.spaceinvaders.interfaces.IMovimiento
 */
public class Nave extends Entidad implements IMovimiento {

    /** Imagen de la nave renderizada en pantalla. */
    private final Image img;

    /** Cantidad de píxeles desplazados en cada paso de movimiento. */
    private double pasoPx = 16;

    /**
     * Construye una nueva nave del jugador.
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     * @param ancho anchura de la nave en píxeles.
     * @param alto altura de la nave en píxeles.
     * @param mundoAncho anchura total del mundo (límite horizontal).
     * @param mundoAlto altura total del mundo (límite vertical).
     * @param rutaRecurso ruta de la imagen de la nave en {@code resources}.
     */
    public Nave(double x, double y, double ancho, double alto,
                double mundoAncho, double mundoAlto, String rutaRecurso) {
        super(x, y, ancho, alto, mundoAncho, mundoAlto);
        img = new Image(getClass().getResourceAsStream(rutaRecurso));
    }

    /**
     * Desplaza la nave un paso en la dirección indicada.
     *
     * <p>Direcciones válidas:</p>
     * <ul>
     *   <li>-1 → izquierda</li>
     *   <li>+1 → derecha</li>
     * </ul>
     *
     * @param dir dirección del movimiento (-1 izquierda, 1 derecha).
     */
    @Override
    public void moverPaso(int dir) {
        posicionHorizontal += dir * pasoPx;
        clamp();
    }

    /**
     * Actualiza el estado de la nave.
     *
     * <p>Actualmente no implementa lógica adicional, ya que la nave
     * se controla únicamente mediante pasos discretos.</p>
     *
     * @param dt tiempo transcurrido en segundos (no utilizado).
     */
    @Override
    public void actualizar(double dt) {
        // Intencionalmente vacío
    }

    /**
     * Renderiza la nave en el contexto gráfico indicado.
     *
     * @param g el {@link GraphicsContext} sobre el que se dibuja la nave.
     */
    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(img, posicionHorizontal, posicionVertical, ancho, alto);
    }

    /**
     * Define el número de píxeles desplazados por paso de movimiento.
     *
     * @param px cantidad de píxeles por paso.
     */
    public void setPasoPx(double px) {
        this.pasoPx = px;
    }
}
