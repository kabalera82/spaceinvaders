package kabalera82.spaceinvaders.controlador;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TeclasJuego {

    public static boolean derecha, izquierda, disparo;

    public void keyTyped(KeyEvent evento) {
        // no usado
    }

    public void keyPressed(KeyEvent evento) {
        KeyCode codigo = evento.getCode();
        if (codigo == KeyCode.RIGHT)  derecha = true;
        if (codigo == KeyCode.LEFT)   izquierda = true;
        if (codigo == KeyCode.SPACE)  disparo = true;
    }

    public void keyReleased(KeyEvent evento) {
        // vacío como lo tenías; si luego quieres soltar flags, lo vemos
    }
}
