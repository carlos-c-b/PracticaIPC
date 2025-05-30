package app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.NavDAOException;
import model.Navigation;
import model.User;
import modelo.Accion;
import modelo.RegistroDateCell;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class PantallaRegistroController implements Initializable {
    
    private User usuario;
    private BooleanProperty avatarValido, nickValido, correoValido, contrasenyaValida, fechaNacimientoValida;
    private BooleanProperty isAvatarEqual, isCorreoEqual, isContrasenyaEqual, isFechaNacimientoEqual;
    private Accion accionValue;

    @FXML
    private ImageView avatar;
    @FXML
    private TextField fieldAvatar;
    @FXML
    private Label errorAvatar;
    @FXML
    private TextField fieldNick;
    @FXML
    private Label errorNick;
    @FXML
    private TextField fieldCorreo;
    @FXML
    private Label errorCorreo;
    @FXML
    private PasswordField fieldContrasenya;
    @FXML
    private Label errorContrasenya;
    @FXML
    private DatePicker pickerFechaNacimiento;
    @FXML
    private Label errorFechaNacimiento;
    @FXML
    private Button accionButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar el DayCellFactory del DatePicker
        pickerFechaNacimiento.setDayCellFactory(picker -> new RegistroDateCell());
    }
    
    public void setModificarPerfil(User usuario) {
        accionValue = Accion.MODIFICAR_PERFIL;
        this.usuario = usuario;
        accionButton.setText("MODIFICAR PERFIL");
        
        // Rellenar campos con la información del usuario
        avatar.setImage(usuario.getAvatar());
        fieldAvatar.setText(usuario.getAvatar().getUrl());
        fieldNick.setText(usuario.getNickName());
        fieldCorreo.setText(usuario.getEmail());
        fieldContrasenya.setText(usuario.getPassword());
        pickerFechaNacimiento.setValue(usuario.getBirthdate());
        
        // Deshabilitar campo del NickName
        fieldNick.setDisable(true);
        
        // Dar el foco al campo de texto del usuario
        fieldContrasenya.requestFocus();
        
        // Variables
        avatarValido = new SimpleBooleanProperty(true);
        correoValido = new SimpleBooleanProperty(true);
        contrasenyaValida = new SimpleBooleanProperty(true);
        fechaNacimientoValida = new SimpleBooleanProperty(true);
        
        isAvatarEqual = new SimpleBooleanProperty(true);
        isCorreoEqual = new SimpleBooleanProperty(true);
        isContrasenyaEqual = new SimpleBooleanProperty(true);
        isFechaNacimientoEqual = new SimpleBooleanProperty(true);
        
        // Enlace para habilitar deshabilitar botón de MODIFICAR PERFIL
        accionButton.disableProperty().bind(Bindings.or(isAvatarEqual.and(isCorreoEqual).and(isContrasenyaEqual).and(isFechaNacimientoEqual), Bindings.not(avatarValido.and(correoValido).and(contrasenyaValida).and(fechaNacimientoValida))));
        
        // Oyentes del texto / valor
        fieldAvatar.textProperty().addListener((obs, oldVal, newVal) -> {
            String u = this.usuario.getAvatar().getUrl();
            isAvatarEqual.setValue(u == null ? newVal.isEmpty() : u.equals(newVal));
            avatarValido.setValue(comprobarAvatar(newVal));
            errorAvatar.setVisible(!avatarValido.getValue());
        });
        fieldCorreo.textProperty().addListener((obs, oldVal, newVal) -> {
            isCorreoEqual.setValue(this.usuario.getEmail().equals(newVal));
            correoValido.setValue(User.checkEmail(newVal));
            errorCorreo.setVisible(!correoValido.getValue() && !newVal.isEmpty());
        });
        fieldContrasenya.textProperty().addListener((obs, oldVal, newVal) -> {
            isContrasenyaEqual.setValue(this.usuario.getPassword().equals(newVal));
            contrasenyaValida.setValue(User.checkPassword(newVal));
            errorContrasenya.setVisible(!contrasenyaValida.getValue() && !newVal.isEmpty());
        });
        pickerFechaNacimiento.valueProperty().addListener((obs, oldVal, newVal) -> {
            isFechaNacimientoEqual.setValue(this.usuario.getBirthdate().equals(newVal));
            fechaNacimientoValida.setValue(newVal != null && newVal.isBefore(LocalDate.now().minusYears(16)));
            errorFechaNacimiento.setVisible(!fechaNacimientoValida.getValue() && newVal != null);
        });
    }
    
    public void setRegistrarse() {
        accionValue = Accion.REGISTRARSE;
        this.usuario = null;
        accionButton.setText("REGISTRARSE");
        
        // Dar el foco al campo de texto del usuario
        fieldNick.requestFocus();
        
        // Variables
        avatarValido = new SimpleBooleanProperty(true);
        nickValido = new SimpleBooleanProperty(false);
        correoValido = new SimpleBooleanProperty(false);
        contrasenyaValida = new SimpleBooleanProperty(false);
        fechaNacimientoValida = new SimpleBooleanProperty(false);
        
        // Enlace para habilitar / deshabilitar botón de REGISTRARSE
        accionButton.disableProperty().bind(Bindings.not(avatarValido.and(nickValido).and(correoValido).and(contrasenyaValida).and(fechaNacimientoValida)));
        
        // Oyentes del texto / valor
        fieldAvatar.textProperty().addListener((obs, oldVal, newVal) -> {
            avatarValido.setValue(comprobarAvatar(newVal));
            errorAvatar.setVisible(!avatarValido.getValue());
        });
        fieldNick.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                nickValido.setValue(comprobarNick(newVal));
                errorNick.setVisible(!nickValido.getValue() && !newVal.isEmpty());
            } catch (NavDAOException ex) {
                Logger.getLogger(PantallaRegistroController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        fieldCorreo.textProperty().addListener((obs, oldVal, newVal) -> {
            correoValido.setValue(User.checkEmail(newVal));
            errorCorreo.setVisible(!correoValido.getValue() && !newVal.isEmpty());
        });
        fieldContrasenya.textProperty().addListener((obs, oldVal, newVal) -> {
            contrasenyaValida.setValue(User.checkPassword(newVal));
            errorContrasenya.setVisible(!contrasenyaValida.getValue() && !newVal.isEmpty());
        });
        pickerFechaNacimiento.valueProperty().addListener((obs, oldVal, newVal) -> {
            fechaNacimientoValida.setValue(newVal != null && newVal.isBefore(LocalDate.now().minusYears(16)));
            errorFechaNacimiento.setVisible(!fechaNacimientoValida.getValue() && newVal != null);
        });
    }
    
    public User getUsuario() {
        return this.usuario;
    }
    
    private boolean comprobarAvatar(String ruta) {
        if (ruta.isEmpty()) {
            if (this.usuario == null) avatar.setImage(null);
            else if (usuario.getAvatar().getUrl() == null) avatar.setImage(usuario.getAvatar());
            return true;
        }
        try {
            avatar.setImage(new Image(new FileInputStream(ruta)));
            return true;
        } catch (FileNotFoundException e) {
            avatar.setImage(null);
            return false;
        }
    }
    
    private boolean comprobarNick(String nick) throws NavDAOException {
        if (!User.checkNickName(nick)) {
            errorNick.setText("El usuario ha de tener entre 6 y 15 caracteres: mayúsculas, minúsculas, números y/o guiones");
            return false;
        }
        if (Navigation.getInstance().exitsNickName(nick)) {
            errorNick.setText("Ya existe un usuario con ese nombre");
            return false;
        }
        return true;
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        accionButton.getScene().getWindow().hide();
    }

    @FXML
    private void accion(ActionEvent event) throws NavDAOException {
        switch (accionValue) {
            case MODIFICAR_PERFIL -> {
                this.usuario.setEmail(fieldCorreo.getText());
                this.usuario.setPassword(fieldContrasenya.getText());
                this.usuario.setAvatar(avatar.getImage());
                this.usuario.setBirthdate(pickerFechaNacimiento.getValue());
            }
            case REGISTRARSE -> {
                this.usuario = Navigation.getInstance().registerUser(fieldNick.getText(), fieldCorreo.getText(), fieldContrasenya.getText(), avatar.getImage(), pickerFechaNacimiento.getValue());
            }
                
        }
        accionButton.getScene().getWindow().hide();
    }
    
}
