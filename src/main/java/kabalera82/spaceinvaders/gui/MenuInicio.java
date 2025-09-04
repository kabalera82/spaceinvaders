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
 * Clase que construye el menú de inicio de la aplicación Space Invaders.
 *
 * <p>El menú incluye:</p>
 * <ul>
 *   <li>Un título.</li>
 *   <li>Un botón para iniciar el juego.</li>
 * </ul>
 *
 * <p>Está construido con JavaFX y aplica estilos desde
 * el fichero CSS <code>/css/styles.css</code>.</p>
 *
 * @author Kabalera82
 * @version 1.0
 * @see javafx.scene.Scene
 */
public class MenuInicio {

    /**
     * Construye y devuelve la escena del menú de inicio.
     *
     * @return la {@link Scene} que contiene el menú inicial.
     */
    public Scene construir() {
        // ==================== Texto del título ====================
        Text titulo = new Text("ESPACE INVADERS"); // Texto que actúa como título
        titulo.getStyleClass().add("titulo");  // Se le aplica la clase CSS "titulo"

        // ==================== Botón Start ====================
        Button botonStart = new Button("Start"); // Botón para iniciar el juego
        botonStart.getStyleClass().add("boton-principal"); // Clase CSS para estilo

        // Acción cuando se pulsa el botón Start
        botonStart.setOnAction(e -> {
            PanelJuego view = new PanelJuego();
            Scene escenaJuego = new Scene(new StackPane(view.getCanvas()));

            // Controlador con TODA la lógica
            GameController controller = new GameController(view);

            // Input: que el controlador escuche el teclado del Canvas (o de la Scene si prefieres)
            controller.bindInput(view.getCanvas());

            Stage stage = (Stage) botonStart.getScene().getWindow();
            stage.setScene(escenaJuego);

            // Arrancar bucle
            controller.start();
        });

        // ==================== Contenedor principal ====================
        VBox contenedor = new VBox(20, titulo, botonStart);
        // VBox: organiza elementos en columna
        // "20" → separación entre elementos (espaciado vertical)

        contenedor.setAlignment(Pos.CENTER);     // Centra el contenido
        contenedor.setPrefSize(720, 480);        // Tamaño preferido de la ventana
        contenedor.getStyleClass().add("fondo-inicio"); // Aplica estilo CSS de fondo

        // ==================== Escena principal ====================
        Scene escenario = new Scene(contenedor); // Escena con VBox como raíz

        // Vincular hoja de estilos CSS
        escenario
            .getStylesheets()
            .add(getClass()
            .getResource("/css/styles.css")
            .toExternalForm());

        // ==================== Devolver escena ====================
        return escenario; // Devuelve la escena ya construida
    }
}
