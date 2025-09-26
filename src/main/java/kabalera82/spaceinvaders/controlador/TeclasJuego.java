package kabalera82.spaceinvaders.controlador;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Gestor de estado de teclas utilizadas en el juego.
 *
 * <p>Permite conocer en cualquier momento si el jugador mantiene pulsadas
 * las teclas de dirección o disparo. Utiliza campos estáticos para que
 * puedan consultarse desde cualquier parte del código.</p>
 *
 * <h2>Teclas controladas</h2>
 * <ul>
 *   <li><b>Izquierda</b> → {@link #izquierda}</li>
 *   <li><b>Derecha</b> → {@link #derecha}</li>
 *   <li><b>Disparo</b> (espacio) → {@link #disparo}</li>
 * </ul>
 *
 * <h2>Uso típico</h2>
 * <pre>{@code
 * escena.setOnKeyPressed(teclas::keyPressed);
 * escena.setOnKeyReleased(teclas::keyReleased);
 * if (TeclasJuego.izquierda) nave.moverPaso(-1);
 * }</pre>
 *
 * @author  Kabalera82
 * @version 1.0
 */
public class TeclasJuego {

    /** Indica si la tecla de mover a la derecha está pulsada. */
    public static boolean derecha;

    /** Indica si la tecla de mover a la izquierda está pulsada. */
    public static boolean izquierda;

    /** Indica si la tecla de disparo (barra espaciadora) está pulsada. */
    public static boolean disparo;

    /**
     * Maneja eventos de tipo "key typed".
     *
     * <p>No se utiliza en este juego, pero se mantiene por completitud.</p>
     *
     * @param evento evento de tecla tipeada.
     */
    public void keyTyped(KeyEvent evento) {
        // No utilizado
    }

    /**
     * Maneja el evento cuando una tecla es presionada.
     *
     * <p>Establece en {@code true} el flag de la tecla correspondiente.</p>
     *
     * @param evento evento de tecla pulsada.
     */
    public void keyPressed(KeyEvent evento) {
        KeyCode codigo = evento.getCode();

        if (codigo == KeyCode.RIGHT)  derecha = true;
        if (codigo == KeyCode.LEFT)   izquierda = true;
        if (codigo == KeyCode.SPACE)  disparo = true;
    }

    /**
     * Maneja el evento cuando una tecla es liberada.
     *
     * <p>Se recomienda implementar la lógica para restablecer los flags a
     * {@code false} al soltar cada tecla:</p>
     * <pre>{@code
     * if (codigo == KeyCode.RIGHT) derecha = false;
     * }</pre>
     *
     * @param evento evento de tecla liberada.
     */
    public void keyReleased(KeyEvent evento) {
        // Por implementar si se desea gestionar la liberación de teclas
    }
}
