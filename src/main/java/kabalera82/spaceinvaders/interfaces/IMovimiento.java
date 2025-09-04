package kabalera82.spaceinvaders.interfaces;

public interface IMovimiento {
    void moverPaso(int dir);   // -1 izq, +1 der
    void actualizar(double dt);
}
