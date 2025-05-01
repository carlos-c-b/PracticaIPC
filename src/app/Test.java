/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

/**
 *
 * @author carlos
 */
public class Test extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        
        Text text = new Text(220, 300, "Pantalla de prueba");
        text.setFont(new Font(20));
        root.getChildren().add(text);
        
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
