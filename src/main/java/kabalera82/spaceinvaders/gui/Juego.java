package kabalera82.spaceinvaders.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Juego {

    // Atributos ==========================================================
    private static final int CASILLA = 32;      // Tamaño de cada casilla en píxeles
    private static final int FILAS = 32;        // Número de filas en el tablero
    private static final int COLUMNAS = 32;     // Número de columnas en el tablero

    private static final int ANCHO = CASILLA * COLUMNAS;
    private static final int ALTO  = CASILLA * FILAS;

    // -----------------------------------------------------------
    private final Canvas lienzo;                // Lienzo para dibujar el juego
    private final GraphicsContext graficos;     // Contexto gráfico para renderizar en el lienzo
    private final AnimationTimer bucleJuego;

    private long frame = 0;

    // -----------------------------------------------------------

    // Constructor ==========================================================
    public Juego() {
        this.lienzo = new Canvas(ANCHO, ALTO);
        this.graficos = lienzo.getGraphicsContext2D();

        init(); // ← Hook de inicialización

        this.bucleJuego = new AnimationTimer() {
            @Override public void handle(long ahora) {
                update(ahora);   // ← Hook de lógica por frame
                renderizar();    // ← Dibujo
                frame++;
            }
        };
    }

    public Canvas getCanvas() { return lienzo; }

    public void start() { bucleJuego.start(); }
    public void stop()  { bucleJuego.stop();  }

    // ===================== Hooks ======================
    private void init() {
        // Pon aquí tus prints de arranque si quieres
        // System.out.println("[Juego] init()");
    }

    private void update(long ahora) {
        // Lógica por frame (de momento vacía)
        // if (frame % 60 == 0) System.out.println("[Juego] frame=" + frame);
    }

    // ===================== Render =====================
    private void renderizar() {
        drawBackground();
        drawGrid();
        drawHeader("Tablero de juego");
    }

    private void drawBackground() {
        graficos.setFill(Color.BLACK);
        graficos.fillRect(0, 0, ANCHO, ALTO);
    }

    private void drawGrid() {
        graficos.setStroke(Color.DARKGRAY);
        graficos.setLineWidth(1);
        for (int i = 0; i <= COLUMNAS; i++) {
            int x = i * CASILLA;
            graficos.strokeLine(x, 0, x, ALTO);
        }
        for (int j = 0; j <= FILAS; j++) {
            int y = j * CASILLA;
            graficos.strokeLine(0, y, ANCHO, y);
        }
    }

    private void drawHeader(String texto) {
        graficos.setFill(Color.LIMEGREEN);
        graficos.setFont(Font.font("Consolas", 28));
        graficos.setTextAlign(TextAlignment.CENTER);
        graficos.fillText(texto, ANCHO / 2.0, 36); // texto arriba centrado
    }
}
