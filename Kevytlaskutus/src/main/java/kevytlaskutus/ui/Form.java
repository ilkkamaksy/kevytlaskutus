/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.util.HashMap;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


/**
 *
 * @author ilkka
 */
public class Form {
    
    private VBox form;
    
    private HashMap<String, TextField> formFields;
   
    public Form() {
        this.formFields = new HashMap<>();
        form = new VBox(10);
    }
      
    public void addFormField(String label, String field) {
        TextField inputField = new TextField(field);
        form.getChildren().add(new Label(label));
        form.getChildren().add(inputField);
        this.formFields.put(label, inputField);
    }
   
    public VBox getForm() {
        return form;
    }

    public HashMap<String, TextField> getFormFields() {
        return this.formFields;
    }
}
