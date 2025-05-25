package app;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Modality;
import model.NavDAOException;
import model.User;
import modelo.PantallaID;

/**
 *
 * @author carlos
 */
public class PantallaInicioController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    /** Cambiar a pantalla de inicio de sesión */
    @FXML
    public void cambiarAInicioSesion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InicioSesion.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Iniciar Sesión");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        
        // Obtener usuario del inicio de sesión
        InicioSesionController controlador = loader.getController();
        User usuario = controlador.getUsuario();
        if (usuario != null) {
            Main.setRoot(PantallaID.MENU);
            MenuController controladorMenu = Main.getController(PantallaID.MENU);
            controladorMenu.setUsuario(usuario);
        }
    }
    
    /** Abrir "PantallaRegistro" en modo registrarse */
    @FXML
    public void cambiarARegistro(ActionEvent event) throws IOException, NavDAOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaRegistro.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Registrarse");
        stage.initModality(Modality.APPLICATION_MODAL);
        
        // Llamar método para especificar que es para modificar perfil
        PantallaRegistroController controlador = loader.getController();
        controlador.setRegistrarse();
        
        stage.showAndWait();
        
        User usuario = controlador.getUsuario();
        if (controlador.getUsuario() != null) {
            Main.setRoot(PantallaID.MENU);
            MenuController controladorMenu = Main.getController(PantallaID.MENU);
            controladorMenu.setUsuario(usuario);
        }
    }
    
}
