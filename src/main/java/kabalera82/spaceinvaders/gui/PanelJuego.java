package kabalera82.spaceinvaders.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import kabalera82.spaceinvaders.model.Nave;   // ← usar el modelo Nave

public class PanelJuego {

    // ====== Config ======
    private static final int CASILLA = 32;
    private static final int FILAS = 32;
    private static final int COLUMNAS = 32;
    private static final int ANCHO = CASILLA * COLUMNAS;
    private static final int ALTO  = CASILLA * FILAS;

    // ====== Dibujo ======
    private final Canvas lienzo;
    private final GraphicsContext graficos;
    private final AnimationTimer bucleJuego;

    // ====== Nave (modelo) ======
    private Nave nave;
    private final double naveW = CASILLA * 2;
    private final double naveH = CASILLA;


    public PanelJuego() {
        this.lienzo = new Canvas(ANCHO, ALTO);
        this.graficos = lienzo.getGraphicsContext2D();
        init();

        this.bucleJuego = new AnimationTimer() {
            @Override public void handle(long ahora) {
                update(ahora);
                renderizar();
                
            }
        };
    }

    public Canvas getCanvas() { return lienzo; }
    public void start() { bucleJuego.start(); }
    public void stop()  { bucleJuego.stop();  }

    private void init() {
        // Instanciar la nave: la imagen se carga dentro de Nave
        nave = new Nave(
            ANCHO / 2.0 - naveW / 2.0,      // x centrada
            ALTO - naveH - CASILLA,         // y abajo con 1 casilla de margen
            naveW, naveH,
            ANCHO, ALTO,
            "/imagenes/nave.png"
        );
        nave.setPasoPx(CASILLA / 2.0);      // salto por pulsación

        // Teclado en el canvas (movimiento discreto)
        lienzo.setFocusTraversable(true);
        lienzo.requestFocus();
        lienzo.setOnKeyPressed(e -> {
            KeyCode c = e.getCode();
            if (c == KeyCode.LEFT || c == KeyCode.A)  nave.moverPaso(-1);
            if (c == KeyCode.RIGHT || c == KeyCode.D) nave.moverPaso(+1);
            // if (c == KeyCode.SPACE) disparar();
        });
    }

    private void update(long ahora) {
        // Lógica por frame si la necesitas más adelante
    }

    private void renderizar() {
        drawBackground();
        // drawGrid(); // si quieres la rejilla, reactiva tu método
        drawHeader("Tablero de juego");

        // Dibuja la nave desde su propio draw()
        if (nave != null) nave.draw(graficos);
    }

    private void drawBackground() {
        graficos.setFill(Color.BLACK);
        graficos.fillRect(0, 0, ANCHO, ALTO);
    }

    private void drawHeader(String texto) {
        graficos.setFill(Color.LIMEGREEN);
        graficos.setFont(Font.font("Consolas", 28));
        graficos.setTextAlign(TextAlignment.CENTER);
        graficos.fillText(texto, ANCHO / 2.0, 36);
    }
}
