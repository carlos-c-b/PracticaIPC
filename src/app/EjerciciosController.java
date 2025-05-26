package app;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
import model.Navigation;
import model.NavDAOException;
import model.Problem;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Circle;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Answer;
/**
 *
 * @author Pablo
 */
public class EjerciciosController implements Initializable {
    
    private User usuario;
    private List<Problem> problems;
    private final ToggleGroup respuestasToggleGroup = new ToggleGroup();
    
    @FXML
    private Label pregunta;
    @FXML
    private VBox contenedorRespuestas;
    @FXML
    private Button validarButton;
    
    
    
    private Group zoomGroup;

    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private Slider zoom_slider;
    @FXML
    private Label mousePosition;
    @FXML
    private ToggleButton dotButton;
    @FXML
    private ToggleButton lineButton;
    @FXML
    private ToggleButton arcButton;
    @FXML 
    private ToggleButton protractorButton;
    @FXML
    private ToggleButton rubberButton;
    private ToggleGroup toggleGroup;
    @FXML
    private ColorPicker colorButton;
    @FXML
    private Pane mapPane;
    
    private Color color;
    
    public Navigation nav;
    
    private double pressedX, pressedY; // Variables para comprobar que la posición en que se ha presionado
                                // el ratón es la misma en la que se ha soltado
    private boolean inLine = false; // Variable para controlar que hemos puesto el primer punto de los dos para la línea

    // Radio
    private double rad = 10;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ToggleButtons
        toggleGroup = new ToggleGroup();
	dotButton.setToggleGroup(toggleGroup);
        lineButton.setToggleGroup(toggleGroup);
	arcButton.setToggleGroup(toggleGroup);
	rubberButton.setToggleGroup(toggleGroup);

		// Zoom
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
        
        mapPane.setOnMousePressed(event -> {
            pressedX = event.getSceneX();
            pressedY = event.getSceneY();
        });
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
    
    public void setUsuario(User usuario) throws NavDAOException {
        this.usuario = usuario;
        
        // Cargar problemas
        problems = Navigation.getInstance().getProblems();
        displayProblem(getRandomProblem());
        
        // Enlace para activar / deshabilitar botón de validar
        validarButton.disableProperty().bind(respuestasToggleGroup.selectedToggleProperty().isNull());
    }
    
    private Problem getRandomProblem() {
        return problems.get(new Random().nextInt(problems.size()));
    }
    
    private void displayProblem(Problem problem) {
        pregunta.setText(problem.getText());
        contenedorRespuestas.getChildren().clear();
        respuestasToggleGroup.getToggles().clear();
        List<Answer> resp = new ArrayList<>(problem.getAnswers());
        Collections.shuffle(resp);
        for (Answer ans : resp) {
            RadioButton rb = new RadioButton(ans.getText());
            rb.setUserData(ans);
            rb.setToggleGroup(respuestasToggleGroup);
            contenedorRespuestas.getChildren().add(rb);
        }
    }

    @FXML
    private void validar(ActionEvent event) {
        Answer selectedAnswer = (Answer) respuestasToggleGroup.getSelectedToggle().getUserData();
        
        // Añadir una sesión por cada pregunta para prevenir que no se guarden si se cierra la aplicación
        if (selectedAnswer.getValidity()) usuario.addSession(1, 0);
        else usuario.addSession(0, 1);
        
        // Mostrar cuál es la respuesta correcta
        for (Toggle toggle : respuestasToggleGroup.getToggles()) {
            RadioButton rb = (RadioButton) toggle;
            rb.setStyle(((Answer) rb.getUserData()).getValidity() ? "-fx-text-fill: #40D040" : "-fx-text-fill: #E04040");
        }
        validarButton.disableProperty().unbind();
        validarButton.setDisable(true);
    }
    
    @FXML
    private void nuevoProblemaAleatorio(ActionEvent event) {
        displayProblem(getRandomProblem());
        validarButton.disableProperty().unbind();
        validarButton.setDisable(false);
        validarButton.disableProperty().bind(respuestasToggleGroup.selectedToggleProperty().isNull());
    }
    
    @FXML
    private void elegirProblema(ActionEvent event) {
        
    }
    
    @FXML
    private void volver(ActionEvent event) throws IOException {
        Main.setRoot(PantallaID.MENU);
    }

    @FXML
    private void coordinates(MouseEvent event) {
	mousePosition.setText("(" + event.getX() + ", " + event.getY() + ")");
    }
	/*
	 * Pinta un círculo de radio rad en la posición del mapa en que se ha clicado
	 * Para ello comprueba que el ratón no se ha arrastrado antes de pintar
	 * @sceneX event.getSceneX
	 * @sceneY event.getSceneY
	 * @localX event.getX
	 * @localY event.getY
	 */
	private void paintButton(double sceneX, double sceneY, double localX, double localY) {
		// Comprobamos que el ratón no se ha desplazado antes de dibujar el punto (umbral de 2 unidades de coordenadas)
		if(abs(sceneX - pressedX) < 2 && abs(sceneY - pressedY) < 2) {
			Circle c = new Circle(localX, localY, rad, colorButton.getValue());
			mapPane.getChildren().add(c);
		}
	}

    @FXML
    private void handleMouseReleased(MouseEvent event) {
        double sceneX = event.getSceneX();
        double sceneY = event.getSceneY();
		double localX = event.getX();
		double localY = event.getY();
        if(dotButton.isSelected())
            paintButton(sceneX, sceneY, localX, localY);
        
    }
    
    // Devuelve el valor absoluto de un número
    private double abs(double a) {
        if(a >= 0)
            return a;
        else
            return -a;
    }

}
