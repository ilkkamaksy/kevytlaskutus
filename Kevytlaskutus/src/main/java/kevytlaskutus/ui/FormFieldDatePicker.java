/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author ilkka
 */
public class FormFieldDatePicker implements FormField {
    
    VBox container;
    DatePicker field;
    Label label; 

    public FormFieldDatePicker(String label, String field) {
        this.label = new Label(label);
        this.field = new DatePicker();
        this.field.setValue(getLocalDateForString(field));
        this.container = new VBox(10);
        this.container.getChildren().addAll(this.label, this.field);
    }

    @Override
    public void setOnChangeHandler(Object object, String property) {
        this.field.valueProperty().addListener((observable, oldValue, newValue) -> {
            Method method;
            try {
                method = object.getClass().getDeclaredMethod("set" + property, java.sql.Date.class);
                
                if (newValue != null) {
                    Date date = Date.valueOf(newValue);
                    method.invoke(object, date);
                }
                
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
        this.field.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
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
    
    private LocalDate getLocalDateForString(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString);
    }
}
