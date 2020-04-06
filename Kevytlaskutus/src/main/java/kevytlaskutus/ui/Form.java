package kevytlaskutus.ui;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


/**
 * Class for creating Forms. 
 */
public class Form {
    
    private VBox form;
    
    private HashMap<String, Node> formFields;
   
    public Form() {
        this.formFields = new HashMap<>();
        form = new VBox(10);
    }
      
    public void addTextField(String label, String field) {
        TextField inputField = new TextField(field);
        form.getChildren().add(new Label(label));
        form.getChildren().add(inputField);
        this.formFields.put(label, inputField);
    }
    
    public void addDatePicker(String label) {
        
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(this.getLocalDateForToday());
        
        form.getChildren().add(new Label(label));
        form.getChildren().add(datePicker);
        this.formFields.put(label, datePicker);
    }
    
    private LocalDate getLocalDateForToday() {
        Date date = new Date(new java.util.Date().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date.toString());
    }
   
    public VBox getForm() {
        return form;
    }

    public HashMap<String, Node> getFormFields() {
        return this.formFields;
    }
}
