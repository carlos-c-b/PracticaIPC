package app;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import model.User;
import modelo.Pantalla;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class ResultadosController implements Initializable {
    
    User usuario;
    
    @FXML
    private DatePicker desdeDatePicker;
    @FXML
    private DatePicker hastaDatePicker;
    @FXML
    private Text textAciertos;
    @FXML
    private Text textFallos;
    @FXML
    private Button desdeButton;
    @FXML
    private Button hastaButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Poner las fechas de los DatePickers a hoy
        desdeDatePicker.setValue(LocalDate.now());
        hastaDatePicker.setValue(LocalDate.now());
        
        // Deshabilitar los botones de HOY
        desdeButton.setDisable(true);
        hastaButton.setDisable(true);
        
        // Configurar DatePickers para que no puedan seleccionar fechas más haya de HOY
        desdeDatePicker.setDayCellFactory(picker -> new FechaDateCell());
        hastaDatePicker.setDayCellFactory(picker -> new FechaDateCell());
        
        // Oyente fecha de desdeDatePicker
        desdeDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Si la fecha de desde es posterior a la de hasta
            if (newVal.isAfter(LocalDate.now())) {
                desdeDatePicker.setValue(LocalDate.now());
                hastaDatePicker.setValue(LocalDate.now());
            } else if (newVal.isAfter(hastaDatePicker.getValue())) {
                hastaDatePicker.setValue(newVal);
            }
            actualizarDatos();
            
            // Deshabilitar botones HOY
            if (newVal.isEqual(LocalDate.now()) || newVal.isAfter(LocalDate.now())) {
                desdeButton.setDisable(true);
            } else {
                desdeButton.setDisable(false);
            }
        });
        
        // Oyente fecha de hastaDatePicker
        hastaDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Si la fecha de hasta es anterior a la de desde
            if (newVal.isAfter(LocalDate.now())) {
                hastaDatePicker.setValue(LocalDate.now());
            } else if (newVal.isBefore(desdeDatePicker.getValue())) {
                desdeDatePicker.setValue(newVal);
            }
            actualizarDatos();
            
            // Deshabilitar botones HOY
            if (newVal.isEqual(LocalDate.now()) || newVal.isAfter(LocalDate.now())) {
                hastaButton.setDisable(true);
            } else {
                hastaButton.setDisable(false);
            }
        });
    }
    
    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
    
    /** Leer datos de la BD con las fechas de las variables y actualizarlos en los campos de texto */
    private void actualizarDatos() {
        // ...
        textAciertos.setText(Integer.toString(0));
        textFallos.setText(Integer.toString(0));
    }
    
    @FXML
    private void atras(ActionEvent event) throws IOException {
        // Cambiar a pantalla "Menu"
        Main.setRoot(Pantalla.MENU);
    }

    @FXML
    private void desdeHoy(ActionEvent event) {
        desdeDatePicker.setValue(LocalDate.now());
        // No hace falta actualizar el hastaDatePicker porque lo hace en el método desdeOnAction() automáticamente
    }

    @FXML
    private void hastaHoy(ActionEvent event) {
        hastaDatePicker.setValue(LocalDate.now());
    }
    
}
