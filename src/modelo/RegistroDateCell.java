package modelo;

import java.time.LocalDate;
import javafx.scene.control.DateCell;

/**
 *
 * @author Pablo
 */
public class RegistroDateCell extends DateCell {
    @Override
    public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        
        setDisable(empty || date.compareTo(LocalDate.now()) > 0);
    }
}
