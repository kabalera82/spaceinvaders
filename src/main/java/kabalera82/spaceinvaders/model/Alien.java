package kabalera82.spaceinvaders.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import kabalera82.spaceinvaders.assets.AlienSkin;
import kabalera82.spaceinvaders.assets.Assets;
import kabalera82.spaceinvaders.interfaces.IMovimiento;

/**
 * Alien con animación por frames y soporte de variantes (colores).
 */
public class Alien extends Entidad implements IMovimiento {

    // === Animación ===
    private final Image[] frames;      // Frames compartidos (cacheados en Assets)
    private int frameIndex = 0;        // Frame actual
    private double frameTimer = 0;     // Acumulador de tiempo
    private double frameDuration = 0.22; // segundos por frame (ajústalo a tu gusto)

    // === Movimiento “clásico” Space Invaders ===
    private final double vel = 60;     // velocidad horizontal px/s
    private int dir = 1;               // 1= derecha, -1= izquierda
    private final double descenso = 16;

    /** Crea alien con un solo sprite (sin animación real). */
    public Alien(double x, double y, double ancho, double alto,
                 double mundoAncho, double mundoAlto, String rutaRecurso) {
        super(x, y, ancho, alto, mundoAncho, mundoAlto);
        this.frames = new Image[]{ Assets.get(rutaRecurso) };
    }

    /** Crea alien con variante (colores) y animación por frames. */
    public Alien(double x, double y, double ancho, double alto,
                 double mundoAncho, double mundoAlto, AlienSkin skin) {
        super(x, y, ancho, alto, mundoAncho, mundoAlto);
        this.frames = Assets.getFrames(skin.framePaths);
    }

    /** Permite ajustar la velocidad de animación (segundos por frame). */
    public void setFrameDuration(double seconds) { this.frameDuration = seconds; }

    @Override
    public void actualizar(double dt) {
        // Movimiento horizontal + rebote + descenso
        posicionHorizontal += dir * vel * dt;
        if (posicionHorizontal <= 0 || posicionHorizontal >= mundoAncho - ancho) {
            dir = -dir;
            posicionVertical += descenso;
            clamp();
        }

        // Avance de animación (si hay más de un frame)
        if (frames.length > 1) {
            frameTimer += dt;
            while (frameTimer >= frameDuration) {
                frameTimer -= frameDuration;
                frameIndex = (frameIndex + 1) % frames.length;
            }
        }
    }

    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(frames[frameIndex], posicionHorizontal, posicionVertical, ancho, alto);
    }

    @Override
    public void moverPaso(int dir) { /* no usado en aliens */ }
}
