/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Pablo
 */
public class Persona {
    
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty correo = new SimpleStringProperty();
    private final StringProperty contrasenya = new SimpleStringProperty();
    private final IntegerProperty edad = new SimpleIntegerProperty();
    private final StringProperty imagePath = new SimpleStringProperty();
    
    public Persona() {
        
    }
    
    public Persona(String nombre, String correo, String contrasenya, int edad, String imagePath) {
        this.nombre.setValue(nombre);
        this.correo.setValue(correo);
        this.contrasenya.setValue(contrasenya);
        this.edad.setValue(edad);
        this.imagePath.setValue(imagePath);
    }

    public StringProperty NombreProperty() {
        return nombre;
    }
    
    public String getNombre() {
        return nombre.getValue();
    }
    
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty CorreoProperty() {
        return correo;
    }
    
    public String getCorreo() {
        return correo.getValue();
    }
    
    public void setCorreo(String correo) {
        this.correo.set(correo);
    }

    public StringProperty ContrasenyaProperty() {
        return contrasenya;
    }
    
    public String getContrasenya() {
        return contrasenya.getValue();
    }
    
    public void setContrasenya(String contrasenya) {
        this.contrasenya.setValue(contrasenya);
    }

    public IntegerProperty EdadProperty() {
        return edad;
    }
    
    public int getEdad() {
        return edad.getValue();
    }
    
    public void setEdad(int edad) {
        this.edad.setValue(edad);
    }

    public StringProperty ImagePathProperty() {
        return imagePath;
    }
    
    public String getImagePath() {
        return imagePath.getValue();
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath.setValue(imagePath);
    }
    
}
