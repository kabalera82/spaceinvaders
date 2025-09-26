package kabalera82.spaceinvaders;

// === Imports =================================================================
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kabalera82.spaceinvaders.assets.Sound;
import kabalera82.spaceinvaders.assets.SoundAssets;
import kabalera82.spaceinvaders.gui.MenuInicio;

/**
 * Clase principal de la aplicación <b>Space Invaders</b>.
 *
 * <p>Extiende de {@link javafx.application.Application} y es responsable de
 * iniciar y gestionar el ciclo de vida de JavaFX. Su cometido principal es:</p>
 * <ul>
 *   <li>Configurar la ventana principal de la aplicación.</li>
 *   <li>Mostrar el menú inicial implementado en {@link MenuInicio}.</li>
 *   <li>Iniciar la música de fondo definida en {@link SoundAssets}.</li>
 * </ul>
 *
 * <p>Esta clase se considera el punto de entrada oficial del proyecto cuando se
 * ejecuta desde Maven o desde un IDE.</p>
 *
 * @author  Kabalera82
 * @version 1.0
 * @see javafx.application.Application
 * @see kabalera82.spaceinvaders.gui.MenuInicio
 * @see kabalera82.spaceinvaders.assets.SoundAssets
 */
public class App extends Application {

    /**
     * Método que se ejecuta al iniciar la aplicación JavaFX.
     *
     * <p>En este método se crea la escena principal, se configuran las
     * propiedades de la ventana (título, tamaño, redimensionamiento) y se
     * establece el menú inicial como contenido visible. Además, se arranca
     * la música de fondo en bucle.</p>
     *
     * @param escenario la ventana principal de la aplicación, proporcionada por
     *                  el sistema JavaFX al arrancar.
     */
    @Override
    public void start(Stage escenario) {
        // Crear la escena del menú de inicio
        Scene menu = new MenuInicio().construir();

        // Configuración de la ventana principal
        escenario.setTitle("Space Invaders"); // Título de la ventana
        escenario.setResizable(false);        // Desactivar redimensionamiento
        escenario.setScene(menu);             // Asignar escena inicial
        escenario.show();                     // Mostrar la ventana en pantalla

        // Iniciar música de fondo con volumen al 50%
        SoundAssets.playMusic(Sound.MUSICA_FONDO, 0.5);
    }

    // =========================================================================
    /**
     * Punto de entrada estándar del programa.
     *
     * <p>Llama a {@link Application#launch(String...)} para inicializar el
     * ciclo de vida de la aplicación JavaFX.</p>
     *
     * @param args argumentos de línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        launch(); // Arranca la aplicación JavaFX
    }
}
