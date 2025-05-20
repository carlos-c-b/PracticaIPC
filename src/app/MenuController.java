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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import modelo.Pantalla;

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
//        perfil.setImage(this.usuario.getAvatar());
    }
    
    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        // Guardar cambios
        // ...
        
        // Cambiar pantalla a "PantallaInicio"
        Main.setRoot(Pantalla.INICIO);
    }

    @FXML
    private void modificarPerfil(MouseEvent event) throws IOException {
        // Abrir "PantallaRegistro" en modo modificar perfil
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
    private void hacerEjercicios(ActionEvent event) throws IOException {
        // Cambiar escena a "PantallaEjercicios"
        Main.setRoot(Pantalla.EJERCICIOS);
//        EjerciciosController controlador = (EjerciciosController) Main.getController(Pantalla.EJERCICIOS);
//        controlador.setUsuario(usuario);
    }

    @FXML
    private void mostrarResultados(ActionEvent event) throws IOException {
        // Cambiar escena a "PantallaResultados"
        Main.setRoot(Pantalla.RESULTADOS);
        ResultadosController controlador = (ResultadosController) Main.getController(Pantalla.RESULTADOS);
        controlador.setUsuario(usuario);
    }
    
}
