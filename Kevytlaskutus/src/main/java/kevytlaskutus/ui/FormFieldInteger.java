/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import kevytlaskutus.domain.AppService;


/**
 *
 * @author ilkka
 */
public class FormFieldInteger implements FormField {
    
    private VBox container;
    private TextField field;
    private Label label; 

    public FormFieldInteger(String label, String field) {
        this.label = new Label(label);
        this.field = new TextField(field);
        this.setValidation();
        
        this.container = new VBox(10);
        this.container.getChildren().addAll(this.label, this.field);
    }
    
    private void setValidation() {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String input = change.getText();
            if (input.matches("[0-9]*")) { 
                return change;
            }
            return null;
        };
        
        this.field.setTextFormatter(new TextFormatter<String>(integerFilter));  
    }

    @Override
    public void setOnChangeHandler(Object object, String property) {
        this.field.textProperty().addListener((observable, oldValue, newValue) -> {
            Method method;
            try {
                method = object.getClass().getDeclaredMethod("set" + property, Integer.class);
                
                if (isNumeric(newValue)) {
                    Integer val = Integer.valueOf(newValue);
                    method.invoke(object, val);
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
    
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public static boolean isNotEmpty(String str) {
        if (str == null || str.isBlank()) {
            return false;
        }
        return true;
    }
}
