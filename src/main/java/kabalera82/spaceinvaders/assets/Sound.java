package kabalera82.spaceinvaders.assets;

/**
 * Enum que lista todos los sonidos del juego con sus rutas en resources/audio.
 */
public enum Sound {
    MUSICA_FONDO   ("/audios/musica-fondo.mp3"),
    ALIEN_EXPLOSION("/audios/alien_explosion.wav"),
    GAME_OVER("/audios/game_over.wav"),
    DISPARO        ("/audios/disparo.wav");

    public final String path;

    Sound(String path) {
        this.path = path;
    }
}
