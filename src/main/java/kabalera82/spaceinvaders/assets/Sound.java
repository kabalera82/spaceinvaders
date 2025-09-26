package kabalera82.spaceinvaders.assets;

/**
 * Enumeración que define los sonidos disponibles en el juego, junto con
 * la ruta de cada archivo dentro de {@code resources/audios}.
 *
 * <h2>Sonidos incluidos</h2>
 * <ul>
 *   <li>{@link #MUSICA_FONDO} → música ambiental de fondo.</li>
 *   <li>{@link #ALIEN_EXPLOSION} → sonido al destruir un alien.</li>
 *   <li>{@link #GAME_OVER} → sonido al finalizar la partida.</li>
 *   <li>{@link #DISPARO} → sonido al disparar un proyectil.</li>
 * </ul>
 *
 * <p>El campo {@link #path} expone la ruta relativa del recurso de audio
 * dentro del classpath para facilitar su carga.</p>
 *
 * @author  Kabalera82
 * @version 1.0
 */
public enum Sound {

    /** Música de fondo principal. */
    MUSICA_FONDO   ("/audios/musica-fondo.mp3"),

    /** Sonido de explosión de un alien. */
    ALIEN_EXPLOSION("/audios/alien_explosion.wav"),

    /** Sonido reproducido al terminar la partida. */
    GAME_OVER      ("/audios/game_over.wav"),

    /** Sonido emitido al disparar un proyectil. */
    DISPARO        ("/audios/disparo.wav");

    /** Ruta del recurso de audio en el classpath. */
    public final String path;

    /**
     * Constructor privado para asociar cada sonido con su recurso.
     *
     * @param path ruta del archivo de audio dentro de {@code resources}.
     */
    Sound(String path) {
        this.path = path;
    }
}
