package kabalera82.spaceinvaders.controlador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import kabalera82.spaceinvaders.assets.AlienSkin;
import kabalera82.spaceinvaders.assets.SoundAssets;
import kabalera82.spaceinvaders.gui.PanelJuego;
import kabalera82.spaceinvaders.model.Alien;
import kabalera82.spaceinvaders.model.Disparo;
import kabalera82.spaceinvaders.model.Nave;

/**
 * Controlador del juego: contiene TODA la lógica.
 * - Bucle principal (AnimationTimer) y cálculo de dt
 * - Input (teclado): mover nave, disparar
 * - Spawns: crear oleadas de aliens
 * - Actualización: mover aliens/disparos
 * - Colisiones y reglas (vidas, puntos, game over)
 * - Pinta a través de la Vista (PanelJuego.render(...))
 */
public class GameController {

    // ====== Config del “mundo” (igual que antes) ======
    private static final int CASILLA  = 32;
    private static final int FILAS    = 32;
    private static final int COLUMNAS = 32;
    private static final int ANCHO    = CASILLA * COLUMNAS;
    private static final int ALTO     = CASILLA * FILAS;

    // ====== Timer ======
    private final AnimationTimer loop;
    private long lastNs = 0;

    // ====== Vista ======
    private final PanelJuego view;

    // ====== Modelo / Estado ======
    private Nave nave;
    private final double naveW = CASILLA * 2;
    private final double naveH = CASILLA;

    private final List<Alien> aliens = new ArrayList<>();
    private final double alienW = CASILLA, alienH = CASILLA;

    private final int ALIENS_FILAS = 5, ALIENS_COLS = 10;
    private final double ALIENS_MARGEN_X = CASILLA;
    private final double ALIENS_MARGEN_SUP = CASILLA * 2;
    private final double ALIENS_SEP_X = CASILLA * 2;
    private final double ALIENS_SEP_Y = CASILLA * 1.5;

    private final List<Disparo> disparos = new ArrayList<>();
    private final double disparoW = CASILLA * 0.2;
    private final double disparoH = CASILLA * 0.8;
    private final double disparoVel = -480; // px/s hacia arriba
    private long lastShotNs = 0;
    private final long shotCooldownNs = 200_000_000; // 300 ms

    private int vidas = 3;
    private int puntos = 0;
    private int nivel = 1;
    private boolean gameOver = false;

    public GameController(PanelJuego view) {
        this.view = view;
        initEstado();

        this.loop = new AnimationTimer() {
            @Override public void handle(long ahora) {
                if (gameOver) {
                    view.render(nave, aliens, disparos, vidas, puntos, true);
                    return;
                }
                if (lastNs == 0) { lastNs = ahora; return; }
                double dt = (ahora - lastNs) / 1_000_000_000.0;
                lastNs = ahora;

                update(dt);
                view.render(nave, aliens, disparos, vidas, puntos, false);
            }
        };
    }

    // ====== Ciclo de vida ======
    public void start() { loop.start(); }
    public void stop()  { loop.stop();  }

    // ====== Input ======
    /** Enlaza el input del teclado de cualquier Node (Canvas, Scene, StackPane...) */
    public void bindInput(Node node) {
        node.setOnKeyPressed(this::onKeyPressed);
        node.setOnKeyReleased(this::onKeyReleased);
        node.setFocusTraversable(true);
        node.requestFocus();
    }

    private void onKeyPressed(KeyEvent e) {
        KeyCode c = e.getCode();
        if (c == KeyCode.LEFT || c == KeyCode.A)  nave.moverPaso(-1);
        if (c == KeyCode.RIGHT || c == KeyCode.D) nave.moverPaso(+1);
        if (c == KeyCode.SPACE) disparar();
    }

    private void onKeyReleased(KeyEvent e) {
        // Si quisieras gestionar “hold” continuo, puedes resetear flags aquí.
    }

    // ====== Estado inicial ======
    private void initEstado() {
        nave = new Nave(
            ANCHO / 2.0 - naveW / 2.0,
            ALTO - naveH - CASILLA,
            naveW, naveH, ANCHO, ALTO,
            "/imagenes/nave.png"
        );
        nave.setPasoPx(CASILLA / 2.0);
        crearOleadaAliens();
        disparos.clear();
        vidas = 3;
        puntos = 0;
        gameOver = false;
        lastNs = 0;
        lastShotNs = 0;
    }

    private void crearOleadaAliens() {
        aliens.clear();
        AlienSkin[] skins = AlienSkin.values(); // GREEN, CYAN, MAGENTA, YELLOW

        double startX = ALIENS_MARGEN_X;
        double startY = ALIENS_MARGEN_SUP;

        // Aumenta filas y columnas con el nivel
        int filas = ALIENS_FILAS + (nivel - 1);   // más filas cada nivel
        int cols  = ALIENS_COLS  + (nivel - 1);   // más columnas cada nivel

        for (int fila = 0; fila < filas; fila++) {
            AlienSkin skinFila = skins[fila % skins.length]; // cada fila un color
            for (int col = 0; col < cols; col++) {
                double x = startX + col * ALIENS_SEP_X;
                double y = startY + fila * ALIENS_SEP_Y;
                Alien alien = new Alien(x, y, alienW, alienH, ANCHO, ALTO, skinFila);

                // cuanto mayor es el nivel, más rápido animan
                double frameDuration = Math.max(0.05, 0.15 - (nivel - 1) * 0.01);
                alien.setFrameDuration(frameDuration);

                aliens.add(alien);
            }
        }
    }

    private void disparar() {
        long ahora = System.nanoTime();
        if (ahora - lastShotNs < shotCooldownNs) return;
        lastShotNs = ahora;

        Rectangle2D nb = nave.getBounds();
        double x = nb.getMinX() + nb.getWidth() / 2.0 - (disparoW / 2.0);
        double y = nb.getMinY() - disparoH;
        disparos.add(new Disparo(x, y, disparoW, disparoH, ANCHO, ALTO, disparoVel, Color.YELLOW));

        SoundAssets.playDisparo();
    }

    // ====== Update/Colisiones/Reglas ======
    private void update(double dt) {
        // Aliens
        for (Alien a : aliens) {
            a.actualizar(dt);
            if (colision(a.getBounds(), nave.getBounds())
             || a.getBounds().getMaxY() >= nave.getBounds().getMinY()) {
                perderVidaYReiniciar();
                SoundAssets.playNaveHit();
                return;
            }
        }

        // Disparos
        Iterator<Disparo> it = disparos.iterator();
        while (it.hasNext()) {
            Disparo d = it.next();
            d.actualizar(dt);
            if (d.fueraDePantalla()) it.remove();
        }

        // Bala vs Alien
        List<Alien> killAliens = new ArrayList<>();
        List<Disparo> killShots = new ArrayList<>();

        for (Disparo d : disparos) {
            Rectangle2D bd = d.getBounds();
            for (Alien a : aliens) {
                if (colision(bd, a.getBounds())) {
                    SoundAssets.playAlienExplosion();
                    killAliens.add(a);
                    killShots.add(d);
                    puntos += 10;
                    break;
                }
            }
        }
        if (!killAliens.isEmpty()) aliens.removeAll(killAliens);
        if (!killShots.isEmpty())  disparos.removeAll(killShots);

        // Siguiente oleada
        if (aliens.isEmpty()) {
            nivel++;
            crearOleadaAliens();
            // Puedes aumentar dificultad aquí (p.ej., velocidad de Alien)
        }
    }

    private boolean colision(Rectangle2D a, Rectangle2D b) { return a.intersects(b); }

    private void perderVidaYReiniciar() {
        vidas--;
        if (vidas <= 0) {
            gameOver = true;
            return;
        }
        // Recolocar nave y reiniciar oleada
        nave = new Nave(
            ANCHO / 2.0 - naveW / 2.0,
            ALTO - naveH - CASILLA,
            naveW, naveH, ANCHO, ALTO, "/imagenes/nave.png"
        );
        nave.setPasoPx(CASILLA / 2.0);
        crearOleadaAliens();
        disparos.clear();
        lastShotNs = 0;
    }
}
