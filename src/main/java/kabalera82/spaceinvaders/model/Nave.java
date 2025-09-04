package kabalera82.spaceinvaders.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import kabalera82.spaceinvaders.interfaces.IMovimiento;

public class Nave extends Entidad implements IMovimiento {

    private final Image img;
    private double pasoPx = 16;

    public Nave(double x, double y, double ancho, double alto,
                double mundoAncho, double mundoAlto, String rutaRecurso) {
        super(x, y, ancho, alto, mundoAncho, mundoAlto);
        img = new Image(getClass().getResourceAsStream(rutaRecurso));
    }

    @Override
    public void moverPaso(int dir) {
        x += dir * pasoPx; // usa los campos de Entidad
        clamp();
    }

    @Override
    public void actualizar(double dt) { }

    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(img, x, y, ancho, alto);
    }

    public void setPasoPx(double px) { this.pasoPx = px; }
}
