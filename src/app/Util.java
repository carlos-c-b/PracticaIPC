package app;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Persona;

/**
 *
 * @author Pablo
 */
public class Util {
    public static void cerrarSesion() {
        // Guardar cambios
        // Cambiar pantalla a "PantallaInicio"
    }
    
    public static void modificarPerfil(Class clase, Persona persona) throws IOException {
        // Poner pantalla delante "PantallaRegistro"
        
        FXMLLoader loader = new FXMLLoader(clase.getResource("PantallaRegistro.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Modificar Perfil");
        stage.initModality(Modality.APPLICATION_MODAL);
        
        //PantallaRegistroController controller = loader.getController();
        //controller.setModificarPerfil(persona);
        
        stage.showAndWait();
    }
    
    public static void registrarse(Class clase, Persona persona) throws IOException {
        // Poner pantalla delante "PantallaRegistro"
        
        FXMLLoader loader = new FXMLLoader(clase.getResource("PantallaRegistro.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Modificar Perfil");
        stage.initModality(Modality.APPLICATION_MODAL);
        
        //PantallaRegistroController controller = loader.getController();
        //controller.setRegistrarse(persona);
        
        stage.showAndWait();
    }
}
