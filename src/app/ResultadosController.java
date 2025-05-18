package app;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class ResultadosController implements Initializable {
    
    Persona persona;
    
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
        desdeDatePicker.setValue(LocalDate.now());
        hastaDatePicker.setValue(LocalDate.now());
        
        // Oyente fecha de desdeDatePicker
        desdeDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Si la fecha de desde es posterior a la de hasta
            if (newVal.isAfter(hastaDatePicker.getValue())) {
                hastaDatePicker.setValue(newVal);
            }
            actualizarDatos();
            
            // Deshabilitar botones HOY
            if (newVal == LocalDate.now()) {
                desdeButton.setDisable(true);
            } else {
                desdeButton.setDisable(false);
            }
        });
        
        // Oyente fecha de hastaDatePicker
        hastaDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Si la fecha de hasta es anterior a la de desde
            if (newVal.isBefore(desdeDatePicker.getValue())) {
                desdeDatePicker.setValue(newVal);
            }
            actualizarDatos();
            
            // Deshabilitar botones HOY
            if (newVal == LocalDate.now()) {
                hastaButton.setDisable(true);
            } else {
                hastaButton.setDisable(false);
            }
        });
    }
    
    /** Leer datos de la BD con las fechas de las variables y actualizarlos en los campos de texto */
    private void actualizarDatos() {
        // ...
        textAciertos.setText(Integer.toString(0));
        textFallos.setText(Integer.toString(0));
    }
    
    @FXML
    private void atras(ActionEvent event) {
        // Cambiar a pantalla "Menu"
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        Util.cerrarSesion();
    }

    @FXML
    private void modificarPerfil(ActionEvent event) throws IOException {
        Util.modificarPerfil(getClass(), persona);
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
