package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import model.User;
import modelo.PantallaID;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Pablo
 */
public class EjerciciosController implements Initializable {
    
    User usuario;
    
    private Group zoomGroup;

    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private Slider zoom_slider;
    @FXML
    private Label mousePosition;
    double x = 0;
    double y = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        double minZoom = 0.15;
        double maxZoom = 1.5;
        zoom_slider.setMin(minZoom);
        zoom_slider.setMax(maxZoom);
        zoom_slider.setValue((maxZoom + minZoom) / 2);
        
        zoom_slider.valueProperty().addListener((obv, oldV, newV) -> zoom((Double) newV));
        
        map_scrollpane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() > 0) {
                zoomIn();
            } else if (event.getDeltaY() < 0) {
                zoomOut();
            }
            event.consume();
        });
        
        //=========================================================================
        //Envuelva el contenido de scrollpane en un grupo para que 
        //ScrollPane vuelva a calcular las barras de desplazamiento tras el escalado
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);
    }
    
    @FXML
    void zoomIn(MouseEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + 0.1);
    }
    @FXML
    void zoomOut(MouseEvent event) {
        zoomOut();
    }
    
    void zoomIn() {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + 0.1);
    }
    void zoomOut() {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal - 0.1);
    }

    
    
    // esta funcion es invocada al cambiar el value del slider zoom_slider
    private void zoom(double scaleValue) {
        //===================================================
        //guardamos los valores del scroll antes del escalado
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        //===================================================
        // escalamos el zoomGroup en X e Y con el valor de entrada
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        //===================================================
        // recuperamos el valor del scroll antes del escalado
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }
    
    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    private void volver(ActionEvent event) throws IOException {
        Main.setRoot(PantallaID.MENU);
    }

    @FXML
    private void coordinates(MouseEvent event) {
        x = event.getX();
        y = event.getY();
	mousePosition.setText("(" + x + ", " + y + ")");
    }


}
