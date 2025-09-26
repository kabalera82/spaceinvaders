package kabalera82.spaceinvaders.assets;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * Gestor de recursos gráficos de la aplicación.
 *
 * <p>Se encarga de cargar y cachear imágenes para evitar múltiples accesos
 * a disco o al classpath. Gracias a este sistema, los mismos recursos
 * pueden reutilizarse en distintas entidades sin penalización de rendimiento.</p>
 *
 * <h2>Características</h2>
 * <ul>
 *   <li>Uso de un {@link Map} interno como caché.</li>
 *   <li>Método {@link #get(String)} para cargar una única imagen.</li>
 *   <li>Método {@link #getFrames(String[])} para cargar secuencias de frames.</li>
 *   <li>Lanza {@link IllegalArgumentException} si el recurso no existe.</li>
 * </ul>
 *
 * <p>Los recursos deben encontrarse en el classpath, típicamente en
 * {@code src/main/resources}.</p>
 *
 * @author  Kabalera82
 * @version 1.0
 */
public final class Assets {

    /** Caché interna de imágenes ya cargadas. */
    private static final Map<String, Image> CACHE = new HashMap<>();

    /** Constructor privado para evitar instanciación. */
    private Assets() {}

    /**
     * Obtiene una imagen desde la ruta indicada, usando la caché si ya
     * fue cargada previamente.
     *
     * @param path ruta del recurso dentro del classpath (ej. {@code "/imagenes/alien.png"}).
     * @return la {@link Image} cargada.
     * @throws IllegalArgumentException si el recurso no se encuentra.
     */
    public static Image get(String path) {
        return CACHE.computeIfAbsent(path, p -> {
            InputStream is = Assets.class.getResourceAsStream(p);
            if (is == null) throw new IllegalArgumentException("Recurso no encontrado: " + p);
            return new Image(is);
        });
    }

    /**
     * Carga una secuencia de imágenes desde un array de rutas.
     *
     * @param paths array de rutas de recursos a cargar.
     * @return un array de {@link Image} con los frames en el mismo orden.
     */
    public static Image[] getFrames(String[] paths) {
        Image[] arr = new Image[paths.length];
        for (int i = 0; i < paths.length; i++) {
            arr[i] = get(paths[i]);
        }
        return arr;
    }
}
