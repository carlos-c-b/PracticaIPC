package app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.NavDAOException;
import model.Navigation;
import model.User;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class InicioSesionController implements Initializable {
    
    User usuario;

    @FXML
    private TextField usuarioField;
    @FXML
    private PasswordField contrasenyaField;
    @FXML
    private Button iniciarSesionButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = null;
        iniciarSesionButton.disableProperty().bind(Bindings.or(usuarioField.textProperty().isEmpty(), contrasenyaField.textProperty().isEmpty()));
    }
    
    public User getUsuario() {
        return this.usuario;
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        usuarioField.getScene().getWindow().hide();
    }

    @FXML
    private void iniciarSesion(ActionEvent event) throws NavDAOException {
        if (!Navigation.getInstance().exitsNickName(usuarioField.getText())) {
            System.out.println("Usuario no registrado"); // Poner una advertencia
            return;
        }
        usuario = Navigation.getInstance().authenticate(usuarioField.getText(), contrasenyaField.getText());
        if (usuario == null) {
            System.out.println("Contraseña incorrecta"); // Poner una advertencia
            return;
        }
        usuarioField.getScene().getWindow().hide();
    }
    
}
