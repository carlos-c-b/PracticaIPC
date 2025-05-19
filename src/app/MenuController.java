package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class MenuController implements Initializable {
    
    User usuario;
    
    @FXML
    private ImageView perfil;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        perfil.setImage(usuario.getAvatar());
    }
    
    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
    
    @FXML
    private void cerrarSesion(ActionEvent event) {
        // Guardar cambios
        // Cambiar pantalla a "PantallaInicio"
    }

    @FXML
    private void modificarPerfil(MouseEvent event) throws IOException {
        // Poner pantalla delante "PantallaRegistro"
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaRegistro.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Modificar Perfil");
        stage.initModality(Modality.APPLICATION_MODAL);
        
        // Llamar método para especificar que es para modificar perfil
        //PantallaRegistroController controller = loader.getController();
        //controller.setModificarPerfil(usuario);
        
        stage.showAndWait();
    }

    @FXML
    private void hacerEjercicios(ActionEvent event) {
        // Cambiar escena a "PantallaEjercicios"
    }

    @FXML
    private void mostrarResultados(ActionEvent event) {
        // Cambiar escena a "PantallaResultados"
    }
    
}
