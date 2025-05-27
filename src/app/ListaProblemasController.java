package app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.NavDAOException;
import model.Navigation;
import model.Problem;
import modelo.ProblemasListCell;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class ListaProblemasController implements Initializable {

    @FXML
    private ListView<Problem> lista;
    @FXML
    private Button elegirButton;
    
    private Problem problem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lista.setCellFactory(c -> new ProblemasListCell());
        lista.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() == -1) elegirButton.setDisable(true);
            else elegirButton.setDisable(false);
        });
    }    
    
    public void instantiateList() throws NavDAOException {
        lista.setItems(FXCollections.observableList(Navigation.getInstance().getProblems()));
        lista.getSelectionModel().clearSelection();
        elegirButton.setDisable(true);
    }
    
    public Problem getProblem() {
        return problem;
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        elegirButton.getScene().getWindow().hide();
    }
    
    @FXML
    private void elegir(ActionEvent event) {
        problem = lista.getSelectionModel().getSelectedItem();
        elegirButton.getScene().getWindow().hide();
    }
    
}
