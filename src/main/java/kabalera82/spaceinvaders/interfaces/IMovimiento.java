package kabalera82.spaceinvaders.interfaces;

/**
 * Interfaz que define el contrato de movimiento para las entidades del juego.
 *
 * <p>Separa la lógica de movimiento en dos niveles:</p>
 * <ul>
 *   <li>{@link #moverPaso(int)} → movimiento discreto, en pasos (ej. la nave al pulsar teclas).</li>
 *   <li>{@link #actualizar(double)} → movimiento continuo en función del tiempo (ej. aliens que se mueven solos).</li>
 * </ul>
 *
 * <p>De esta forma, tanto entidades controladas por el jugador como las que
 * se mueven automáticamente comparten la misma interfaz.</p>
 */
public interface IMovimiento {

    /**
     * Mueve la entidad un paso en una dirección concreta.
     *
     * @param dir dirección del movimiento:
     *            <ul>
     *              <li>-1 → izquierda</li>
     *              <li>+1 → derecha</li>
     *            </ul>
     */
    void moverPaso(int dir);

    /**
     * Actualiza la posición de la entidad en función del tiempo transcurrido.
     *
     * <p>Se suele usar para movimientos automáticos, animaciones o física.</p>
     *
     * @param dt tiempo en segundos desde la última actualización.
     */
    void actualizar(double dt);
}
