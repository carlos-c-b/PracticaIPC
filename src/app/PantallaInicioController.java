/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import model.User;
import modelo.Pantalla;

/**
 *
 * @author carlos
 */
public class PantallaInicioController implements Initializable {
    private User usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    /** Cambiar a pantalla de inicio de sesión */
    @FXML
    public void cambiarAInicioSesion(ActionEvent event) throws IOException {
//        // Abrir "PantallaInicioSesion"
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaInicioSesion.fxml"));
//        Parent root = loader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.setTitle("Iniciar Sesión");
//        stage.initModality(Modality.APPLICATION_MODAL);
//        
//        // Llamar método para pasarle el usuario que tiene que modificar
//        PantallaInicioSesion controlador = loader.getController();
//        controller.setUsuario(usuario);
//        
//        stage.showAndWait();
//        
//        if (controlador.getRespuesta()) {
            Main.setRoot(Pantalla.MENU);
            MenuController controladorMenu = (MenuController) Main.getController(Pantalla.MENU);
            controladorMenu.setUsuario(usuario);
//        }
    }
    
    /** Cambiar a pantalla de registro */
    @FXML
    public void cambiarARegistro(ActionEvent event) throws IOException {
//        // Abrir "PantallaRegistro" en modo registrarse
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaRegistro.fxml"));
//        Parent root = loader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.setTitle("Registrarse");
//        stage.initModality(Modality.APPLICATION_MODAL);
//        
//        // Llamar método para especificar que es para modificar perfil
//        PantallaRegistroController controlador = loader.getController();
//        controller.setRegistrarse(usuario);
//        
//        stage.showAndWait();
//        
//        if (controlador.getRespuesta()) {
            Main.setRoot(Pantalla.MENU);
            MenuController controladorMenu = (MenuController) Main.getController(Pantalla.MENU);
            controladorMenu.setUsuario(usuario);
//        }
    }
    
}
