package modelo;

import javafx.scene.control.ListCell;
import model.Problem;

/**
 *
 * @author Pablo
 */
public class ProblemasListCell extends ListCell<Problem> {
    @Override
    protected void updateItem(Problem item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty || item == null) setText(null);
        else setText(item.getText());
    }
}
