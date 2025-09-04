package kabalera82.spaceinvaders.assets;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Gestor centralizado de sonidos para el juego Space Invaders.
 *
 * <p>Permite reproducir música de fondo en bucle y efectos de sonido
 * puntuales (disparos, explosiones, impactos, etc.).
 * Los recursos de audio deben estar en <code>src/main/resources/audio/</code>
 * y declarados en el enum {@link Sound}.</p>
 *
 * <p>Ejemplos de uso:</p>
 * <pre>{@code
 *   // Música de fondo en bucle
 *   SoundAssets.playMusic(Sound.MUSICA_FONDO, 0.5);
 *
 *   // Efecto al disparar
 *   SoundAssets.playDisparo();
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public final class SoundAssets {

    /** Caché para no recargar varias veces el mismo recurso de audio. */
    private static final Map<String, Media> CACHE = new HashMap<>();

    /** Reproductor dedicado a la música de fondo (se mantiene en bucle). */
    private static MediaPlayer musicPlayer;

    /** Constructor privado: clase de utilidades, no instanciable. */
    private SoundAssets() {}

    /**
     * Obtiene (y cachea) un recurso de audio como {@link Media}.
     *
     * @param path Ruta dentro del classpath (ej. "/audio/disparo.mp3")
     * @return el objeto {@link Media} correspondiente al recurso
     * @throws IllegalArgumentException si el recurso no existe
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
     * @param sound  sonido definido en {@link Sound} (ej. MUSICA_FONDO)
     * @param volume volumen de 0.0 (silencio) a 1.0 (máximo)
     */
    public static void playMusic(Sound sound, double volume) {
        if (musicPlayer != null) musicPlayer.stop();
        musicPlayer = new MediaPlayer(getMedia(sound.path));
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // bucle infinito
        musicPlayer.setVolume(volume);
        musicPlayer.play();
    }

    /**
     * Detiene la música de fondo si está sonando.
     */
    public static void stopMusic() {
        if (musicPlayer != null) musicPlayer.stop();
    }

    // === Efectos de sonido =================================================

    /**
     * Reproduce un efecto puntual (no en bucle).
     *
     * @param sound  sonido definido en {@link Sound} (ej. DISPARO)
     * @param volume volumen de 0.0 (silencio) a 1.0 (máximo)
     */
    public static void playEffect(Sound sound, double volume) {
        MediaPlayer effect = new MediaPlayer(getMedia(sound.path));
        effect.setVolume(volume);
        effect.setOnEndOfMedia(effect::dispose); // liberar recursos al acabar
        effect.play();
    }

    // === Helpers específicos del juego =====================================

    /** Efecto de disparo de la nave. */
    public static void playDisparo() {
        playEffect(Sound.DISPARO, 0.8);
    }

    /** Efecto de explosión de un alien. */
    public static void playAlienExplosion() {
        playEffect(Sound.ALIEN_EXPLOSION, 0.9);
    }

    /** Efecto de golpe a la nave (cuando pierde una vida). */
    public static void playNaveHit() {
        playEffect(Sound.GAME_OVER, 1.0);
    }
}
