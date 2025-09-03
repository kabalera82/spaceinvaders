package kabalera82.spaceinvaders.gui;

// === Imports ===========================================================
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

// ====================================================================================
/**
 * Clase que contruye el menú de inicio de la aplicación Space Invaders.
 * <p> El menú incluye un título y un botón para iniciar el juego. </p>
 * <p> Utiliza JavaFX para la interfaz gráfica. </p>
 * 
 * <p> Aplica estilos visuales en Css desde <code>/css/styles.css</code></p>
 * 
 * @author Kabalera82
 * @version 1.0
 * @see javafx.scene.Scene
 */

public class MenuInicio {
    /**
     * Construye y devuelve la escena del menú de inicio.
     * 
     * @return la {@link Scene} que continien el mnú inicial.
     */
    public Scene construir() {
        // Textos ---------------------------------------------------------------------------
        Text titulo = new Text("Menú Inicio");                                  // Asigna a titulo un texto      
        titulo.getStyleClass().add("titulo");                                 // Asigna a titulo la clase CSS "titulo"    

        // Botones --------------------------------------------------------------------
        Button botonStart = new Button("Start");                                // Asigna a botonStart un boton con texto "Start"    
        botonStart.getStyleClass().add("boton-principal");                    // Asigna a botonStart la clase CSS "boton-principal"
        botonStart.setOnAction(e -> {
            botonStart.setDisable(true);
            botonStart.setText("Cargando...");
        });

        // Contenedor --------------------------------------------------------------
        VBox contenedor = new VBox(20, titulo, botonStart);                     // Crea un contenedor vertical (VBox) con espacio de 20 entre elementos, que contiene el titulo y el boton
        contenedor.setAlignment(Pos.CENTER);                                    // Centra los elementos dentro del contenedor
        contenedor.setPrefSize(720, 480);                                       // Establece un tamaño preferido para el contenedor
        contenedor.getStyleClass().add("fondo-inicio");                       // Asigna al contenedor la clase CSS "fondo-inicio"

        // Escenario -------------------------------------------------------------------
        Scene escenario = new Scene(contenedor);                                   // Crea una escena con el contenedor como raíz
        escenario
            .getStylesheets()
            .add(getClass()
            .getResource("/css/styles.css").toExternalForm());             // Crea una escena con contenedor como raiz y aplica estilos desde styles.css
        return escenario;                                                          // Devuelve la escena construida
    }
}
