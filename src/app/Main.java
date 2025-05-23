package app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.HashMap;
import modelo.PantallaID;
import modelo.PantallaObjeto;

/**
 *
 * @author carlos
 */
public class Main extends Application {
    
    private static Stage stage;
    private static Scene scene;
    private static final HashMap<PantallaID, PantallaObjeto<?>> pantallas = new HashMap<>();
    
    public static void setRoot(PantallaID id) throws IOException {
        scene.setRoot(pantallas.get(id).getRoot());
        stage.setTitle(pantallas.get(id).getTitle());
    }
    
    public static <T> T getController(PantallaID pantalla) {
        return (T) pantallas.get(pantalla).getController();
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        Main.stage = stage;
        
        // Añadir los roots de las pantallas
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaInicio.fxml"));
        pantallas.put(PantallaID.INICIO, new PantallaObjeto<>(loader.load(), loader.getController(), "Pantalla de Inicio"));
        
        loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        pantallas.put(PantallaID.MENU, new PantallaObjeto<>(loader.load(), loader.getController(), "Menú"));
        
        loader = new FXMLLoader(getClass().getResource("Resultados.fxml"));
        pantallas.put(PantallaID.RESULTADOS, new PantallaObjeto<>(loader.load(), loader.getController(), "Resultados"));
        
        loader = new FXMLLoader(getClass().getResource("Ejercicios.fxml"));
        pantallas.put(PantallaID.EJERCICIOS, new PantallaObjeto<>(loader.load(), loader.getController(), "Ejercicios"));
        
        // Cargar la pantalla de Inicio
        scene = new Scene(pantallas.get(PantallaID.INICIO).getRoot(), 600, 400);
        stage.setScene(scene);
        stage.setTitle(pantallas.get(PantallaID.INICIO).getTitle());
        stage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
