package kabalera82.spaceinvaders.gui;

// === Imports ===========================================================
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kabalera82.spaceinvaders.controlador.GameController;

/**
 * Representa el menú inicial de la aplicación <b>Space Invaders</b>.
 *
 * <p>Este menú se muestra al iniciar el programa y ofrece:</p>
 * <ul>
 *   <li>Un título visible en la parte superior.</li>
 *   <li>Un botón principal para iniciar la partida.</li>
 * </ul>
 *
 * <p>El diseño está implementado con <b>JavaFX</b> y aplica estilos
 * desde la hoja de estilos externa <code>/css/styles.css</code>.</p>
 *
 * <h2>Responsabilidades</h2>
 * <ul>
 *   <li>Construir la interfaz del menú de inicio.</li>
 *   <li>Gestionar la transición hacia la escena de juego cuando se pulsa
 *       el botón "Start".</li>
 *   <li>Inicializar el {@link GameController} que orquesta la lógica
 *       del juego.</li>
 * </ul>
 *
 * @author  Kabalera82
 * @version 1.0
 * @see javafx.scene.Scene
 * @see kabalera82.spaceinvaders.controlador.GameController
 * @see kabalera82.spaceinvaders.gui.PanelJuego
 */
public class MenuInicio {

    /**
     * Construye y devuelve la escena del menú inicial.
     *
     * <p>Incluye un título y un botón de inicio. Al pulsar el botón, se
     * crea una instancia de {@link PanelJuego}, se inicializa un
     * {@link GameController} y se cambia la escena del
     * {@link Stage} principal hacia el juego.</p>
     *
     * @return la {@link Scene} que contiene la interfaz del menú de inicio.
     */
    public Scene construir() {
        // --- Título del menú ---
        Text titulo = new Text("SPACE INVADERS");
        titulo.getStyleClass().add("titulo");  // Clase CSS para el estilo del título

        // --- Botón Start ---
        Button botonStart = new Button("Start");
        botonStart.getStyleClass().add("boton-principal"); // Clase CSS para el estilo del botón

        // Acción asociada al botón Start
        botonStart.setOnAction(e -> {
            // Crear la vista principal del juego (Canvas incluido)
            PanelJuego view = new PanelJuego();

            // Escena del juego, usando un StackPane como contenedor del Canvas
            Scene escenaJuego = new Scene(new StackPane(view.getCanvas()));

            // Crear el controlador encargado de la lógica del juego
            GameController controller = new GameController(view);

            // Vincular entradas de teclado al controlador
            controller.bindInput(view.getCanvas());

            // Obtener ventana principal (Stage) y cambiar su escena
            Stage stage = (Stage) botonStart.getScene().getWindow();
            stage.setScene(escenaJuego);

            // Iniciar el bucle principal del juego
            controller.start();
        });

        // --- Contenedor principal ---
        VBox contenedor = new VBox(20, titulo, botonStart);
        contenedor.setAlignment(Pos.CENTER);             // Centrar contenido
        contenedor.setPrefSize(720, 480);                // Tamaño preferido de la ventana
        contenedor.getStyleClass().add("fondo-inicio");  // Estilo CSS de fondo

        // --- Escena principal del menú ---
        Scene escenario = new Scene(contenedor);

        // Vincular hoja de estilos CSS
        escenario.getStylesheets().add(
                getClass().getResource("/css/styles.css").toExternalForm()
        );

        // --- Devolver escena construida ---
        return escenario;
    }
}
