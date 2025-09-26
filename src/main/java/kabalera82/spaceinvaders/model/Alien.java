package kabalera82.spaceinvaders.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import kabalera82.spaceinvaders.assets.AlienSkin;
import kabalera82.spaceinvaders.assets.Assets;
import kabalera82.spaceinvaders.interfaces.IMovimiento;

/**
 * Representa a un alien enemigo dentro del juego Space Invaders.
 *
 * <p>Un alien es una {@link Entidad} que implementa {@link IMovimiento}. Su
 * comportamiento combina movimiento horizontal clásico con rebote en los bordes,
 * descenso progresivo y animación mediante fotogramas.</p>
 *
 * <h2>Características</h2>
 * <ul>
 *   <li>Animación configurable por frames.</li>
 *   <li>Soporte de variantes de aspecto mediante {@link AlienSkin}.</li>
 *   <li>Movimiento horizontal con cambio de dirección al llegar a los bordes.</li>
 *   <li>Descenso automático tras cada rebote en los límites.</li>
 * </ul>
 *
 * @author  Kabalera82
 * @version 1.0
 * @see kabalera82.spaceinvaders.model.Entidad
 * @see kabalera82.spaceinvaders.interfaces.IMovimiento
 * @see kabalera82.spaceinvaders.assets.AlienSkin
 */
public class Alien extends Entidad implements IMovimiento {

    // === Animación ===

    /** Conjunto de frames del alien (cargados desde {@link Assets}). */
    private final Image[] frames;

    /** Índice del frame actual en reproducción. */
    private int frameIndex = 0;

    /** Acumulador de tiempo transcurrido desde el último cambio de frame. */
    private double frameTimer = 0;

    /** Duración de cada frame en segundos (velocidad de animación). */
    private double frameDuration = 0.22;

    // === Movimiento clásico estilo Space Invaders ===

    /** Velocidad horizontal en píxeles por segundo. */
    private final double vel = 60;

    /** Dirección del movimiento: {@code 1} derecha, {@code -1} izquierda. */
    private int dir = 1;

    /** Cantidad de píxeles que desciende tras cada rebote. */
    private final double descenso = 16;

    /**
     * Construye un alien con un solo sprite (sin animación).
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     * @param ancho anchura del alien en píxeles.
     * @param alto altura del alien en píxeles.
     * @param mundoAncho anchura total del mundo.
     * @param mundoAlto altura total del mundo.
     * @param rutaRecurso ruta del recurso gráfico en {@code resources}.
     */
    public Alien(double x, double y, double ancho, double alto,
                 double mundoAncho, double mundoAlto, String rutaRecurso) {
        super(x, y, ancho, alto, mundoAncho, mundoAlto);
        this.frames = new Image[]{ Assets.get(rutaRecurso) };
    }

    /**
     * Construye un alien con animación y aspecto definido por un {@link AlienSkin}.
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     * @param ancho anchura del alien en píxeles.
     * @param alto altura del alien en píxeles.
     * @param mundoAncho anchura total del mundo.
     * @param mundoAlto altura total del mundo.
     * @param skin variante de alien (define los recursos gráficos a usar).
     */
    public Alien(double x, double y, double ancho, double alto,
                 double mundoAncho, double mundoAlto, AlienSkin skin) {
        super(x, y, ancho, alto, mundoAncho, mundoAlto);
        this.frames = Assets.getFrames(skin.framePaths);
    }

    /**
     * Ajusta la velocidad de la animación del alien.
     *
     * @param seconds duración de cada frame en segundos.
     */
    public void setFrameDuration(double seconds) {
        this.frameDuration = seconds;
    }

    /**
     * Actualiza la posición y la animación del alien.
     *
     * <ul>
     *   <li>Se mueve horizontalmente a velocidad constante.</li>
     *   <li>Si toca un borde, invierte la dirección y desciende unos píxeles.</li>
     *   <li>Avanza en su animación si tiene más de un frame.</li>
     * </ul>
     *
     * @param dt tiempo en segundos transcurrido desde la última actualización.
     */
    @Override
    public void actualizar(double dt) {
        // Movimiento horizontal
        posicionHorizontal += dir * vel * dt;

        // Rebote en bordes y descenso
        if (posicionHorizontal <= 0 || posicionHorizontal >= mundoAncho - ancho) {
            dir = -dir;
            posicionVertical += descenso;
            clamp();
        }

        // Animación por frames
        if (frames.length > 1) {
            frameTimer += dt;
            while (frameTimer >= frameDuration) {
                frameTimer -= frameDuration;
                frameIndex = (frameIndex + 1) % frames.length;
            }
        }
    }

    /**
     * Dibuja el alien en el contexto gráfico indicado.
     *
     * @param g el {@link GraphicsContext} sobre el que se renderiza el alien.
     */
    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(frames[frameIndex], posicionHorizontal, posicionVertical, ancho, alto);
    }

    /**
     * Método de {@link IMovimiento} no utilizado en esta clase.
     *
     * @param dir dirección del movimiento (sin efecto en aliens).
     */
    @Override
    public void moverPaso(int dir) {
        // No utilizado en aliens (se mueven automáticamente).
    }
}
