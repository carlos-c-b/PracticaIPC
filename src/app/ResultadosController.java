package app;

import modelo.ResultadosDateCell;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Session;
import model.User;
import modelo.PantallaID;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class ResultadosController implements Initializable {
    
    private List<Session> sesiones;
    
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
    @FXML
    private Text pText;
    @FXML
    private Rectangle pAciertos;
    @FXML
    private Rectangle pFallos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Poner las fechas de los DatePickers a hoy
        desdeDatePicker.setValue(LocalDate.now());
        hastaDatePicker.setValue(LocalDate.now());
        
        // Deshabilitar los botones de HOY
        desdeButton.setDisable(true);
        hastaButton.setDisable(true);
        
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
    
    /** Cargar las sesiones del usuario
     * @param usuario */
    public void setSesiones(User usuario) {
        sesiones = usuario.getSessions();
        
        // Poner las fechas de los DatePickers a hoy
        desdeDatePicker.setValue(LocalDate.now());
        hastaDatePicker.setValue(LocalDate.now());
        
        actualizarDatos();
        
        // Configurar el DayCellFactory de los DatePickers
        HashSet<LocalDate> fechas = new HashSet<>();
        for (Session s : sesiones) fechas.add(s.getTimeStamp().toLocalDate());
        desdeDatePicker.setDayCellFactory(picker -> new ResultadosDateCell(fechas));
        hastaDatePicker.setDayCellFactory(picker -> new ResultadosDateCell(fechas));
    }
    
    /** Leer datos de aciertos y fallos y, teniendo en cuenta las fechas, actualizarlos en los campos de texto */
    private void actualizarDatos() {
        int aciertos = 0, fallos = 0;
        
        for (Session s : sesiones) {
            LocalDate date = s.getTimeStamp().toLocalDate();
            if (!(date.isAfter(hastaDatePicker.getValue()) || date.isBefore(desdeDatePicker.getValue()))) {
                aciertos += s.getHits();
                fallos += s.getFaults();
            }
        }
        
        textAciertos.setText(Integer.toString(aciertos));
        textFallos.setText(Integer.toString(fallos));
        
        // Actualizar porcentajes
        double aciertosP = 0;
        double fallosP = 0;
        boolean sinDatos = (aciertos == 0 && fallos == 0);
        if (!sinDatos) {
            aciertosP = (double) aciertos / (aciertos + fallos);
            fallosP = (double) fallos / (aciertos + fallos);
        }
        pText.setText(sinDatos ? "--" : Integer.toString((int) (aciertosP * 100)) + "%");
        pAciertos.setWidth(sinDatos ? 0 : aciertosP * 200);
        pFallos.setWidth(sinDatos ? 0 : fallosP * 200);
    }
    
    /** Cambiar a pantalla "Menu" */
    @FXML
    private void atras(ActionEvent event) throws IOException {
        Main.setRoot(PantallaID.MENU);
    }
    
    /** Poner el DatePicker de desde con fecha a HOY */
    @FXML
    private void desdeHoy(ActionEvent event) {
        desdeDatePicker.setValue(LocalDate.now());
    }
    
    /** Poner el DatePicker de hasta con fecha a HOY */
    @FXML
    private void hastaHoy(ActionEvent event) {
        hastaDatePicker.setValue(LocalDate.now());
    }
    
}
