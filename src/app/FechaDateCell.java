package app;

import java.time.LocalDate;
import java.util.HashSet;
import javafx.scene.control.DateCell;

/**
 *
 * @author Pablo
 */
public class FechaDateCell extends DateCell {
    
    private final HashSet<LocalDate> fechas;

    public FechaDateCell(HashSet<LocalDate> fechas) {
        this.fechas = fechas;
    }
    
    @Override
    public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        LocalDate today = LocalDate.now();
        setDisable(empty || date.compareTo(today) > 0);
        if (!empty && fechas.contains(date)) setStyle("-fx-background-color: TAN");
        else setStyle(null);
    }
}
