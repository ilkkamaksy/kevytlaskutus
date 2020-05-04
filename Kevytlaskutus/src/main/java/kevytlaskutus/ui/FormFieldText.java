/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author ilkka
 */
public class FormFieldText implements FormField {
    
    VBox container;
    TextField field;
    Label label; 

    public FormFieldText(String label, String field) {
        this.label = new Label(label);
        this.field = new TextField(field);
        this.container = new VBox(10);
        this.container.getChildren().addAll(this.label, this.field);
    }

    @Override
    public void setOnChangeHandler(Object object, String property) {
        this.field.textProperty().addListener((observable, oldValue, newValue) -> {
            Method method;
            try {
                
                method = object.getClass().getDeclaredMethod("set" + property, String.class);
                method.invoke(object, newValue);
               
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void setCallback(Object object, String property) {
        this.field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            Method method;
            try {
                
                method = object.getClass().getDeclaredMethod(property);
                method.invoke(object);
                
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public VBox getField() {
        return this.container;
    }
}
