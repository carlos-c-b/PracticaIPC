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
        // Puede dar error porque al ejecutare la primera orden,
        // se ejecuta automáticamente el método desdeOnAction() y
        // se compara desdeDatePicker.getValue() con hastaDatePicker.getValue(),
        // este último estando vacío
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

    @FXML
    private void desdeOnAction(ActionEvent event) {
        if (hastaDatePicker.getValue().isBefore(desdeDatePicker.getValue())) {
            hastaDatePicker.setValue(desdeDatePicker.getValue());
        }
        actualizarDatos();
        
        // Deshabilitar botones HOY
        if (desdeDatePicker.getValue() == LocalDate.now()) {
            desdeButton.setDisable(true);
        } else {
            desdeButton.setDisable(false);
        }
    }

    @FXML
    private void hastaOnAction(ActionEvent event) {
        if (desdeDatePicker.getValue().isAfter(hastaDatePicker.getValue())) {
            desdeDatePicker.setValue(hastaDatePicker.getValue());
        }
        actualizarDatos();
        
        // Deshabilitar botones HOY
        if (hastaDatePicker.getValue() == LocalDate.now()) {
            hastaButton.setDisable(true);
        } else {
            hastaButton.setDisable(false);
        }
    }
    
}
