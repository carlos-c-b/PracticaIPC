/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.HashMap;
import javafx.scene.Parent;
import modelo.Pantalla;

/**
 *s
 * @author carlos
 */
public class Main extends Application {
    
    private static Scene scene;
    private static final HashMap<Pantalla, Parent> roots = new HashMap<>();
    private static final HashMap<Pantalla, Object> controladores = new HashMap<>();
    
    public static void setRoot(Pantalla pantalla) throws IOException {
        scene.setRoot(roots.get(pantalla));
    }
    
    public static Object getLoader(Pantalla pantalla) {
        return controladores.get(pantalla);
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        
        // Añadir los roots de las pantallas
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaInicio.fxml"));
        roots.put(Pantalla.INICIO, loader.load());
        controladores.put(Pantalla.INICIO, loader.getController());
        
        loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        roots.put(Pantalla.MENU, loader.load());
        controladores.put(Pantalla.MENU, loader.getController());
        
        loader = new FXMLLoader(getClass().getResource("Resultados.fxml"));
        roots.put(Pantalla.RESULTADOS, loader.load());
        controladores.put(Pantalla.RESULTADOS, loader.getController());
        
//        loader = new FXMLLoader(getClass().getResource("Ejercicios.fxml"));
//        roots.put(Pantalla.EJERCICIOS, loader.load());
//        controladores.put(Pantalla.EJERCICIOS, loader.getController());
        
        // Cargar la pantalla de Inicio
        scene = new Scene(roots.get(Pantalla.INICIO), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Aplicación");
        stage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
