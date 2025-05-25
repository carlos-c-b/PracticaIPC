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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import modelo.PantallaID;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class MenuController implements Initializable {
    
    private User usuario;
    
    @FXML
    private ImageView perfil;
    @FXML
    private Button perfilButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setUsuario(User usuario) {
        this.usuario = usuario;
        perfil.setImage(this.usuario.getAvatar());
        perfilButton.setText(this.usuario.getNickName());
    }
    
    /** Cambiar pantlla a "PantallaInicio" */
    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        Main.setRoot(PantallaID.INICIO);
    }
    
    /** Abrir pantalla "PantallaRegistro" en modo modificar perfil */
    @FXML
    private void modificarPerfil(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaRegistro.fxml"));
//        Parent root = loader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.setTitle("Modificar Perfil");
//        stage.initModality(Modality.APPLICATION_MODAL);
//        
//        // Llamar método para especificar que es para modificar perfil
//        PantallaRegistroController controller = loader.getController();
//        controller.setModificarPerfil(usuario);
//        
//        stage.showAndWait();
    }
    
    /** Cambiar escena a "PantallaEjercicios" */
    @FXML
    private void hacerEjercicios(ActionEvent event) throws IOException {
        Main.setRoot(PantallaID.EJERCICIOS);
        EjerciciosController controlador = Main.getController(PantallaID.EJERCICIOS);
        controlador.setUsuario(usuario);
    }
    
    /** Cambiar escena a "PantallaResultados" */
    @FXML
    private void mostrarResultados(ActionEvent event) throws IOException {
        Main.setRoot(PantallaID.RESULTADOS);
        ResultadosController controlador = Main.getController(PantallaID.RESULTADOS);
        controlador.setSesiones(usuario);
    }
    
}
