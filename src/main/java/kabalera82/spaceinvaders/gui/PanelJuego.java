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
 * Vista principal del juego encargada exclusivamente del renderizado.
 *
 * <p>Esta clase sigue el patrón MVC: se limita a dibujar en pantalla el
 * estado actual de las entidades y la interfaz, sin contener lógica de
 * control ni de modelo.</p>
 *
 * <h2>Responsabilidades</h2>
 * <ul>
 *   <li>Gestionar un {@link Canvas} y su {@link GraphicsContext}.</li>
 *   <li>Dibujar el fondo, encabezado y HUD.</li>
 *   <li>Renderizar las entidades del juego ({@link Nave}, {@link Alien},
 *       {@link Disparo}).</li>
 *   <li>Mostrar mensajes especiales como <b>GAME OVER</b>.</li>
 * </ul>
 *
 * @author  Kabalera82
 * @version 1.0
 */
public class PanelJuego {

    // === Constantes de configuración del tablero ===
    private static final int CASILLA  = 32;
    private static final int FILAS    = 32;
    private static final int COLUMNAS = 32;
    private static final int ANCHO    = CASILLA * COLUMNAS;
    private static final int ALTO     = CASILLA * FILAS;

    /** Lienzo de dibujo del juego. */
    private final Canvas lienzo;

    /** Contexto gráfico asociado al lienzo. */
    private final GraphicsContext g;

    /**
     * Crea un nuevo panel de juego inicializando el lienzo y su
     * contexto gráfico.
     */
    public PanelJuego() {
        this.lienzo = new Canvas(ANCHO, ALTO);
        this.g = lienzo.getGraphicsContext2D();
    }

    /**
     * Devuelve el lienzo gráfico sobre el que se dibuja el juego.
     *
     * @return el objeto {@link Canvas}.
     */
    public Canvas getCanvas() {
        return lienzo;
    }

    /**
     * Renderiza el estado completo del juego en el lienzo.
     *
     * <p>Este método es invocado en cada frame por el controlador, recibiendo
     * las entidades y valores de estado que deben mostrarse.</p>
     *
     * @param nave     la {@link Nave} del jugador (puede ser {@code null}).
     * @param aliens   lista de {@link Alien} activos en pantalla.
     * @param disparos lista de {@link Disparo} disparados actualmente.
     * @param vidas    número de vidas restantes del jugador.
     * @param puntos   puntuación acumulada.
     * @param gameOver indica si el juego ha terminado.
     */
    public void render(Nave nave, List<Alien> aliens, List<Disparo> disparos,
                       int vidas, int puntos, boolean gameOver) {
        // Fondo del tablero
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, ANCHO, ALTO);

        // Encabezado
        g.setFill(Color.LIMEGREEN);
        g.setFont(Font.font("Consolas", 28));
        g.setTextAlign(TextAlignment.CENTER);
        g.fillText("Tablero de juego", ANCHO / 2.0, 36);

        // HUD con información básica
        g.setFill(Color.WHITE);
        g.setFont(Font.font("Consolas", 18));
        g.setTextAlign(TextAlignment.LEFT);
        g.fillText("Vidas: " + vidas, 12, 24);
        g.fillText("Puntos: " + puntos, 12, 44);
        g.fillText("Aliens: " + aliens.size(), 12, 64);

        // Dibujar entidades
        if (nave != null) nave.draw(g);
        for (Alien a : aliens) a.draw(g);
        for (Disparo d : disparos) d.draw(g);

        // Mensaje de fin de juego
        if (gameOver) {
            g.setFill(Color.RED);
            g.setFont(Font.font("Consolas", 48));
            g.setTextAlign(TextAlignment.CENTER);
            g.fillText("GAME OVER", ANCHO / 2.0, ALTO / 2.0);
        }
    }
}
