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
import javafx.scene.text.Text;
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
    private BooleanProperty isAvatarEqual, isCorreoEqual, isContrasenyaEqual, isFehcaNacimientoEqual;
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
        avatarValido = new SimpleBooleanProperty(true);
        nickValido = new SimpleBooleanProperty(false);
        correoValido = new SimpleBooleanProperty(false);
        contrasenyaValida = new SimpleBooleanProperty(false);
        fechaNacimientoValida = new SimpleBooleanProperty(false);
        
        isAvatarEqual = new SimpleBooleanProperty(true);
        isCorreoEqual = new SimpleBooleanProperty(true);
        isContrasenyaEqual = new SimpleBooleanProperty(true);
        isFehcaNacimientoEqual = new SimpleBooleanProperty(true);
        
        // Oyentes
        fieldAvatar.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                this.avatarValido.setValue(comprobarAvatar(fieldAvatar.getText()));
            }
        });
        fieldNick.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                try {
                    this.nickValido.setValue(comprobarNick(fieldNick.getText()));
                } catch (NavDAOException ex) {
                    Logger.getLogger(PantallaRegistroController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fieldCorreo.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                this.correoValido.setValue(comprobarCorreo(fieldCorreo.getText()));
            }
        });
        fieldContrasenya.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                this.contrasenyaValida.setValue(comprobarContrasenya(fieldContrasenya.getText()));
            }
        });
        pickerFechaNacimiento.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                this.fechaNacimientoValida.setValue(comprobarFechaNacimiento(pickerFechaNacimiento.getValue()));
            }
        });
        
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
        
        // Enlace para habilitar deshabilitar botón de MODIFICAR PERFIL
        accionButton.disableProperty().bind(isAvatarEqual.and(isCorreoEqual).and(isContrasenyaEqual).and(isFehcaNacimientoEqual).and(Bindings.not(avatarValido.and(correoValido).and(contrasenyaValida).and(fechaNacimientoValida))));
        
        // Oyentes del texto / valor
        fieldAvatar.textProperty().addListener((obs, oldVal, newVal) -> {
            isAvatarEqual.setValue(this.usuario.getAvatar().getUrl().equals(newVal));
        });
        fieldCorreo.textProperty().addListener((obs, oldVal, newVal) -> {
            isCorreoEqual.setValue(this.usuario.getEmail().equals(newVal));
        });
        fieldContrasenya.textProperty().addListener((obs, oldVal, newVal) -> {
            isContrasenyaEqual.setValue(this.usuario.getPassword().equals(newVal));
        });
        pickerFechaNacimiento.valueProperty().addListener((obs, oldVal, newVal) -> {
            isFehcaNacimientoEqual.setValue(this.usuario.getBirthdate().equals(newVal));
        });
    }
    
    public void setRegistrarse() {
        accionValue = Accion.REGISTRARSE;
        this.usuario = null;
        accionButton.setText("REGISTRARSE");
        
        // Dar el foco al campo de texto del usuario
        fieldNick.requestFocus();
        
        // Enlace para habilitar / deshabilitar botón de REGISTRARSE
        accionButton.disableProperty().bind(Bindings.not(avatarValido.and(nickValido).and(correoValido).and(contrasenyaValida).and(fechaNacimientoValida)));
    }
    
    public User getUsuario() {
        return this.usuario;
    }
    
    private boolean comprobarAvatar(String ruta) {
        if (ruta.isEmpty()) {
            errorAvatar.setVisible(false);
            return true;
        }
        try {
            avatar.setImage(new Image(new FileInputStream(ruta)));
        } catch (FileNotFoundException e) {
            errorAvatar.setVisible(true);
            return false;
        }
        errorAvatar.setVisible(false);
        return true;
    }
    
    private boolean comprobarNick(String nick) throws NavDAOException {
        if (!User.checkNickName(nick)) {
            errorNick.setVisible(true);
            errorNick.setText("Usuario no válido");
            return false;
        }
        if (Navigation.getInstance().exitsNickName(nick)) {
            errorNick.setVisible(true);
            errorNick.setText("Ya existe un usuario con ese nombre");
            return false;
        }
        errorNick.setText("Error");
        errorNick.setVisible(false);
        return true;
    }
    
    private boolean comprobarCorreo(String correo) {
        if (!User.checkEmail(correo)) {
            errorCorreo.setVisible(true);
            return false;
        }
        errorCorreo.setVisible(false);
        return true;
    }
    
    private boolean comprobarContrasenya(String contrasenya) {
        if (!User.checkPassword(contrasenya)) {
            errorContrasenya.setVisible(true);
            return false;
        }
        errorContrasenya.setVisible(false);
        return true;
    }
    
    private boolean comprobarFechaNacimiento(LocalDate fecha) {
        if (fecha == null || !fecha.isBefore(LocalDate.now().minusYears(16))) {
            errorFechaNacimiento.setVisible(true);
            return false;
        }
        errorFechaNacimiento.setVisible(false);
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
