package kabalera82.spaceinvaders.assets;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/** Carga y cachea imágenes para no repetir IO por cada alien. */
public final class Assets {
    private static final Map<String, Image> CACHE = new HashMap<>();

    private Assets() {}

    public static Image get(String path) {
        return CACHE.computeIfAbsent(path, p -> {
            InputStream is = Assets.class.getResourceAsStream(p);
            if (is == null) throw new IllegalArgumentException("Recurso no encontrado: " + p);
            return new Image(is);
        });
    }

    public static Image[] getFrames(String[] paths) {
        Image[] arr = new Image[paths.length];
        for (int i = 0; i < paths.length; i++) arr[i] = get(paths[i]);
        return arr;
    }
}
