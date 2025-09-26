package kabalera82.spaceinvaders.assets;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Gestor centralizado de sonidos para el juego <b>Space Invaders</b>.
 *
 * <p>Se encarga de reproducir música de fondo en bucle y efectos de sonido
 * puntuales (disparos, explosiones, impactos, etc.). Los recursos deben estar
 * ubicados en {@code src/main/resources/audios/} y referenciados desde el
 * enum {@link Sound}.</p>
 *
 * <h2>Ejemplos de uso</h2>
 * <pre>{@code
 * // Reproducir música de fondo en bucle
 * SoundAssets.playMusic(Sound.MUSICA_FONDO, 0.5);
 *
 * // Reproducir efecto de disparo
 * SoundAssets.playDisparo();
 * }</pre>
 *
 * @author  Kabalera82
 * @version 1.0
 */
public final class SoundAssets {

    /** Caché interna para evitar recargar varias veces el mismo recurso de audio. */
    private static final Map<String, Media> CACHE = new HashMap<>();

    /** Reproductor dedicado a la música de fondo (en bucle). */
    private static MediaPlayer musicPlayer;

    /** Constructor privado: clase de utilidades, no instanciable. */
    private SoundAssets() {}

    /**
     * Obtiene un recurso de audio y lo almacena en caché para futuras llamadas.
     *
     * @param path ruta relativa dentro del classpath (ej. {@code "/audios/disparo.wav"}).
     * @return el objeto {@link Media} correspondiente.
     * @throws IllegalArgumentException si el recurso no existe.
     */
    private static Media getMedia(String path) {
        return CACHE.computeIfAbsent(path, p -> {
            URL url = SoundAssets.class.getResource(p);
            if (url == null) {
                throw new IllegalArgumentException("Audio no encontrado: " + p);
            }
            return new Media(url.toExternalForm());
        });
    }

    // === Música de fondo ===================================================

    /**
     * Reproduce en bucle una pista de música de fondo.
     *
     * @param sound  sonido definido en {@link Sound} (ej. {@link Sound#MUSICA_FONDO}).
     * @param volume volumen entre {@code 0.0} (silencio) y {@code 1.0} (máximo).
     */
    public static void playMusic(Sound sound, double volume) {
        if (musicPlayer != null) musicPlayer.stop();
        musicPlayer = new MediaPlayer(getMedia(sound.path));
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // bucle infinito
        musicPlayer.setVolume(volume);
        musicPlayer.play();
    }

    /** Detiene la música de fondo si está activa. */
    public static void stopMusic() {
        if (musicPlayer != null) musicPlayer.stop();
    }

    // === Efectos de sonido =================================================

    /**
     * Reproduce un efecto puntual (no en bucle).
     *
     * @param sound  sonido definido en {@link Sound} (ej. {@link Sound#DISPARO}).
     * @param volume volumen entre {@code 0.0} (silencio) y {@code 1.0} (máximo).
     */
    public static void playEffect(Sound sound, double volume) {
        MediaPlayer effect = new MediaPlayer(getMedia(sound.path));
        effect.setVolume(volume);
        effect.setOnEndOfMedia(effect::dispose); // liberar recursos al terminar
        effect.play();
    }

    // === Métodos helper ====================================================

    /** Reproduce el efecto de disparo de la nave. */
    public static void playDisparo() {
        playEffect(Sound.DISPARO, 0.8);
    }

    /** Reproduce el efecto de explosión de un alien. */
    public static void playAlienExplosion() {
        playEffect(Sound.ALIEN_EXPLOSION, 0.9);
    }

    /** Reproduce el efecto de golpe a la nave (pérdida de vida o fin de partida). */
    public static void playNaveHit() {
        playEffect(Sound.GAME_OVER, 1.0);
    }
}
