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
import modelo.Pantalla;
import modelo.PantallaObjeto;

/**
 *s
 * @author carlos
 */
public class Main extends Application {
    
    private static Stage stage;
    private static Scene scene;
    private static final HashMap<Pantalla, PantallaObjeto> pantallas = new HashMap<>();
    
    public static void setRoot(Pantalla pantalla) throws IOException {
        scene.setRoot(pantallas.get(pantalla).getRoot());
        stage.setTitle(pantallas.get(pantalla).getTitle());
    }
    
    public static Object getController(Pantalla pantalla) {
        return pantallas.get(pantalla).getController();
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        Main.stage = stage;
        
        // Añadir los roots de las pantallas
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaInicio.fxml"));
        pantallas.put(Pantalla.INICIO, new PantallaObjeto(loader.load(), loader.getController(), "Pantalla de Inicio"));
        
        loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        pantallas.put(Pantalla.MENU, new PantallaObjeto(loader.load(), loader.getController(), "Menú"));
        
        loader = new FXMLLoader(getClass().getResource("Resultados.fxml"));
        pantallas.put(Pantalla.RESULTADOS, new PantallaObjeto(loader.load(), loader.getController(), "Resultados"));
        
//        loader = new FXMLLoader(getClass().getResource("Ejercicios.fxml"));
//        pantallas.put(Pantalla.EJERCICIOS, new PantallaObjeto(loader.load(), loader.getController(), "Ejercicios"));
        
        // Cargar la pantalla de Inicio
        scene = new Scene(pantallas.get(Pantalla.INICIO).getRoot(), 600, 400);
        stage.setScene(scene);
        stage.setTitle(pantallas.get(Pantalla.INICIO).getTitle());
        stage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
