/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.util.HashMap;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author ilkka
 */
public class FormUi {
    
    private VBox form;
    private HashMap<String, TextField> formFields;
    private HashMap<String, Label> formLabels;
    private Button submitButton = new Button("Submit");
 
    public FormUi() {
        form = new VBox(10);
        this.formFields = new HashMap<>();
        this.formLabels = new HashMap<>();
    }
    
    public VBox make() {       
        addAllNodesToForm(form);
        return form;
    }
  
    public void setSubmitButtonText(String text) {
        submitButton.setText(text);
    }
    
    public Button getSubmitButton() {
        return this.submitButton;
    }
    
    public void addFormField(String label, String field) {
        this.formFields.put(label, new TextField(field));
        this.formLabels.put(label, new Label(label));
    }
   
    public HashMap<String, TextField> getFormFields() {
        return this.formFields;
    }
    
    private void addAllNodesToForm(VBox form) {
        for ( String key : this.formFields.keySet() ) {
            form.getChildren().add(this.formLabels.get(key));
            form.getChildren().add(this.formFields.get(key));
        }
        form.getChildren().add(this.submitButton);
    }
    
    public VBox getForm() {
        return this.form;
    }
}
