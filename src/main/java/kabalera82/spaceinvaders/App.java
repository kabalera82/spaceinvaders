package kabalera82.spaceinvaders;

// === Imports ===========================================================
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kabalera82.spaceinvaders.gui.MenuInicio;

// ====================================================================================
/**
 * Clase principal de la app Space Invaders.
 * <p>
 * Muestra un menú inicial con un botón para comenzar el juego.
 * </p>
 * <p>
 * Esta clase extiende {@link javafx.application.Application} y
 * es la encargada de arrancar el programa JavaFx. 
 * </p>
 * <p>
 * Crea la ventana principal y muestra el menú de inicio definido en {@link gui.MenuInicio}.
 * </p>
 * 
 * @author Kabalera82
 * @version 1.0
 * @see javafx.application.Application
 */

public class App extends Application {
/**
 * Método principal que inicia la aplicación JavaFX.
 * 
 * @param escenario que representa la ventana principal (Stage) de la aplicación.
 */

    @Override
    public void start(Stage escenario) {                            // metodo principal que recibe la ventana (Stage escenario) 
        Scene menu = new MenuInicio().construir();                  // Creamos una instancia de MenuInicio y construimos la escena del menú
        escenario.setTitle("Space Invaders");                       // titulo del escenario
        escenario.setResizable(false);                              // ventana del escenario no redimensionable        
        escenario.setScene(menu);                                   // asigna escena del menu al escenario
        escenario.show();                                           // muestra el escenario
    }

    //===========================================================================================
        /**
     * Punto de entrada del programa.
     * <p>
     * Este método invoca a {@link Application#launch(String...)} para
     * iniciar el ciclo de vida de JavaFX.
     * </p>
     *
     * @param args Argumentos de línea de comandos (no usados).
     */
    public static void main(String[] args) {
        launch(); // Lanza la aplicación JavaFX
    }           
}
