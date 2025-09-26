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
 * Controlador principal del juego Space Invaders.
 *
 * <p>Se encarga de gestionar toda la lógica del juego, siguiendo el patrón MVC:</p>
 * <ul>
 *   <li><b>Modelo:</b> gestiona el estado de {@link Nave}, {@link Alien} y {@link Disparo}.</li>
 *   <li><b>Vista:</b> se comunica con {@link PanelJuego} para renderizar.</li>
 *   <li><b>Controlador:</b> procesa la entrada de teclado y aplica las reglas del juego.</li>
 * </ul>
 *
 * <h2>Responsabilidades</h2>
 * <ul>
 *   <li>Gestionar el bucle principal mediante {@link AnimationTimer}.</li>
 *   <li>Recoger y procesar las entradas de teclado (mover nave, disparar).</li>
 *   <li>Crear oleadas de enemigos.</li>
 *   <li>Actualizar posiciones de entidades y detectar colisiones.</li>
 *   <li>Aplicar reglas de vidas, puntuación, niveles y condiciones de fin de juego.</li>
 *   <li>Delegar el dibujado a {@link PanelJuego}.</li>
 * </ul>
 *
 * @author  Kabalera82
 * @version 1.0
 */
public class GameController {

    // ====== Configuración del mundo ======
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
    private final long shotCooldownNs = 200_000_000; // 200 ms

    private int vidas = 3;
    private int puntos = 0;
    private int nivel = 1;
    private boolean gameOver = false;

    /**
     * Construye un controlador asociado a la vista especificada.
     *
     * @param view panel de juego que se usará para renderizar.
     */
    public GameController(PanelJuego view) {
        this.view = view;
        initEstado();

        // Bucle de animación principal
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

    /** Inicia el bucle de animación del juego. */
    public void start() { loop.start(); }

    /** Detiene el bucle de animación del juego. */
    public void stop()  { loop.stop();  }

    // ====== Input ======

    /**
     * Enlaza los eventos de teclado al nodo indicado.
     *
     * @param node nodo sobre el que escuchar eventos (Canvas, Scene, etc.).
     */
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
        // Punto de extensión: podría usarse para gestionar inputs continuos
    }

    // ====== Estado inicial ======

    /** Inicializa o reinicia el estado del juego. */
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

    /** Genera una nueva oleada de aliens en función del nivel actual. */
    private void crearOleadaAliens() {
        aliens.clear();
        AlienSkin[] skins = AlienSkin.values();

        double startX = ALIENS_MARGEN_X;
        double startY = ALIENS_MARGEN_SUP;

        int filas = ALIENS_FILAS + (nivel - 1);
        int cols  = ALIENS_COLS  + (nivel - 1);

        for (int fila = 0; fila < filas; fila++) {
            AlienSkin skinFila = skins[fila % skins.length];
            for (int col = 0; col < cols; col++) {
                double x = startX + col * ALIENS_SEP_X;
                double y = startY + fila * ALIENS_SEP_Y;
                Alien alien = new Alien(x, y, alienW, alienH, ANCHO, ALTO, skinFila);

                double frameDuration = Math.max(0.05, 0.15 - (nivel - 1) * 0.01);
                alien.setFrameDuration(frameDuration);

                aliens.add(alien);
            }
        }
    }

    /** Dispara un proyectil desde la nave si el cooldown lo permite. */
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

    // ====== Update / Colisiones / Reglas ======

    /**
     * Actualiza el estado del juego.
     *
     * @param dt tiempo en segundos desde la última actualización.
     */
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

        // Nueva oleada
        if (aliens.isEmpty()) {
            nivel++;
            crearOleadaAliens();
            // Punto de extensión: aumentar dificultad progresiva
        }
    }

    private boolean colision(Rectangle2D a, Rectangle2D b) {
        return a.intersects(b);
    }

    /** Resta una vida y reinicia el estado si aún quedan intentos. */
    private void perderVidaYReiniciar() {
        vidas--;
        if (vidas <= 0) {
            gameOver = true;
            return;
        }
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
