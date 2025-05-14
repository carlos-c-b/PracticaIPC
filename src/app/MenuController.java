/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.stage.Modality;
import javafx.stage.Stage;
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
        // Guardar cambios
        // Cambiar pantalla a "PantallaInicio"
    }

    @FXML
    private void modificarPerfil(ActionEvent event) throws IOException {
        // Poner pantalla delante "PantallaRegistro"
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PantallaRegistro.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Modificar Perfil");
        stage.initModality(Modality.APPLICATION_MODAL);
        
        PantallaRegistroController controller = loader.getController();
        controller.setModificarPerfil(persona);
        
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
