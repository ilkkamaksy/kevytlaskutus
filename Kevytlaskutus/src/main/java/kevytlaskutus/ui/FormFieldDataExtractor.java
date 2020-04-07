/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author ilkka
 */
public class FormFieldDataExtractor {
    
    public static Date createDateFromDatePicker(String fieldName, HashMap<String, Node> formFields) {
        DatePicker dateFieldElem = (DatePicker) formFields.get(fieldName);
        return Date.valueOf(dateFieldElem.getValue());
    }
    
    public static String getSelectedValueFromComboBox(String fieldName, HashMap<String, Node> formFields) {
        ComboBox comboBox = (ComboBox) formFields.get(fieldName);
        if (comboBox.getSelectionModel().isEmpty()) {
            return "";
        }
        return comboBox.getValue().toString();
    }

    public static BigDecimal getBigDecimalFromTextField(String fieldName, HashMap<String, Node> formFields) {
        String value = getValueFromTextField(fieldName, formFields);
        if (value.isEmpty()) {
            return null;
        }
        return new BigDecimal(value);
    }
    
    public static int getIntFromTextField(String fieldName, HashMap<String, Node> formFields) {
        String value = getValueFromTextField(fieldName, formFields);
        if (value.isEmpty()) {
            return -1;
        }
        return Integer.valueOf(value);
    }
    
    public static String getValueFromTextField(String fieldName, HashMap<String, Node> formFields) {
        TextField textField = (TextField) formFields.get(fieldName);
        return textField.getText();
    }
}
