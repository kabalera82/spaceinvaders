package kabalera82.spaceinvaders.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entidad {
    protected double x, y;
    protected final double ancho, alto;
    protected final double mundoAncho, mundoAlto;

    protected Entidad(double x, double y, double ancho, double alto,
                      double mundoAncho, double mundoAlto) {
        this.x = x; this.y = y;
        this.ancho = ancho; this.alto = alto;
        this.mundoAncho = mundoAncho; this.mundoAlto = mundoAlto;
    }

    protected void clamp() {
        if (x < 0) x = 0;
        double maxX = mundoAncho - ancho; if (x > maxX) x = maxX;
        if (y < 0) y = 0;
        double maxY = mundoAlto - alto;   if (y > maxY) y = maxY;
    }

    public Rectangle2D getBounds() { return new Rectangle2D(x, y, ancho, alto); }
    public abstract void draw(GraphicsContext g);
}
