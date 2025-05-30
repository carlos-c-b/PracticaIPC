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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import model.User;
import modelo.PantallaID;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseEvent;
import model.Navigation;
import model.NavDAOException;
import model.Problem;
import java.util.List;
import java.util.Random;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Circle;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Answer;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.application.Platform;
import javafx.scene.text.Font;
import java.io.File;
import java.io.FileInputStream;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.FileNotFoundException;
import java.util.Locale;
import javafx.geometry.Bounds;

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
    private ListView listView;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private Slider zoom_slider;
    @FXML
    private Label mousePosition;
    @FXML
    private ToggleButton pointButton;
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
    @FXML
    private ToggleButton cursorButton;
    @FXML
    private ToggleButton textButton;
    @FXML
    private Slider widthSlider;

    private ImageView protractorView = new ImageView();

    private String urlImage = "."+File.separator+"src"+File.separator+"images"+File.separator+"transportador.png";
    
    private Color color;
    
    private Point2D localBase;
    
    public Navigation nav;
    
    private double pressedX, pressedY; // Variables para comprobar que la posición en que se ha presionado
                                // el ratón es la misma en la que se ha soltado
    
    public double strokeWidth = 10;
    
    private Line currentLine; // Línea actual siendo dibujada
    private Arc currentArc; // Arco actual
    private double rad;
    private double startX;
    private boolean angleLocked = false;
    private double startAngle;
    private double startY;
    
    private double inicioTransX;
    private double inicioTransY;
    private double baseX;
    private double baseY;
    
    
    private ToggleButton currentToggle; // Botón actualmente seleccionado
    
    private AtomicBoolean erasing; // Variable para cuando la goma está seleccionada y mantenemos pulsado sobre el mapa

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ToggleButtons
        toggleGroup = new ToggleGroup();
        cursorButton.setToggleGroup(toggleGroup);
	pointButton.setToggleGroup(toggleGroup);
        lineButton.setToggleGroup(toggleGroup);
	arcButton.setToggleGroup(toggleGroup);
	rubberButton.setToggleGroup(toggleGroup);
	textButton.setToggleGroup(toggleGroup);

	cursorButton.setSelected(true); // Encendido por defecto
	colorButton.setValue(Color.BLACK); // Color por defecto

	erasing = new AtomicBoolean();
	currentToggle = cursorButton;
        
        
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

        // Trazo
        double minTrazo = 5;
        double maxTrazo = 25;
        widthSlider.setMin(minTrazo);
        widthSlider.setMax(maxTrazo);
        strokeWidth = minTrazo; // Por defecto

        widthSlider.valueProperty().addListener((ob, oldV, newV) -> strokeWidth = (Double) newV);

        // Pannable sólo si cursorButton.isSelected
        map_scrollpane.pannableProperty().bind(cursorButton.selectedProperty());

        protractorButton.setOnAction(event -> {
            if (protractorButton.isSelected()) {
                try {
                    Image protractorImage = new Image(new FileInputStream(urlImage), 1000, 1000, true, false);
                    protractorView.imageProperty().setValue(protractorImage);
                    protractorView.setOpacity(0.65);
                    addToPane(protractorView);

                    /*
                     *   Calculamos la posición central del ListView para posicionar
                     *   el transportador
                     *   La jerarquía entre ListView y mapPane es la siguiente:
                     *   ListView
                     *   |->StackPane
                     *       |->ScrollPane
                     *            |->Pane (mapPane)  
                     *   Necesitamos 3 getParent
                     */
                    Bounds listViewBounds = listView.localToScene(listView.getBoundsInLocal());
                    double listViewCenterX = listViewBounds.getMinX() + listViewBounds.getWidth() / 2;
                    double listViewCenterY = listViewBounds.getMinY() + listViewBounds.getHeight() / 2;
                    System.out.println(listViewBounds.getMinX()/2 + "\t" + listViewBounds.getMinY()/2);
                    Point2D centerInPane = mapPane.sceneToLocal(listViewCenterX / 2, listViewCenterY / 2);

                    protractorView.setLayoutX(centerInPane.getX());
                    protractorView.setLayoutY(centerInPane.getY());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (protractorView != null) remove(protractorView);
        });
	
        protractorView.setOnMousePressed(event -> {
            localBase = mapPane.sceneToLocal(event.getSceneX(), event.getSceneY());
            baseX = protractorView.getTranslateX();
            baseY = protractorView.getTranslateY();
            event.consume();
        });
        
        protractorView.setOnMouseDragged(event -> {
            Point2D localPos = mapPane.sceneToLocal(event.getSceneX(), event.getSceneY());
            protractorView.setTranslateX(baseX + localPos.getX() - localBase.getX());
            protractorView.setTranslateY(baseY + localPos.getY() - localBase.getY());
            event.consume();
        });
        
        mapPane.setOnMousePressed(event -> {
            // Creamos punto si pointButton.isSelected
            if (pointButton.isSelected() && event.getButton() == MouseButton.PRIMARY) {
                Circle point = new Circle(event.getX(), event.getY(), strokeWidth, colorButton.getValue());
                addToPane(point);

                addContextMenu(point); // Añadimos menú al punto

                // Añadimos funcionalidad de borrado al punto
                addDeleteOnRubber(point);
            }
            
            // Creamos línea si lineButton.isSelected
            if (lineButton.isSelected() && event.getButton() == MouseButton.PRIMARY) {
                currentLine = new Line(event.getX(), event.getY(), event.getX(), event.getY());
                currentLine.setStroke(colorButton.getValue()); // Ponemos el color
                currentLine.setStrokeWidth(strokeWidth); // Ponemos anchura

                Line line = currentLine;
                addToPane(currentLine); // Añadimos la línea al mapa

                addContextMenu(line); // Añadimos menú de borrar a la línea

                // Añadimos funcionalidad de borrado a la línea
                addDeleteOnRubber(line);
            }

            // Creamos arco si arcButton.isSelected
            if (arcButton.isSelected() && event.getButton() == MouseButton.PRIMARY) {
                rad = 5;
                angleLocked = false;
                startX = event.getX();
                startY = event.getY();
                currentArc = new Arc();
                currentArc.setCenterX(event.getX());
                currentArc.setCenterY(event.getY());
                currentArc.setRadiusX(rad);
                currentArc.setRadiusY(rad);
                currentArc.setStroke(colorButton.getValue());
                currentArc.setStrokeWidth(strokeWidth);
                currentArc.setStartAngle(90);
                currentArc.setLength(180);
                currentArc.setFill(Color.TRANSPARENT);
                currentArc.setType(ArcType.OPEN);
                addToPane(currentArc);
                
                addContextMenu(currentArc);
                addDeleteOnRubber(currentArc);
            }
            
            // Funcionalidad goma
            if (rubberButton.isSelected() && event.getButton() == MouseButton.PRIMARY) {
                erasing.set(true);
            }
            
            // Funcionalidad texto
            if (textButton.isSelected() && event.getButton() == MouseButton.PRIMARY) {
                TextField text = new TextField();
                addToPane(text);
                text.setLayoutX(event.getX());
                text.setLayoutY(event.getY());
                Platform.runLater(() -> {
                    text.requestFocus();
                    text.focusedProperty().addListener((ob, oldV, newV) -> {
                        if (newV == false) {
                            remove(text);	// Eliminamos el TextField si clicamos fuera
                        }
                    });
                });
                text.setOnAction(e -> {
                    Text textoT = new Text(text.getText());
                    textoT.setLayoutX(text.getLayoutX());
                    textoT.setLayoutY(text.getLayoutY());
                    textoT.setStyle("-fx-font-family: Gafata;");
                    textoT.setFont(new Font(strokeWidth * 10));
                    addToPane(textoT);
                    remove(text);
                    addContextMenu(textoT);
                    addDeleteOnRubber(textoT);
                    e.consume();
                });
            }
            
            // Vamos a modo scroll si presionamos la rueda del ratón
            currentToggle = (ToggleButton) toggleGroup.getSelectedToggle();
            if (event.getButton() == MouseButton.MIDDLE) {
                cursorButton.setSelected(true);
            }
        });
        
        mapPane.setOnMouseDragged(event -> {
            // Arrastramos línea
            if (lineButton.isSelected() && (currentLine != null) && event.getButton() == MouseButton.PRIMARY) {
                currentLine.setEndX(event.getX());
                currentLine.setEndY(event.getY());
                event.consume();
            }

            // Arrastramos el arco
            if (arcButton.isSelected() && currentArc != null && event.getButton() == MouseButton.PRIMARY) {

                double dx = event.getX() - startX;
                double dy = event.getY() - startY;
                double nx = -dy; // Coordenadas vector normal
                double ny = dx;

                // Umbral para fijar el ángulo
                if (!angleLocked && Math.hypot(dx, dy) > 5) {
                    startAngle = Math.toDegrees(Math.atan2(-ny, nx)); // Invertimos Y porque en JavaFX Y está hacia abajo
                    currentArc.setStartAngle(startAngle);
                    currentArc.setLength(180);
                    angleLocked = true;
                }

                if (angleLocked) {
                    double radio = Math.hypot(dx, dy);
                    currentArc.setRadiusX(radio);
                    currentArc.setRadiusY(radio);
                }

                event.consume();
            }
        });
        
        mapPane.setOnMouseReleased(event -> {
            // Eliminamos la línea que estamos dibujando si su longitud es demasiado pequeña
            if (currentLine != null
                    && abs(currentLine.getStartX() - currentLine.getEndX()) < 5
                    && abs(currentLine.getStartY() - currentLine.getEndY()) < 5) {
                remove(currentLine);
            }
            // Eliminamos el arco si su longitud es demasiado pequeña
            if (currentArc != null
                    && abs(currentArc.getCenterX() - event.getX()) < 5
                    && abs(currentArc.getCenterY() - event.getY()) < 5) {
                remove(currentArc);
            }

            currentToggle.setSelected(true);
            erasing.set(false); // Desactivamos erasing si estaba activo
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
        
        nuevoProblemaAleatorio();
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
        
        validarButton.disableProperty().unbind();
        validarButton.setDisable(false);
        validarButton.disableProperty().bind(respuestasToggleGroup.selectedToggleProperty().isNull());
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
        
        // Dehacer binding y desactivar botón de validar hasta que no se elija otro problema
        validarButton.disableProperty().unbind();
        validarButton.setDisable(true);
    }
    
    @FXML
    private void nuevoProblemaAleatorio() {
        Problem randomProblem = problems.get(new Random().nextInt(problems.size()));
        displayProblem(randomProblem);
    }
    
    @FXML
    private void elegirProblema(ActionEvent event) throws IOException, NavDAOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaProblemas.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Elegir problema");
        stage.initModality(Modality.APPLICATION_MODAL);
        
        // Instanciar lista
        ListaProblemasController controlador = loader.getController();
        controlador.instantiateList();
        
        stage.showAndWait();
        
        // Obtener problema y si existe mostrarlo
        Problem problema = controlador.getProblem();
        if (problema != null) displayProblem(problema);
    }
    
    @FXML
    private void atras(ActionEvent event) throws IOException {
        Main.setRoot(PantallaID.MENU);
    }

    @FXML
    private void coordinates(MouseEvent event) {
        String x = String.format(Locale.US, "%.2f", event.getX());
        String y = String.format(Locale.US, "%.2f", event.getY());
	mousePosition.setText("(" + x + ", " + y + ")");
    }
    
    @FXML
    private void handleMouseReleased(MouseEvent event) {
        double sceneX = event.getSceneX();
        double sceneY = event.getSceneY();
        double localX = event.getX();
        double localY = event.getY();
        if (pointButton.isSelected()) paintPoint(sceneX, sceneY, localX, localY);
    }
    
    // Devuelve el valor absoluto de un número
    private double abs(double a) {
        if (a >= 0) return a;
        else return -a;
    }
    
    /**
     * Pinta un círculo de radio rad en la posición del mapa en que se ha clicado
     * Para ello comprueba que el ratón no se ha arrastrado antes de pintar
     * @sceneX event.getSceneX
     * @sceneY event.getSceneY
     * @localX event.getX
     * @localY event.getY
     */
    private Circle paintPoint(double sceneX, double sceneY, double localX, double localY) {
        Circle c = new Circle(-1);	
        // Comprobamos que el ratón no se ha desplazado antes de dibujar el punto (umbral de 2 unidades de coordenadas)
        if (abs(sceneX - pressedX) < 2 && abs(sceneY - pressedY) < 2) {
            c = new Circle(localX, localY, strokeWidth, colorButton.getValue());
            mapPane.getChildren().add(c);
        }
        return c;
    }
    
    private void addContextMenu(Node node) {
        ContextMenu menu = new ContextMenu();
        MenuItem borrar = new MenuItem("eliminar");
        borrar.setOnAction(e -> remove(node));
        menu.getItems().add(borrar);

        if (node instanceof Text) {
            MenuItem editar = new MenuItem("editar");
            editar.setOnAction(e -> {
                Text text = (Text) node;
                text.setVisible(false);
                TextField textF = new TextField();
                textF.setLayoutX(text.getLayoutX());
                textF.setLayoutY(text.getLayoutY());
                textF.setPrefHeight(text.getLayoutBounds().getHeight());
                addToPane(textF);
                Platform.runLater(() -> {
                    textF.requestFocus();
                    textF.focusedProperty().addListener((ob, oldV, newV) -> {
                        if(newV == false) {
                            remove(textF);
                            text.setVisible(true);
                        }
                    });
                });
                textF.setOnAction(ee -> {
                    text.setText(textF.getText());
                    text.setVisible(true);
                    remove(textF);
                    ee.consume();
                });
            });
            menu.getItems().add(editar);
        }

        node.setOnContextMenuRequested(event -> {
            if(menu.isShowing()) menu.hide();
            menu.show(node, event.getScreenX(), event.getScreenY());
            event.consume();
        });
    }
    
    // Método para añadir a cualquier forma y poder borrarla con la goma
    private void addDeleteOnRubber(Node node) {
        node.setOnMousePressed(event -> {
            if(rubberButton.isSelected()) remove(node);
        });
    }
    
    private void remove(Node node) {
        mapPane.getChildren().remove(node);
    }
    
    private void addToPane(Node node) {
        mapPane.getChildren().add(node);
    }
    
}
