package kabalera82.spaceinvaders.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import kabalera82.spaceinvaders.interfaces.IMovimiento;

public class Alien extends Entidad implements IMovimiento {

    private final Image img;
    private final double vel = 40;
    private int dir = 1;
    private final double descenso = 16;

    public Alien(double x, double y, double ancho, double alto,
                 double mundoAncho, double mundoAlto, String rutaRecurso) {
        super(x, y, ancho, alto, mundoAncho, mundoAlto);
        img = new Image(getClass().getResourceAsStream(rutaRecurso));
    }

    @Override
    public void actualizar(double dt) {
        x += dir * vel * dt;
        if (x <= 0 || x >= mundoAncho - ancho) {
            dir = -dir;
            y += descenso;
            clamp();
        }
    }

    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(img, x, y, ancho, alto);
    }

    @Override
    public void moverPaso(int dir) { }
}
