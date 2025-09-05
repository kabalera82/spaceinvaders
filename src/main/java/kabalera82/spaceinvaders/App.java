package kabalera82.spaceinvaders;

//  Imports ===============================================================================
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kabalera82.spaceinvaders.assets.Sound;
import kabalera82.spaceinvaders.assets.SoundAssets;
import kabalera82.spaceinvaders.gui.MenuInicio;

/**
 * Clase principal de la aplicación <b>Space Invaders</b>.
 *
 * <p>Esta clase extiende {@link javafx.application.Application} y es la encargada
 * de gestionar el ciclo de vida de JavaFX.</p>
 *
 * <ul>
 *   <li>Crear y configurar la ventana principal de la aplicación.</li>
 *   <li>Mostrar el menú inicial definido en {@link MenuInicio}.</li>
 *   <li>Iniciar la música de fondo.</li>
 * </ul>
 * @author Kabalera82
 * @version 1.0
 */
public class App extends Application {

    /**
     * Método principal que se ejecuta al iniciar la aplicación JavaFX.
     *
     * <p>Se encarga de preparar la ventana principal (Stage), asignar un título,
     * configurar si es redimensionable y mostrar la escena del menú inicial.</p>
     *
     * @param escenario Ventana principal de la aplicación (Stage).
     */
    @Override
    public void start(Stage escenario) {
        // Crear la escena del menú de inicio a través de MenuInicio
        Scene menu = new MenuInicio().construir();

        // Configurar propiedades del escenario (ventana)
        escenario.setTitle("Space Invaders"); // título de la ventana
        escenario.setResizable(false);        // no permitir redimensionar
        escenario.setScene(menu);             // asignar la escena del menú
        escenario.show();                     // mostrar la ventana
    
        // Reproducir música de fondo
        SoundAssets.playMusic(Sound.MUSICA_FONDO, 0.5);
        
    }

    // ===========================================================================================
    /**
     * Punto de entrada del programa.
     *
     * <p>Este método invoca a {@link Application#launch(String...)} para
     * iniciar el ciclo de vida de JavaFX.</p>
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        launch(); // Lanza la aplicación JavaFX
    }
}
