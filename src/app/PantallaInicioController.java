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
import javafx.scene.Node;
import java.io.IOException;

/**
 *
 * @author carlos
 */
public class PantallaInicioController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    /*
     * Cambiar a pantalla de inicio de sesión
     */
    public void cambiarAInicioSesion(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("PantallaInicioSesion.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /*
    * Cambiar a pantalla de registro
    */
    public void cambiarARegistro(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("PantallaRegistro.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
