/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class MenuController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void cerrarSesion(ActionEvent event) {
        // Guardar cambios
        // Cambiar pantalla a "PantallaInicio"
    }

    @FXML
    private void modificarPerfil(ActionEvent event) {
        // Poner pantalla delante "PantallaRegistro"
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
