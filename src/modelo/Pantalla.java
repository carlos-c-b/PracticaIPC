package modelo;

import javafx.scene.Parent;

/**
 *
 * @author Pablo
 * @param <T>
 */
public class Pantalla<T> {
    private final Parent root;
    private final T controller;
    private final String title;
    
    public Pantalla(Parent root, T controller, String title) {
        this.root = root;
        this.controller = controller;
        this.title = title;
    }

    public Parent getRoot() {
        return root;
    }

    public T getController() {
        return controller;
    }

    public String getTitle() {
        return title;
    }
    
}
