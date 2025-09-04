package kabalera82.spaceinvaders.controlador;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Clase que gestiona el estado de las teclas utilizadas en el juego.
 *
 * <p>Permite saber si el jugador está pulsando izquierda, derecha o disparo.
 * Usa <code>boolean</code> estáticos para que cualquier parte del juego
 * pueda consultar el estado actual de las teclas.</p>
 *
 * <p>Se utiliza junto con los eventos de teclado de JavaFX:
 * {@link #keyPressed(KeyEvent)}, {@link #keyReleased(KeyEvent)}.</p>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>
 * escena.setOnKeyPressed(teclas::keyPressed);
 * escena.setOnKeyReleased(teclas::keyReleased);
 * if (TeclasJuego.izquierda) nave.moverPaso(-1);
 * </pre>
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
     * <p>En este juego no se utiliza, pero se incluye por completitud.</p>
     *
     * @param evento evento de tecla tipeada.
     */
    public void keyTyped(KeyEvent evento) {
        // No usado en este juego (solo interesa PRESSED y RELEASED)
    }

    /**
     * Maneja cuando una tecla se pulsa.
     * 
     * <p>Activa el flag correspondiente a la tecla pulsada:</p>
     * <ul>
     *   <li>→ (RIGHT) → activa {@link #derecha}.</li>
     *   <li>← (LEFT) → activa {@link #izquierda}.</li>
     *   <li>SPACE → activa {@link #disparo}.</li>
     * </ul>
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
     * Maneja cuando una tecla se suelta.
     *
     * <p>Actualmente está vacío, pero normalmente se usaría para poner en
     * <code>false</code> los flags al soltar cada tecla. Ejemplo:</p>
     * <pre>
     * if (codigo == KeyCode.RIGHT) derecha = false;
     * </pre>
     *
     * @param evento evento de tecla liberada.
     */
    public void keyReleased(KeyEvent evento) {
        // Vacío: se podría implementar para resetear flags al soltar teclas
    }
}
