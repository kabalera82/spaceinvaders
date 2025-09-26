package kabalera82.spaceinvaders.interfaces;

/**
 * Interfaz que define el contrato de movimiento para las entidades del juego.
 *
 * <p>Permite implementar tanto el control manual (movimientos discretos
 * activados por el jugador) como el control automático (movimientos
 * continuos calculados en cada ciclo de actualización).</p>
 *
 * <h2>Dos tipos de movimiento</h2>
 * <ul>
 *   <li>{@link #moverPaso(int)} → movimiento discreto, útil para acciones
 *       ligadas a entradas de teclado como mover la nave a izquierda o derecha.</li>
 *   <li>{@link #actualizar(double)} → movimiento continuo dependiente del
 *       tiempo, usado en elementos autónomos como aliens o proyectiles.</li>
 * </ul>
 *
 * <p>Gracias a esta interfaz, cualquier clase que represente una entidad del
 * juego puede implementar su propia lógica de movimiento y ser gestionada
 * de forma uniforme dentro del motor.</p>
 *
 * @author  Kabalera82
 * @version 1.0
 */
public interface IMovimiento {

    /**
     * Mueve la entidad un paso discreto en una dirección.
     *
     * <p>Se invoca habitualmente en respuesta a acciones del jugador
     * (por ejemplo, pulsar teclas de dirección).</p>
     *
     * @param dir dirección del movimiento:
     *            <ul>
     *              <li>{@code -1} → izquierda</li>
     *              <li>{@code +1} → derecha</li>
     *            </ul>
     */
    void moverPaso(int dir);

    /**
     * Actualiza la posición de la entidad en función del tiempo transcurrido.
     *
     * <p>Se usa en bucles de juego para animaciones o movimientos
     * automáticos. Cada implementación decide cómo aplicar el incremento.</p>
     *
     * @param dt tiempo en segundos desde la última actualización.
     */
    void actualizar(double dt);
}
