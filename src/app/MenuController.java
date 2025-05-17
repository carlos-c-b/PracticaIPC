package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class MenuController implements Initializable {
    
    Persona persona;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    @FXML
    private void cerrarSesion(ActionEvent event) {
        Util.cerrarSesion();
    }

    @FXML
    private void modificarPerfil(ActionEvent event) throws IOException {
        Util.modificarPerfil(getClass(), persona);
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
