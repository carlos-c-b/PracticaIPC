package modelo;

import java.time.LocalDate;
import java.util.HashSet;
import javafx.scene.control.DateCell;

/**
 *
 * @author Pablo
 */
public class ResultadosDateCell extends DateCell {
    
    private final HashSet<LocalDate> fechas;

    public ResultadosDateCell(HashSet<LocalDate> fechas) {
        this.fechas = fechas;
    }
    
    @Override
    public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        
        setDisable(empty || date.compareTo(LocalDate.now()) > 0);
        if (!empty && fechas.contains(date)) setStyle("-fx-background-color: TAN");
        else setStyle(null);
    }
}
