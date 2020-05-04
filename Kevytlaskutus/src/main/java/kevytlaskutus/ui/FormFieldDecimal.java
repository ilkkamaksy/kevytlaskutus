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
public class FormFieldDecimal implements FormField {
    
    private VBox container;
    private TextField field;
    private Label label; 

    public FormFieldDecimal(String label, String field) {
        this.label = new Label(label);
        this.field = new TextField(field);
        this.setValidation();
        
        this.container = new VBox(10);
        this.container.getChildren().addAll(this.label, this.field);
    }
    
    private void setValidation() {
        Pattern decimalPattern = Pattern.compile("-?\\d*(\\.\\d{0,2})?");
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };
        TextFormatter<Double> formatter = new TextFormatter<>(filter);
        this.field.setTextFormatter(formatter);
    }

    @Override
    public void setOnChangeHandler(Object object, String property) {
        this.field.textProperty().addListener((observable, oldValue, newValue) -> {
            Method method;
            try {
                method = object.getClass().getDeclaredMethod("set" + property, BigDecimal.class);
                
                if (isNotEmpty(newValue) && isNumeric(newValue)) {
                    method.invoke(object, new BigDecimal(newValue));
                } else {
                    method.invoke(object, new BigDecimal(0));
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
