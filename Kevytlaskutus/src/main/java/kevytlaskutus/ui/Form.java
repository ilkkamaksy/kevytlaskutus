package kevytlaskutus.ui;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import kevytlaskutus.domain.CustomerCompany;

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
        this.addNodesToForm(new Label(label), inputField);
        this.formFields.put(label, inputField);
    }
    
    public void addDatePicker(String label, Date presetDate) {
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(getLocalDateForString(presetDate));
        this.addNodesToForm(new Label(label), datePicker);
        this.formFields.put(label, datePicker);
    }
    
    private LocalDate getLocalDateForString(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date.toString());
    }
    
    public void addDropDown(String label, ObservableList<String> options, CustomerCompany selectedCustomer) {
        form.getChildren().add(new Label(label));
        ComboBox dropdown = new ComboBox(options);
        this.setDropDownPresetOption(dropdown, options, selectedCustomer);
        this.addNodesToForm(dropdown);
        this.formFields.put(label, dropdown);
    }
    
    private void setDropDownPresetOption(ComboBox dropdown, ObservableList<String> options, CustomerCompany selectedCustomer) {
        if (selectedCustomer == null) {
            return;
        }
        System.out.println("sfsafsa " + selectedCustomer.getName());
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).equals(selectedCustomer.getName())) {
                dropdown.getSelectionModel().select(selectedCustomer.getName());
            }
        }
    }
    
    private void addNodesToForm(Node... node) {
        form.getChildren().addAll(node);
    }
   
    public VBox getForm() {
        return form;
    }

    public HashMap<String, Node> getFormFields() {
        return this.formFields;
    }
}
