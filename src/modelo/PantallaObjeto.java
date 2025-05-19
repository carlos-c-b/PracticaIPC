package modelo;

import javafx.scene.Parent;

/**
 *
 * @author Pablo
 */
public class PantallaObjeto {
    private final Parent root;
    private final Object controller;
    private final String title;
    
    public PantallaObjeto(Parent root, Object controller, String title) {
        this.root = root;
        this.controller = controller;
        this.title = title;
    }

    public Parent getRoot() {
        return root;
    }

    public Object getController() {
        return controller;
    }

    public String getTitle() {
        return title;
    }
    
    
}
