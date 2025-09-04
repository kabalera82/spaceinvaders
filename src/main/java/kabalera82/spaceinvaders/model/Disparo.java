package kabalera82.spaceinvaders.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import kabalera82.spaceinvaders.interfaces.IMovimiento;

/**
 * Proyectil disparado por la nave (o por enemigos, si lo deseas).
 * Se mueve verticalmente (por defecto hacia arriba).
 */
public class Disparo extends Entidad implements IMovimiento {

    private final double velocidadPxS; // px/seg (negativo = hacia arriba)
    private final Color color;

    /**
     * @param x           posición inicial X
     * @param y           posición inicial Y
     * @param ancho       ancho del proyectil
     * @param alto        alto del proyectil
     * @param mundoAncho  ancho del mundo (límites)
     * @param mundoAlto   alto del mundo (límites)
     * @param velocidadPxS velocidad en px/seg (usa negativa para subir)
     * @param color       color del proyectil
     */
    public Disparo(double x, double y, double ancho, double alto,
                   double mundoAncho, double mundoAlto,
                   double velocidadPxS, Color color) {
        super(x, y, ancho, alto, mundoAncho, mundoAlto);
        this.velocidadPxS = velocidadPxS;
        this.color = color == null ? Color.WHITE : color;
    }

    @Override
    public void actualizar(double dt) {
        posicionVertical += velocidadPxS * dt;
        // Si quisieras impedir que “entre” en márgenes negativos/positivos:
        // clamp(); // no lo usamos para permitir desaparecer por fuera
    }

    /** ¿El disparo se ha ido fuera de la pantalla? */
    public boolean fueraDePantalla() {
        return (posicionVertical + alto) < 0 || posicionVertical > mundoAlto;
    }

    @Override
    public void draw(GraphicsContext g) {
        g.setFill(color);
        g.fillRect(posicionHorizontal, posicionVertical, ancho, alto);
    }

    @Override
    public void moverPaso(int dir) {
        // No se usa: el disparo se mueve con actualizar(dt)
    }
}
