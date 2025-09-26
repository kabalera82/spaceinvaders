package kabalera82.spaceinvaders.assets;

/**
 * Enumeración que define las variantes de alien disponibles en el juego.
 *
 * <p>Cada variante especifica las rutas de los fotogramas que se utilizan
 * para animar al alien. Estas rutas corresponden a recursos ubicados en
 * {@code /imagenes/} dentro del classpath.</p>
 *
 * <h2>Variantes</h2>
 * <ul>
 *   <li>{@link #ALIEN} → versión estándar (verde).</li>
 *   <li>{@link #CYAN} → alien de color cian.</li>
 *   <li>{@link #MAGENTA} → alien de color magenta.</li>
 *   <li>{@link #YELLOW} → alien de color amarillo.</li>
 * </ul>
 *
 * <p>El array {@link #framePaths} permite que cada alien disponga de varios
 * sprites para generar animaciones por frames.</p>
 *
 * @author  Kabalera82
 * @version 1.0
 */
public enum AlienSkin {

    /** Variante estándar de alien (verde). */
    ALIEN(new String[]{"/imagenes/alien.png", "/imagenes/alien1.png"}),

    /** Variante de alien color cian. */
    CYAN(new String[]{"/imagenes/alien-cyan.png", "/imagenes/alien-cyan1.png"}),

    /** Variante de alien color magenta. */
    MAGENTA(new String[]{"/imagenes/alien-magenta.png","/imagenes/alien-magenta1.png"}),

    /** Variante de alien color amarillo. */
    YELLOW(new String[]{"/imagenes/alien-yellow.png", "/imagenes/alien-yellow1.png"});

    /** Rutas de los recursos gráficos correspondientes a los frames de animación. */
    public final String[] framePaths;

    /**
     * Constructor privado para asociar los frames a cada variante.
     *
     * @param framePaths rutas de imágenes en {@code resources}.
     */
    AlienSkin(String[] framePaths) {
        this.framePaths = framePaths;
    }
}
