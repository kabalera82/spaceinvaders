package kabalera82.spaceinvaders.gui;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import kabalera82.spaceinvaders.model.Alien;
import kabalera82.spaceinvaders.model.Disparo;
import kabalera82.spaceinvaders.model.Nave;

/**
 * Vista del juego. Solo dibuja (no contiene lógica).
 */
public class PanelJuego {

    private static final int CASILLA  = 32;
    private static final int FILAS    = 32;
    private static final int COLUMNAS = 32;
    private static final int ANCHO    = CASILLA * COLUMNAS;
    private static final int ALTO     = CASILLA * FILAS;

    private final Canvas lienzo;
    private final GraphicsContext g;

    public PanelJuego() {
        this.lienzo = new Canvas(ANCHO, ALTO);
        this.g = lienzo.getGraphicsContext2D();
    }

    public Canvas getCanvas() { return lienzo; }

    /** El Controlador llama aquí cada frame para pintar el estado recibido. */
    public void render(Nave nave, List<Alien> aliens, List<Disparo> disparos,
                       int vidas, int puntos, boolean gameOver) {
        // Fondo
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, ANCHO, ALTO);

        // Encabezado
        g.setFill(Color.LIMEGREEN);
        g.setFont(Font.font("Consolas", 28));
        g.setTextAlign(TextAlignment.CENTER);
        g.fillText("Tablero de juego", ANCHO / 2.0, 36);

        // HUD
        g.setFill(Color.WHITE);
        g.setFont(Font.font("Consolas", 18));
        g.setTextAlign(TextAlignment.LEFT);
        g.fillText("Vidas: " + vidas, 12, 24);
        g.fillText("Puntos: " + puntos, 12, 44);
        g.fillText("Aliens: " + aliens.size(), 12, 64);

        // Entidades
        if (nave != null) nave.draw(g);
        for (Alien a : aliens) a.draw(g);
        for (Disparo d : disparos) d.draw(g);

        if (gameOver) {
            g.setFill(Color.RED);
            g.setFont(Font.font("Consolas", 48));
            g.setTextAlign(TextAlignment.CENTER);
            g.fillText("GAME OVER", ANCHO / 2.0, ALTO / 2.0);
        }
    }
}
