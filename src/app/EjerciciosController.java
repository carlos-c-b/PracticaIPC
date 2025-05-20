package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.User;
import modelo.Pantalla;

/**
 *
 * @author Pablo
 */
public class EjerciciosController implements Initializable {
    
    User usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        Main.setRoot(Pantalla.MENU);
    }
    
}
