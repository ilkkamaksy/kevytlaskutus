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
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;

/**
 *
 * @author ilkka
 */
public class FormFieldDropdown implements FormField {
    
    VBox container;
    ComboBox field;
    Label label; 
    ObservableList<String> options;
    String selectedItem;

    private AppService appService;
    
    public FormFieldDropdown(String label, ObservableList<String> options, String selectedItem, AppService appService) {
        this.label = new Label(label);
        
        this.options = options;
        this.selectedItem = selectedItem;
        this.field = new ComboBox(options);
        this.setDropDownPresetOption();
        
        this.container = new VBox(10);
        this.container.getChildren().addAll(this.label, this.field);
        
        this.appService = appService;
    }
    
    private void setDropDownPresetOption() {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).equals(selectedItem)) {
                this.field.getSelectionModel().select(selectedItem);
            }
        }
    }

    @Override
    public void setOnChangeHandler(Object object, String property) {
        this.field.valueProperty().addListener((observable, oldValue, newValue) -> {
            Method method;
            try {
                method = object.getClass().getDeclaredMethod("set" + property, CustomerCompany.class);
               
                if (newValue != null) {
                    CustomerCompany customer = this.appService.getCustomerCompanyByName((String) newValue);
                    method.invoke(object, customer);
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
        
    }

    @Override
    public VBox getField() {
        return this.container;
    }
}
