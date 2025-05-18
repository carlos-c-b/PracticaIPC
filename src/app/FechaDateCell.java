package app;

import java.time.LocalDate;
import javafx.scene.control.DateCell;

/**
 *
 * @author Pablo
 */
public class FechaDateCell extends DateCell {
    @Override
    public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        LocalDate today = LocalDate.now();
        setDisable(empty || date.compareTo(today) > 0);
    }
}
