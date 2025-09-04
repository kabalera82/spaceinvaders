package kabalera82.spaceinvaders.assets;

/** Variantes de alien, cada una con sus frames para animación. */
public enum AlienSkin {
    ALIEN(new String[]{"/imagenes/alien.png", "/imagenes/alien1.png"}),
    CYAN (new String[]{"/imagenes/alien-cyan.png",  "/imagenes/alien-cyan1.png"}),
    MAGENTA(new String[]{"/imagenes/alien-magenta.png","/imagenes/alien-magenta1.png"}),
    YELLOW (new String[]{"/imagenes/alien-yellow.png", "/imagenes/alien-yellow1.png"});

    public final String[] framePaths;
    AlienSkin(String[] framePaths) { this.framePaths = framePaths; }
}
